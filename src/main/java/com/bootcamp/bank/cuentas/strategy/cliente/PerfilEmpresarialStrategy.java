package com.bootcamp.bank.cuentas.strategy.cliente;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PerfilEmpresarialStrategy implements PerfilClienteStrategy {

    @Override
    public PerfilInfo configurarPerfil(Cliente cliente) {
        PerfilInfo perfil= new PerfilInfo();
        if (cliente.getTipoCli().equals("EMP")){
            List<String> tiposCuentasPermitidas = List.of("CTE","PYM");
            perfil.setPerfiles(tiposCuentasPermitidas);
            perfil.setCliente(cliente);
        }
        return perfil;
    }
}
