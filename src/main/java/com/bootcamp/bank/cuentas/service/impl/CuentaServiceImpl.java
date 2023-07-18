package com.bootcamp.bank.cuentas.service.impl;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.factory.CuentaA;
import com.bootcamp.bank.cuentas.factory.FactoryCuentas;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import com.bootcamp.bank.cuentas.model.enums.CuentasTipoTypes;
import com.bootcamp.bank.cuentas.model.enums.PerfilClienteTypes;
import com.bootcamp.bank.cuentas.service.CuentaServiceI;
import com.bootcamp.bank.cuentas.strategy.cliente.PerfilClienteStrategy;
import com.bootcamp.bank.cuentas.strategy.cliente.PerfilClienteStrategyFactory;
import com.bootcamp.bank.cuentas.strategy.cuentas.CuentasStrategy;
import com.bootcamp.bank.cuentas.strategy.cuentas.CuentasStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Clase Service Cuentas
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaServiceImpl implements CuentaServiceI{

    private final CuentaRepository cuentaRepository;

    private final ClientApiClientes clientApiClientes;

    private final ClientApiCreditos clientApiCreditos;

    private final CuentasStrategyFactory cuentasStrategyFactory;

    private final FactoryCuentas factoryCuentas;

    private final PerfilClienteStrategyFactory perfilClienteStrategyFactory;

    @Value("${tipo.cliente.personal}")
    private String tipoClientePersonal;

    @Value("${tipo.cliente.empresarial}")
    private String tipoClienteEmpresarial;

    @Value("${tipo.cuenta.ahorro}")
    private String tipoCuentaAhorro;

    @Value("${tipo.cuenta.corriente}")
    private String tipoCuentaCorriente;

    @Value("${tipo.cuenta.plazo.fijo}")
    private String tipoCuentaPlazoFijo;



    /**
     * Permite registrar una cuenta
     *
     * -Un cliente personal (PER) solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo.
     *  si existe una cuenta previa no procedera a realizar la grabacion para tipo de cuenta PER
     *
     * -Un cliente empresarial (EMP) no puede tener una cuenta de ahorro o de plazo fijo, pero sí múltiples cuentas corrientes.
     *
     * @param cuentaDao
     * @return
     */

    public Mono<CuentaDao> save(CuentaDao cuentaDao) {

        // verificar existencia de cliente
        // definir la estrategia de validacion segun el cliente y tipo de cuenta
        // persistencia en la bd mongo o lanzar excepcion

        return clientApiClientes.getClientes(cuentaDao.getIdCliente())
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cliente con el id "+cuentaDao.getIdCliente())))
                .flatMap(cli->{
                    // Tipos de cuenta por tipo de cliente
                    PerfilClienteTypes perfilClienteTypes= setPerfilCliente.apply(cli.getTipoCli());
                    PerfilClienteStrategy strategyPerfil = perfilClienteStrategyFactory.getStrategy(perfilClienteTypes);
                    PerfilInfo perfilInfo = strategyPerfil.configurarPerfil(cli);

                    // Segun el tipo de cuenta se parametriza el bean
                    CuentaA cuentaA = factoryCuentas.getCuentaBancaria(cuentaDao.getTipoCuenta());
                    cuentaA.setIdCliente(cuentaDao.getIdCliente());
                    BeanUtils.copyProperties(cuentaA,cuentaDao);

                    // Segun el tipo  de cuenta se aplican reglas de negocio
                    CuentasTipoTypes cuentasType= setTipoCuenta.apply(cuentaDao.getTipoCuenta());
                    CuentasStrategy strategy= cuentasStrategyFactory.getStrategy(cuentasType);
                    return strategy.verifyCuenta(cuentaRepository,clientApiClientes,clientApiCreditos,cuentaDao,perfilInfo)
                            .flatMap(valido ->{
                                if (valido){
                                    return cuentaRepository
                                            .save(cuentaDao);
                                } else {
                                    return Mono.error(new BusinessException("No se pudo registrar cuenta , no cumple con las pre-condiciones"));
                                }
                            });


                });

    }

    /**
     * Permite obtener todas las cuentas
     * @return
     */
    public Flux<CuentaDao> findAll(){

        return cuentaRepository.findAll();
    }


    /**
     * Permite obtener cuenta por id
     * @param id
     * @return
     */
    public Mono<CuentaDao> findById(String id){
        return cuentaRepository.findById(id);
    }

    @Override
    public Mono<CuentaDao> findByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    /**
     * Permite obtener las cuenta existentes por el id de cliente
     * @param idCliente
     * @return
     */
    public Flux<CuentaDao> findByIdCliente(String idCliente){

        return cuentaRepository.findByIdCliente(idCliente);
    }

    @Override
    public Flux<CuentaDao> findByIdClienteAndTipoCuenta(String idCliente,String tipoCuenta) {
        return cuentaRepository.findByIdClienteAndTipoCuenta(idCliente,tipoCuenta);
    }

    /**
     * Permite actualizar una cuenta
     * @param cuentaDao
     * @return
     */
    public Mono<CuentaDao> update( CuentaDao cuentaDao,String numeroCuenta ) {
        return findByNumeroCuenta(numeroCuenta)
            .switchIfEmpty(Mono.error(()->new BusinessException("No existe cuenta con el numero = "+numeroCuenta)))
                .flatMap(c->cuentaRepository.save(cuentaDao));
    }

    /**
     * Permite eliminar una cuenta
     * @param numeroCuenta
     * @return
     */
    public Mono<Void> delete( String numeroCuenta) {
        return findByNumeroCuenta(numeroCuenta)
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cuenta con el numero = "+numeroCuenta)))
                .flatMap( cuenta->cuentaRepository.deleteById(cuenta.getId()));
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


}