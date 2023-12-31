package com.bootcamp.bank.cuentas.service.impl;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.factory.CuentaA;
import com.bootcamp.bank.cuentas.factory.FactoryCuentas;
import com.bootcamp.bank.cuentas.model.*;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import com.bootcamp.bank.cuentas.model.enums.CuentasTipoTypes;
import com.bootcamp.bank.cuentas.model.enums.MonederoTypes;
import com.bootcamp.bank.cuentas.model.enums.PerfilClienteTypes;
import com.bootcamp.bank.cuentas.producer.KafkaMessageSender;
import com.bootcamp.bank.cuentas.service.MonederoMovilService;
import com.bootcamp.bank.cuentas.strategy.cliente.PerfilClienteStrategy;
import com.bootcamp.bank.cuentas.strategy.cliente.PerfilClienteStrategyFactory;
import com.bootcamp.bank.cuentas.strategy.cuentas.CuentasStrategy;
import com.bootcamp.bank.cuentas.strategy.cuentas.CuentasStrategyFactory;
import com.bootcamp.bank.cuentas.strategy.monedero.MonederoStrategy;
import com.bootcamp.bank.cuentas.strategy.monedero.MonederoStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Clase Servicio Monedero Movil
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class MonederoMovilServiceImpl implements MonederoMovilService {

    public static final String TIPO_CLIENTE_PERSONAL = "PER";
    public static final String TIPO_CUENTA_AHORRO = "AHO";

    private final CuentaRepository cuentaRepository;

    private final KafkaMessageSender kafkaProducer;

    private final ClientApiClientes clientApiClientes;

    private final ClientApiCreditos clientApiCreditos;

    private final FactoryCuentas factoryCuentas;

    private final CuentasStrategyFactory cuentasStrategyFactory;

    private final ReactiveRedisTemplate<String, CuentaDao> reactiveRedisTemplate;

    private final PerfilClienteStrategyFactory perfilClienteStrategyFactory;

    private final MonederoStrategyFactory monederoStrategyFactory;

    /**
     * Permite registrar Monedero movil
     * Permite registrar Monedero p2p
     * No se necesita ser cliente -> debe de generarse como cliente
     * Debe de vincularse a una cuenta -> debe de generarse una cuenta
     * -Se genera en backend cliente nuevo
     * -Se genera en backend una cuenta de ahorro
     * -Se registra cuenta de ahorro generada en redis
     * -Se genera en backend un monedero movil a travez de kafka
     * @param monederoMovilPost
     * @return
     */
    @Override
    public Mono<ResponseMonedero> registrarMonedero(MonederoMovilPost monederoMovilPost) {

        ClientePost cliente=new ClientePost();
        cliente.setTipoCli(TIPO_CLIENTE_PERSONAL);
        cliente.setNumeroDocumento(monederoMovilPost.getNumeroDocumento());
        cliente.setTipoDocumento(monederoMovilPost.getTipoDocumento());
        cliente.setNumeroCelular(monederoMovilPost.getNumeroCelular());
        cliente.setNombre(monederoMovilPost.getNombre());
        cliente.setCorreo(monederoMovilPost.getCorreo());
        cliente.setImeiCelular(monederoMovilPost.getImeiCelular());

        return clientApiClientes.saveCliente(cliente).flatMap(clienteNuevo->{
            log.info("nuevo cliente generado = "+clienteNuevo.toString());
            CuentaDao cuentaDao=new CuentaDao();
            cuentaDao.setTipoCuenta(TIPO_CUENTA_AHORRO);
            cuentaDao.setIdCliente(clienteNuevo.getId());

            Cliente cli=new Cliente();
            cli.setTipoCli(clienteNuevo.getTipoCli());

            // Tipos de cuenta por tipo de cliente
            PerfilClienteTypes perfilClienteTypes= setPerfilCliente.apply(cli.getTipoCli());
            PerfilClienteStrategy strategyPerfil = perfilClienteStrategyFactory.getStrategy(perfilClienteTypes);
            PerfilInfo perfilInfo = strategyPerfil.configurarPerfil(cli);
            log.info("cliente= "+cli.toString()+" tipo de cliente "+cli.getTipoCli()+" cuentas permitidas "+perfilInfo.getPerfiles().toString() );

            // Segun el tipo de cuenta se parametriza el bean
            CuentaA cuentaA = factoryCuentas.getCuentaBancaria(cuentaDao.getTipoCuenta());
            cuentaA.setIdCliente(cuentaDao.getIdCliente());
            BeanUtils.copyProperties(cuentaA,cuentaDao);

            // Segun el tipo  de cuenta se aplican estrategia reglas de negocio
            CuentasTipoTypes cuentasType= setTipoCuenta.apply(cuentaDao.getTipoCuenta());
            CuentasStrategy strategy= cuentasStrategyFactory.getStrategy(cuentasType);
            return strategy.verifyCuenta(cuentaRepository,clientApiClientes,clientApiCreditos,cuentaDao,perfilInfo)
                    .flatMap(valido ->{
                        if (valido){
                            return cuentaRepository
                                    .save(cuentaDao)
                                    .map(cuenta->{
                                        log.info(" redis request "+cuenta.toString());
                                        // REGISTRO DE CUENTA EN CACHE REDIS
                                        reactiveRedisTemplate.opsForValue().set(cuenta.getId(), cuenta).subscribe();
                                        return cuenta;
                                    })
                                    .flatMap(cuenta->{

                                        MonederoTypes moned= setTipoMonedero.apply(monederoMovilPost.getTipoMonedero());
                                        MonederoStrategy monederoStrategy= monederoStrategyFactory.getStrategy(moned);

                                        return monederoStrategy.registrarMonedero (kafkaProducer, clienteNuevo , cuenta, monederoMovilPost );

                                    });

                        } else {
                            return Mono.error(new BusinessException("No se pudo registrar cuenta , no cumple con las pre-condiciones"));
                        }

                    });

                });



    }


    Function<String, CuentasTipoTypes> setTipoCuenta = tipoCuenta  -> {
        CuentasTipoTypes cuentasType= null;
        switch (tipoCuenta) {
            case "AHO" -> cuentasType= CuentasTipoTypes.AHORRO;

            case "CTE" -> cuentasType= CuentasTipoTypes.CORRIENTE;

            case "PZF" -> cuentasType= CuentasTipoTypes.PLAZOFIJO;

            case "VIP" -> cuentasType= CuentasTipoTypes.PERSONALVIP;

            case "PYM" -> cuentasType= CuentasTipoTypes.PYME;

            default -> cuentasType =CuentasTipoTypes.INVALIDO;

        }
        return cuentasType;
    };

    Function<String, PerfilClienteTypes> setPerfilCliente = perfilCliente  -> {
        PerfilClienteTypes perfilClienteTypes= null;
        switch (perfilCliente) {
            case "EMP" -> perfilClienteTypes= PerfilClienteTypes.EMPRESARIAL;

            case "PER" -> perfilClienteTypes= PerfilClienteTypes.PERSONAL;

            default -> perfilClienteTypes = PerfilClienteTypes.INVALIDO;

        }
        return perfilClienteTypes;
    };


    Function<String, MonederoTypes> setTipoMonedero = tipoMonedero  -> {
        MonederoTypes monederoTypes= null;
        switch (tipoMonedero) {
            case "NOR" -> monederoTypes= MonederoTypes.MOVIL;

            case "P2P" -> monederoTypes= MonederoTypes.MOVIL_P2P;

            default -> monederoTypes =MonederoTypes.INVALIDO;

        }
        return monederoTypes;
    };
}
