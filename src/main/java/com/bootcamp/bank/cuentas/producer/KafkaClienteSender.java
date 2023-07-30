package com.bootcamp.bank.cuentas.producer;

import com.bootcamp.bank.cuentas.model.ClientePost;
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
public class KafkaClienteSender {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ResponseMonedero sendMonedero(ClientePost clientePost) {
        log.info("send message"+clientePost.toString());
        ResponseMonedero responseMonedero =new ResponseMonedero();
        try {
            String clientePostAsMessage = objectMapper.writeValueAsString(clientePost);
            kafkaTemplate.send("clienteTopic = ", clientePostAsMessage);
            responseMonedero.setCodigo("01");
            responseMonedero.setMensaje("mensaje enviado correctamente a kafka");
        }catch(JsonProcessingException ex){
            responseMonedero.setMensaje("Ocurrrio un error en el envio de mensaje a kafka");
            log.error("error "+ex.getMessage());
        }
        return responseMonedero;

    }
}