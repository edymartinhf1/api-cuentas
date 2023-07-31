package com.bootcamp.bank.cuentas.strategy.monedero;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.producer.KafkaMessageSender;
import reactor.core.publisher.Mono;

public interface MonederoStrategy {
    Mono<ResponseMonedero> registrarMonedero(
            KafkaMessageSender kafkaProducer ,
            Cliente clienteNuevo,
            CuentaDao cuenta,
            MonederoMovilPost monederoMovilPost)
            ;
}
