package com.bootcamp.bank;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import com.bootcamp.bank.cuentas.service.impl.CuentaServiceImpl;
import com.bootcamp.bank.cuentas.strategy.cliente.PerfilEmpresarialStrategy;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mongodb.assertions.Assertions.assertTrue;


@SpringBootTest
public class PerfilStrategyTest {

    @Mock
    CuentaRepository cuentaRepository;

    @InjectMocks
    PerfilEmpresarialStrategy perfilEmpresarialStrategy;

    @InjectMocks
    CuentaServiceImpl cuentaServiceImpl;

    @Before
    public  void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPerfilEmpresarial(){
        Cliente cliente=new Cliente();
        cliente.setId("1");
        cliente.setNombre("EDUARDO GUZMAN");
        cliente.setTipoCli("EMP");
        PerfilInfo perfil= perfilEmpresarialStrategy.configurarPerfil(cliente);
        assertTrue(perfil.getPerfiles().contains("CTE"));
    }





}
