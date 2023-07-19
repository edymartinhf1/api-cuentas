package com.bootcamp.bank.cuentas.clients;

import com.bootcamp.bank.cuentas.model.CreditoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ClientApiCreditos {

    @Autowired
    @Qualifier("clientCreditos")
    private WebClient webClient;

    /**
     * Permite obtener creditos de cliente del api-creditos
     * @param idCliente
     * @return
     */
    public Flux<CreditoProducto> getCreditosPorIdClientAndTipoCredito(String idCliente,String tipoCredito) {
        return webClient.get()
                .uri("/creditos/client/" + idCliente+"/tipo/"+tipoCredito)
                .retrieve()
                .bodyToFlux(CreditoProducto.class);
    }

    public Flux<CreditoProducto> getCreditosPorIdCliente(String idCliente) {
        return webClient.get()
                .uri("/creditos/cliente/" + idCliente)
                .retrieve()
                .bodyToFlux(CreditoProducto.class);
    }

}

