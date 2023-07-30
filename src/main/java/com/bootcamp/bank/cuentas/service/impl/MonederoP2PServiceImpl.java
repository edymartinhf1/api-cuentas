package com.bootcamp.bank.cuentas.service.impl;

import com.bootcamp.bank.cuentas.model.MonederoP2PPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;
import com.bootcamp.bank.cuentas.producer.KafkaMonederoP2PSender;
import com.bootcamp.bank.cuentas.service.MonederoP2PService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MonederoP2PServiceImpl implements MonederoP2PService {

    @Autowired
    private KafkaMonederoP2PSender kafkaMonederoP2PSender;

    @Override
    public ResponseMonedero registrarMonedero(MonederoP2PPost monederoP2PPost) {

        return kafkaMonederoP2PSender.sendMonedero(monederoP2PPost);
    }
}
