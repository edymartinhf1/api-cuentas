package com.bootcamp.bank.cuentas.service.impl;

import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;


@ExtendWith(MockitoExtension.class)
@Log4j2
class CuentaServiceImplTest {
    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImpl cuentaServiceI;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        CuentaDao expected = new CuentaDao();
        expected.setTipoCuenta("PER");
        expected.setNumeroCuenta("456-789-456");

        CuentaDao cuenta = new CuentaDao();
        cuenta.setTipoCuenta("PER");
        cuenta.setNumeroCuenta("456-789-456");

        Mockito.when( cuentaRepository.save(Mockito.any(CuentaDao.class)) )
                .thenReturn( Mono.just(cuenta) );
        log.info("step 1"+cuenta.toString());
        CuentaDao actualiza=new CuentaDao();
        actualiza.setTipoCuenta("PER");
        actualiza.setNumeroCuenta("456-789-456");
        String numeroCuenta="456-789-456";
        Mono<CuentaDao> actual0 = cuentaRepository.save(actualiza);
        CuentaDao actual=actual0.block();
        log.info("step 2"+actual.toString());

        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getNumeroCuenta(),actual.getNumeroCuenta());
        Assertions.assertEquals(expected.getTipoCuenta(),actual.getTipoCuenta());
    }

    @Test
    void findAll() {
    }

    @Test
    @DisplayName("Test for buscar por Id ")
    void findById() {
        CuentaDao espero = new CuentaDao();
        espero.setId("1");
        espero.setTipoCuenta("PER");
        espero.setNumeroCuenta("456-789-456");

        CuentaDao cuenta = new CuentaDao();
        cuenta.setId("1");
        cuenta.setTipoCuenta("PER");
        cuenta.setNumeroCuenta("456-789-456");

        Mockito.when( cuentaRepository.findById("1") )
                .thenReturn(Mono.just(cuenta));

        Mono<CuentaDao> recibo1 = cuentaRepository.findById("1");
        CuentaDao recibo = recibo1.block();

        Assertions.assertEquals(espero.getId(),recibo.getId());
        Assertions.assertEquals(espero.getNumeroCuenta(),recibo.getNumeroCuenta());


    }

    @Test
    void findByNumeroCuenta() {
    }

    @Test
    void findByIdCliente() {
    }

    @Test
    void findByIdClienteAndTipoCuenta() {
    }

    @Test
    @DisplayName("Test for update")
    void update() {
        CuentaDao expected = new CuentaDao();
        expected.setTipoCuenta("PER");
        expected.setNumeroCuenta("456-789-456");

        CuentaDao cuenta = new CuentaDao();
        cuenta.setTipoCuenta("PER");
        cuenta.setNumeroCuenta("456-789-456");

        Mockito.when( cuentaRepository.save(Mockito.any(CuentaDao.class)) )
                .thenReturn( Mono.just(cuenta) );
        log.info("step 1"+cuenta.toString());
        CuentaDao actualiza=new CuentaDao();
        actualiza.setTipoCuenta("PER");
        actualiza.setNumeroCuenta("456-789-456");
        String numeroCuenta="456-789-456";
        Mono<CuentaDao> actual0 = cuentaRepository.save(actualiza);
        CuentaDao actual=actual0.block();
        log.info("step 2"+actual.toString());

        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getNumeroCuenta(),actual.getNumeroCuenta());
        Assertions.assertEquals(expected.getTipoCuenta(),actual.getTipoCuenta());
    }


    /*
    @Test
    void delete() {
    }
    */
}