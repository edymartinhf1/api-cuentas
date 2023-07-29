package com.bootcamp.bank.cuentas.controller;

import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.Response;
import com.bootcamp.bank.cuentas.service.MonederoMovilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase Monedero Movil
 */
@RestController
@RequestMapping("/cuentas/monedero")
@Log4j2
@RequiredArgsConstructor
public class MonederoMovilController {

    private final MonederoMovilService monederoMovilService;

    /**
     * Permite generar un monedero movil virtual
     * @param monederoMovilPost
     * @return
     */
    @PostMapping
    public Response createPocketBook(@RequestBody MonederoMovilPost monederoMovilPost) {
        return monederoMovilService.registrarMonedero(monederoMovilPost);

    }

}
