package com.bootcamp.bank;

import com.bootcamp.bank.cuentas.service.CuentaServiceI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApiCuentasApplicationTests {
	@Autowired
	private CuentaServiceI cuentaServiceI;
	@Test
	void contextLoads() {
		assertThat(cuentaServiceI).isNotNull();
	}

}
