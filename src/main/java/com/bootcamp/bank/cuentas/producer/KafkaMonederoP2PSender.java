package com.bootcamp.bank.cuentas.producer;

import com.bootcamp.bank.cuentas.model.MonederoP2PPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaMonederoP2PSender {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public ResponseMonedero sendMonedero(MonederoP2PPost monederoP2PPost) {
        log.info("send message"+monederoP2PPost.toString());
        ResponseMonedero responseMonedero =new ResponseMonedero();
        try {
            String monederoAsMessage = objectMapper.writeValueAsString(monederoP2PPost);
            kafkaTemplate.send("monederoP2PMovilTopic = ", monederoAsMessage);
            responseMonedero.setCodigo("01");
            responseMonedero.setMensaje("mensaje enviado correctamente a kafka");
        }catch(JsonProcessingException ex){
            responseMonedero.setMensaje("Ocurrrio un error en el envio de mensaje a kafka");
            log.error("error "+ex.getMessage());
        }
        return responseMonedero;

    }
}
