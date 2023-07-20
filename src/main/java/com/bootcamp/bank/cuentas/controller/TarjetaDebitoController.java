package com.bootcamp.bank.cuentas.controller;

import com.bootcamp.bank.cuentas.model.TarjetaDebito;
import com.bootcamp.bank.cuentas.model.TarjetaDebitoCuenta;
import com.bootcamp.bank.cuentas.model.TarjetaDebitoPost;
import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoCuentaDao;
import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoDao;
import com.bootcamp.bank.cuentas.service.TarjetaDebitoServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller Tarjeta debito
 */
@RestController
@RequestMapping("/tarjeta/debito")
@Log4j2
@RequiredArgsConstructor
public class TarjetaDebitoController {

    private final TarjetaDebitoServiceI tarjetaDebitoServiceI;

    @PostMapping
    public Mono<TarjetaDebito> createCardDebit(@RequestBody TarjetaDebitoPost tarjetaDebitoPost) {
       return tarjetaDebitoServiceI.createCardDebit(this.fromTarjetaDebitoPostToTarjetaDebitoDao(tarjetaDebitoPost))
               .map(this::fromTarjetaDebitoDaoToTarjetaDebito);
    }

    /**
     * Permite asociar una cuenta bancaria a una tarjeta de debito
     * @param tarjetaDebitoPost
     * @return
     */
    @PostMapping("/asociacion")
    public Mono<TarjetaDebitoCuenta> associateCardDebit(@RequestBody TarjetaDebitoPost tarjetaDebitoPost) {
        return tarjetaDebitoServiceI.associateCardDebit(this.fromTarjetaDebitoPostToTarjetaDebitoDao(tarjetaDebitoPost))
                .map(this::fromTarjetaDebitoCuentaDaoToTarjetaDebitoCuenta);
    }

    /**
     * Permite obtener Tarjetas de debito por id cliente
     * @param idCliente
     * @return
     */
    @GetMapping("/{idCliente}")
    public Flux<TarjetaDebito> getCardDebitPorIdClient(@PathVariable String idCliente) {
        return tarjetaDebitoServiceI.getCardDebitPorIdCliente(idCliente)
                .map(this::fromTarjetaDebitoDaoToTarjetaDebito);
    }

    /**
     * Permite Obtener numeros de cuenta por numero de tarjeta de debito
     * @param numeroTarjetaDebito
     * @return
     */
    @GetMapping("/cuentas/{numeroTarjetaDebito}")
    public Flux<TarjetaDebitoCuenta> getNumberAccountsPorCardDebit(@PathVariable String numeroTarjetaDebito) {
        return tarjetaDebitoServiceI.getNumberAccountsPorCardDebit(numeroTarjetaDebito)
                .map(this::fromTarjetaDebitoCuentaDaoToTarjetaDebitoCuenta);
    }

    private TarjetaDebito fromTarjetaDebitoDaoToTarjetaDebito(TarjetaDebitoDao tarjetaDebitoDao) {
        TarjetaDebito tarjetaDebito = new TarjetaDebito();
        BeanUtils.copyProperties(tarjetaDebitoDao,tarjetaDebito);
        return tarjetaDebito;
    }

    private TarjetaDebitoDao fromTarjetaDebitoPostToTarjetaDebitoDao(TarjetaDebitoPost tarjetaDebitoPost) {
        TarjetaDebitoDao tarjetaDebitoDao = new TarjetaDebitoDao();
        BeanUtils.copyProperties(tarjetaDebitoPost,tarjetaDebitoDao);
        return tarjetaDebitoDao;
    }

    private TarjetaDebitoCuenta fromTarjetaDebitoCuentaDaoToTarjetaDebitoCuenta(TarjetaDebitoCuentaDao tarjetaDebitoCuentaDao) {
        TarjetaDebitoCuenta tarjetaDebitoCuenta = new TarjetaDebitoCuenta();
        BeanUtils.copyProperties(tarjetaDebitoCuentaDao,tarjetaDebitoCuenta);
        return tarjetaDebitoCuenta;
    }



}
