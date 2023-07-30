package com.bootcamp.bank.cuentas.model.dao.repository;

import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CuentaRepository extends ReactiveMongoRepository<CuentaDao,String> {
    @Query("{'idCliente':?0}")
    Flux<CuentaDao> findByIdCliente(String idCliente);

    @Query("{'tipoCuenta':?0}")
    Flux<CuentaDao> findByTipoCuenta(String tipoCuenta);

    Flux<CuentaDao> findByIdClienteAndTipoCuenta(String idCliente,String tipoCuenta);
    @Query("{'numeroCuenta':?0}")
    Mono<CuentaDao> findByNumeroCuenta(String numeroCuenta);

}