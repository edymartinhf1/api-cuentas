package com.bootcamp.bank.cuentas.producer;

import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Clase Sender Monedero Movil via kafka
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaMessageSender {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Permite enviar informacion de monedero a kafka
     * @param monederoMovilPost
     */
    public Response sendMonedero(MonederoMovilPost monederoMovilPost) {
        log.info("send message"+monederoMovilPost.toString());
        Response response=new Response();
        try {
            String monederoAsMessage = objectMapper.writeValueAsString(monederoMovilPost);
            kafkaTemplate.send("monederomovil", monederoAsMessage);
            response.setCodigo("01");
            response.setMensaje("mensaje enviado correctamente a kafka");
        }catch(JsonProcessingException ex){
            response.setMensaje("Ocurrrio un error en el envio de mensaje a kafka");
            log.error("error "+ex.getMessage());
        }
        return  response;

    }


}
