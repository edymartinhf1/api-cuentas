package com.bootcamp.bank.cuentas.service.impl;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.Response;
import com.bootcamp.bank.cuentas.producer.KafkaMessageSender;
import com.bootcamp.bank.cuentas.service.MonederoMovilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase Servicio Monedero Movil
 */
@Service
@Log4j2
public class MonederoMovilServiceImpl implements MonederoMovilService {

    @Autowired
    private KafkaMessageSender kafkaProducer;

    @Autowired
    private ClientApiClientes clientApiClientes;

    /**
     * Permite registrar Monedero movil
     * @param monederoMovilPost
     * @return
     */
    @Override
    public Response registrarMonedero(MonederoMovilPost monederoMovilPost) {

        return kafkaProducer.sendMonedero(monederoMovilPost);
    }
}
