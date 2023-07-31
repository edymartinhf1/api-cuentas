package com.bootcamp.bank.cuentas.strategy.monedero;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.producer.KafkaMessageSender;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Component
public class MonederoP2PStrategy  implements MonederoStrategy{
    @Override
    public Mono<ResponseMonedero> registrarMonedero(
            KafkaMessageSender kafkaProducer ,
            Cliente clienteNuevo,
            CuentaDao cuenta,
            MonederoMovilPost monederoMovilPost)
    {


        UUID idRequest =UUID.randomUUID();
        monederoMovilPost.setIdCliente(clienteNuevo.getId());
        monederoMovilPost.setNumeroCuentaPrincipal(cuenta.getNumeroCuenta());
        monederoMovilPost.setIdRequest(idRequest.toString());
        kafkaProducer.sendMonederoP2P(monederoMovilPost);

        ResponseMonedero responseMonedero =new ResponseMonedero();
        responseMonedero.setCodigo("01");
        responseMonedero.setMensaje("cliente y cuentas generadas , procesando monedero");
        responseMonedero.setIdCliente(clienteNuevo.getId());
        responseMonedero.setNumeroCuenta(cuenta.getNumeroCuenta());
        responseMonedero.setIdRequest(idRequest.toString());
        return Mono.just(responseMonedero);

    }
}
