package com.bootcamp.bank.cuentas.controller;

import com.bootcamp.bank.cuentas.model.MonederoP2PPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;
import com.bootcamp.bank.cuentas.service.MonederoP2PService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuentas/monedero/p2p")
@Log4j2
@RequiredArgsConstructor
public class MonederoP2PController {

    private final MonederoP2PService monederoP2PService;

    @PostMapping
    public ResponseMonedero createPocketBook(@RequestBody MonederoP2PPost monederoP2PPost) {
        return monederoP2PService.registrarMonedero(monederoP2PPost);

    }
}
