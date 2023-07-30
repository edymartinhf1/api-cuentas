package com.bootcamp.bank.cuentas.consumer;

import com.bootcamp.bank.cuentas.service.MonederoMovilService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaClienteListener {
    /*
    private  final ObjectMapper objectMapper;

    private final MonederoMovilService monederoMovilService;

    @KafkaListener(
            topics = "monederomovil",
            groupId = "groupId"
    )
    public void consumerMonederoMovil(String tramaKafka) {
        MonederoMovilPost monederoMovil=null;
        try {
            monederoMovil = objectMapper.readValue(tramaKafka, MonederoMovilPost.class);
            log.info("monedero "+monederoMovil.toString());
            Mono<MonederoMovilPost> monederoMovilDto= Mono.just(monederoMovil);
            monederoMovilService.registrarMonedero(Util.fromMonederoMovilPostToMonederoMovilDao(monederoMovil));
        }catch(JsonProcessingException ex){
            log.error(ex.getMessage());
        }

    }
    */

}