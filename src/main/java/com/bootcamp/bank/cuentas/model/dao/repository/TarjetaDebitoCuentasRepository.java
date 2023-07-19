package com.bootcamp.bank.cuentas.model.dao.repository;

import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoCuentaDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TarjetaDebitoCuentasRepository extends ReactiveMongoRepository<TarjetaDebitoCuentaDao,String> {

    Flux<TarjetaDebitoCuentaDao> findByNumeroCuentaAndIdCliente(String numeroCuenta, String idCliente);
    Flux<TarjetaDebitoCuentaDao> findByNumeroTarjetaDebito(String numeroTarjetaDebito);
    Flux<TarjetaDebitoCuentaDao> findByNumeroCuenta(String numeroCuenta);
}
