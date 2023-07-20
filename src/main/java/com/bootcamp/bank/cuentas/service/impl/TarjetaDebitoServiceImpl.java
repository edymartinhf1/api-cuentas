package com.bootcamp.bank.cuentas.service.impl;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoCuentaDao;
import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import com.bootcamp.bank.cuentas.model.dao.repository.TarjetaDebitoCuentasRepository;
import com.bootcamp.bank.cuentas.model.dao.repository.TarjetaDebitoRepository;
import com.bootcamp.bank.cuentas.service.TarjetaDebitoServiceI;
import com.bootcamp.bank.cuentas.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

/**
 * Clase Tarjeta Debito Service
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class TarjetaDebitoServiceImpl implements TarjetaDebitoServiceI {

    private final TarjetaDebitoRepository tarjetaDebitoRepository;

    private final TarjetaDebitoCuentasRepository tarjetaDebitoCuentasRepository;

    private final CuentaRepository cuentaRepository;

    private final ClientApiClientes clientApiClientes;

    /**
     * Permite registrar Tarjetas de debito
     * -verificar existencia id cliente
     * -verificar existencia de numero de cuenta principal
     * -verificar existencia de numero de cuenta a enlazar
     * -registro de tarjeta de debito
     * -verificar relacion de tarjeta de debito y numero de cuenta
     * -registro de relacion de tarjeta de debito y numero de cuenta
     * @param tarjetaDebitoDao
     * @return
     */
    @Override
    public Mono<TarjetaDebitoDao> createCardDebit(TarjetaDebitoDao tarjetaDebitoDao) {

        return clientApiClientes.getClientes(tarjetaDebitoDao.getIdCliente())
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cliente con el id "+tarjetaDebitoDao.getIdCliente())))
                .flatMap(cliente-> {
                    log.info(" cliente ="+cliente.toString());
                    return this.validateNumerosCuentasBancarias(tarjetaDebitoDao)
                        .flatMap(cuentasvalidas ->{
                            if (cuentasvalidas){
                                return this.verifyNumeroCuentaandIdClienteCardDebit(tarjetaDebitoDao);
                            } else {
                                return Mono.error(new BusinessException(" no se pudo registrar tarjeta  debito , error en validacion de cuentas "));
                            }
                        });
                })
                .flatMap(verificaexistenciatarjetadebito->{
                    log.info("existe tarjeta debito = "+verificaexistenciatarjetadebito);
                    return this.findByNumeroCuentaAndIdCliente(tarjetaDebitoDao)
                            .flatMap(valido -> {
                                log.info("registro de relacion de tarjeta de debito con numero de cuenta bancaria");
                                if (valido) {

                                    final TarjetaDebitoDao tarjetaDebitoDaoFinal = configurarTarjeta.apply(tarjetaDebitoDao);
                                    log.info("info tarjeta debito = "+tarjetaDebitoDaoFinal.toString());
                                    TarjetaDebitoCuentaDao tarjetaDebitoCuentaDao=new TarjetaDebitoCuentaDao();
                                    tarjetaDebitoCuentaDao.setIdCliente(tarjetaDebitoDaoFinal.getIdCliente());
                                    tarjetaDebitoCuentaDao.setFechaCreacion(Util.getCurrentLocalDate());
                                    tarjetaDebitoCuentaDao.setNumeroTarjetaDebito(tarjetaDebitoDaoFinal.getNumeroTarjetaDebito());
                                    tarjetaDebitoCuentaDao.setNumeroCuenta(tarjetaDebitoDaoFinal.getNumeroCuenta());

                                    if (!verificaexistenciatarjetadebito){ // graba tarjeta debito  y relacion de tarjeta de debito
                                        return tarjetaDebitoRepository.save(tarjetaDebitoDaoFinal)
                                                .flatMap(tarjetadebito-> {
                                                    return tarjetaDebitoCuentasRepository.save(tarjetaDebitoCuentaDao)
                                                            .map(e -> tarjetadebito);
                                                });
                                    } else { // solo registra relacion de tarjeta de debito y cuentas
                                        return tarjetaDebitoCuentasRepository.save(tarjetaDebitoCuentaDao)
                                                .map(e-> tarjetaDebitoDao);
                                    }
                                } else {
                                    return Mono.error(new BusinessException("No se pudo registrar relacion tarjeta  debito - numero cuenta , ya existe una relacion de  tarjeta de debito con ese numero"));
                                }
                            });

                });




    }

    /**
     * Verificar id cliente
     * Verificar el numero de cuenta de asociacion
     * Verificar el numero de tarjeta de debito
     * Verificar
     * Registro de asociacion
     * @param tarjetaDebitoDao
     * @return
     */
    @Override
    public Mono<TarjetaDebitoCuentaDao> associateCardDebit(TarjetaDebitoDao tarjetaDebitoDao) {
        return clientApiClientes.getClientes(tarjetaDebitoDao.getIdCliente())
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cliente con el id "+tarjetaDebitoDao.getIdCliente())))
                .flatMap(cliente-> {
                    log.info(" cliente ="+cliente.toString());
                    return  cuentaRepository.findByNumeroCuenta(tarjetaDebitoDao.getNumeroCuenta())
                            .switchIfEmpty(Mono.error(()->new BusinessException("No existe cuenta bancaria  "+tarjetaDebitoDao.getNumeroCuenta())))
                            .flatMap(cuenta ->{
                                log.info(" cuenta "+cuenta.toString());
                                return tarjetaDebitoRepository.findByNumeroTarjetaDebito(tarjetaDebitoDao.getNumeroTarjetaDebito())
                                        .switchIfEmpty(Mono.error(()->new BusinessException("No existe tarjeta de debito  "+tarjetaDebitoDao.getNumeroCuenta())))
                                        .flatMap( tarjetadebito->{
                                            log.info(" tarjeta debito "+tarjetadebito.toString());
                                            return tarjetaDebitoCuentasRepository.findByNumeroCuentaAndNumeroTarjetaDebito(tarjetaDebitoDao.getNumeroCuenta(),tarjetaDebitoDao.getNumeroTarjetaDebito())
                                                    .collectList()
                                                    .flatMap(cuentasLst->{
                                                        if (!cuentasLst.isEmpty()) {
                                                            return Mono.error(()->new BusinessException("Ya  existe relacion de tarjeta de debito  "+tarjetaDebitoDao.getNumeroTarjetaDebito()+" con la cuenta "+tarjetaDebitoDao.getNumeroCuenta()));
                                                        } else {
                                                            TarjetaDebitoCuentaDao tarjetaDebitoCuentaDao = new TarjetaDebitoCuentaDao();
                                                            tarjetaDebitoCuentaDao.setIdCliente(tarjetaDebitoDao.getIdCliente());
                                                            tarjetaDebitoCuentaDao.setFechaCreacion(Util.getCurrentLocalDate());
                                                            tarjetaDebitoCuentaDao.setNumeroTarjetaDebito(tarjetaDebitoDao.getNumeroTarjetaDebito());
                                                            tarjetaDebitoCuentaDao.setNumeroCuenta(tarjetaDebitoDao.getNumeroCuenta());
                                                            return tarjetaDebitoCuentasRepository.save(tarjetaDebitoCuentaDao);
                                                        }
                                                    });
                                        });
                            });
                });
    }

    @Override
    public Flux<TarjetaDebitoDao> getCardDebitPorIdCliente(String idCliente) {
        return tarjetaDebitoRepository.findByIdCliente(idCliente);
    }

    @Override
    public Flux<TarjetaDebitoCuentaDao> getNumberAccountsPorCardDebit(String tarjetaDebito) {
        return tarjetaDebitoCuentasRepository.findByNumeroTarjetaDebito(tarjetaDebito);
    }

    UnaryOperator<TarjetaDebitoDao> configurarTarjeta= tarjeta -> {
        tarjeta.setFechaCreacion(Util.getCurrentLocalDate());
        tarjeta.setNumeroTarjetaDebito("TAR-DEB-"+Util.generateRandomNumber(1,100000));
        tarjeta.setFechaCreacionT(Util.getCurrentDateAsString("yyyy-MM-dd"));
        return tarjeta;
    };

    /**
     * Permite validar la cuenta principal y la cuenta secundaria en la creacion de una tarjeta de debito
     * @param tarjetaDebitoDao
     * @return
     */
    private Mono<Boolean> validateNumerosCuentasBancarias(TarjetaDebitoDao tarjetaDebitoDao){
        log.info("tarjetaDebitoDao = "+tarjetaDebitoDao.toString());
        return cuentaRepository.findByNumeroCuenta(tarjetaDebitoDao.getNumeroCuentaPrincipal()) // cuenta principal
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cuenta bancaria principal con el numero "+tarjetaDebitoDao.getNumeroCuentaPrincipal())))
                .flatMap( cuentaprincipal -> {
                    log.info("cuenta bancaria principal encontrado = "+cuentaprincipal.toString());
                    return cuentaRepository.findByNumeroCuenta(tarjetaDebitoDao.getNumeroCuenta()) // cuenta secundaria
                            .switchIfEmpty(Mono.error(()->new BusinessException("No existe cuenta corriente con el numero "+tarjetaDebitoDao.getNumeroCuenta())))
                            .map(cuenta ->{
                                log.info("cuenta bancaria encontrado = "+cuenta.toString());
                                return cuenta!=null ? Boolean.TRUE : Boolean.FALSE;
                            });
                });
    }

    /**
     * Permite verificar la pre-existencia de una tarjeta de debito vinculada a un id cliente y a una cuenta bancaria principal
     * @param tarjetaDebitoDao
     * @return
     */
    private Mono<Boolean> verifyNumeroCuentaandIdClienteCardDebit(TarjetaDebitoDao tarjetaDebitoDao){
        return tarjetaDebitoRepository.findByNumeroCuentaPrincipalAndIdCliente(tarjetaDebitoDao.getNumeroCuentaPrincipal(),tarjetaDebitoDao.getIdCliente())
                .collectList()
                .map( cuentasencontradas -> {
                    log.info(" numero de tarjetas debito "+ cuentasencontradas.size()+" vinculadas a la cuenta principal="+tarjetaDebitoDao.getNumeroCuentaPrincipal()+" id cliente="+tarjetaDebitoDao.getIdCliente());
                    return cuentasencontradas.isEmpty() ? Boolean.FALSE : Boolean.TRUE; // False no existe , True existe
                });
    }

    /**
     * Permite verificar la pre-existencia en la entidad relaciones cuenta y tarjeta de debito  mediante numero de cuenta e id cliente
     * en tarjetaDebitoCuentasRepository
     * @param tarjetaDebitoDao
     * @return
     */
    private Mono<Boolean> findByNumeroCuentaAndIdCliente(TarjetaDebitoDao tarjetaDebitoDao){
        log.info(" findByNumeroTarjetaDebitoAndNumeroCuenta "+tarjetaDebitoDao.toString());
        return tarjetaDebitoCuentasRepository.findByNumeroCuentaAndIdCliente(tarjetaDebitoDao.getNumeroCuenta(),tarjetaDebitoDao.getIdCliente())
                .collectList()
                .map(lista -> {
                    log.info(" lista relaciones tarjeta debito encontradas "+lista.size());
                    return lista.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
                });
    }






}
