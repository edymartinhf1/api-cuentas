package com.bootcamp.bank.cuentas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientsConfig {
    @Bean("clientClientes")
    public WebClient webClient() {
        return WebClient.create("http://localhost:8081"); // Cambia la URL y el puerto según tu API REST local
    }

    @Bean(name = "clientCuentas")
    public WebClient webClientCuentas() {
        return WebClient.create("http://localhost:8083");
    }

    @Bean(name = "clientCreditos")
    public WebClient webClientCreditos() {
        return WebClient.create("http://localhost:8082");
    }
}