package com.bootcamp.bank.cuentas.controller;

import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.CuentaPost;
import com.bootcamp.bank.cuentas.model.Cuenta;
import com.bootcamp.bank.cuentas.service.CuentaServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")
@Slf4j
@RequiredArgsConstructor
public class CuentasController {

    private final CuentaServiceI cuentasService;

    /**
     * Permite crear una cuenta bancaria
     * @param cuenta
     * @return
     */
    @PostMapping
    public Mono<Cuenta> createAccount(@RequestBody CuentaPost cuenta) {
        return cuentasService.save(this.fromCuentaPostToCuentaDao(cuenta))
                .map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite Obtener todas las cuentas bancarias
     * @return
     */
    @GetMapping
    public Flux<Cuenta> getAll(){

        return cuentasService.findAll().map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite obtener cuenta bancaria por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Cuenta> findById(@PathVariable String id){

        return cuentasService.findById(id).map(this::fromCuentaDaoToCuentaDto);
    }


    /**
     * Permite obtener una cuenta bancaria por numero de cuenta
     * @param numeroCuenta
     * @return
     */
    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public Mono<Cuenta> findByNumeroCuenta(@PathVariable String numeroCuenta){
        log.info("findByNumeroCuenta -> numero cuenta :"+numeroCuenta);
        return cuentasService.findByNumeroCuenta(numeroCuenta).map(this::fromCuentaDaoToCuentaDto);
    }


    /**
     * Permite obtener listado de cuentas bancarias por id cliente
     * @param idCliente
     * @return
     */
    @GetMapping("/cliente/{idCliente}")
    public Flux<Cuenta> findByIdCliente(@PathVariable String idCliente){

        return cuentasService.findByIdCliente(idCliente).map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite actualizar cuenta
     * @param numeroCuenta
     * @return
     */
    @PutMapping("/{numeroCuenta}")
    public Mono<Cuenta> updateAccount(@RequestBody CuentaPost cuenta, @PathVariable String numeroCuenta) {
        return cuentasService.update(this.fromCuentaPostToCuentaDao(cuenta),numeroCuenta)
                .map(this::fromCuentaDaoToCuentaDto);
    }


    /**
     * Permite borrar una cuenta
     * @param numeroCuenta
     * @return
     */
    @DeleteMapping("/{numeroCuenta}")
    public Mono<Void> deleteAccount(@PathVariable String numeroCuenta) {
        return cuentasService.delete(numeroCuenta);

    }

    private Cuenta fromCuentaDaoToCuentaDto(CuentaDao cuentaDao) {
        Cuenta cuenta = new Cuenta();
        BeanUtils.copyProperties(cuentaDao,cuenta);
        return cuenta;
    }

    private CuentaDao fromCuentaPostToCuentaDao(CuentaPost cuentaPost) {
        CuentaDao cuenta = new CuentaDao();
        BeanUtils.copyProperties(cuentaPost,cuenta);
        return cuenta;
    }
}
