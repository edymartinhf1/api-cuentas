package com.bootcamp.bank;

import com.bootcamp.bank.cuentas.controller.CuentasController;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.service.CuentaServiceI;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApiCuentasApplicationTests {
	@Autowired
	private CuentaServiceI cuentaServiceI;

	@Test
	void contextLoads() {
		assertThat(cuentaServiceI).isNotNull();
	}

	@Test
	void findAllAccount() {
		// When
		List<CuentaDao> cuentas = this.cuentaServiceI.findAll().collectList().block();
		// Then
		assertThat(cuentas.size()>0);
	}

}
