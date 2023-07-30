package com.bootcamp.bank.cuentas.clients;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.ClientePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientApiClientes {
    @Autowired
    @Qualifier("clientClientes")
    private WebClient webClient;

    /**
     * Permite obtener informacion de cliente del api-clientes
     * @param idCliente
     * @return
     */

    public Mono<Cliente> getClientes(String idCliente) {
        return webClient.get()
                .uri("/clientes/" + idCliente)
                .retrieve()
                .bodyToMono(Cliente.class);
    }

    public Mono<Cliente> saveCliente(ClientePost cliente) {
        return webClient.post()
                .uri("/clientes/")
                .body(BodyInserters.fromValue(cliente))
                .retrieve()
                .bodyToMono(Cliente.class);
    }
}
