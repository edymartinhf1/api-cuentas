package com.bootcamp.bank.cuentas.controller;

import com.bootcamp.bank.cuentas.model.Cuenta;
import com.bootcamp.bank.cuentas.model.TarjetaDebitoPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/tarjeta")
@Slf4j
@RequiredArgsConstructor
public class TarjetaDebitoController {

    @PostMapping
    public Mono<Cuenta> createCardDebit(@RequestBody TarjetaDebitoPost tarjetaDebitoPost) {
       return null;
    }



}
