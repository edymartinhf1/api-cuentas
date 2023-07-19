package com.bootcamp.bank.cuentas.model.dao.repository;

import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TarjetaDebitoRepository extends ReactiveMongoRepository<TarjetaDebitoDao,String> {
    Mono<TarjetaDebitoDao> findByNumeroTarjetaDebito(String numeroTarjetaDebito);
    Flux<TarjetaDebitoDao> findByNumeroCuentaPrincipalAndIdCliente(String numeroCuentaPrincipal, String idCliente);
    Flux<TarjetaDebitoDao> findByIdCliente(String idCliente);

}
