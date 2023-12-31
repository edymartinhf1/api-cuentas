package com.bootcamp.bank.cuentas.strategy.cliente;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PerfilPersonalSstrategy implements PerfilClienteStrategy {
    @Override
    public PerfilInfo configurarPerfil(Cliente cliente) {
        PerfilInfo perfil= new PerfilInfo();
        if (cliente.getTipoCli().equals("PER")){
            List<String> tiposCuentasPermitidas = List.of("AHO","CTE","PZF","VIP");
            perfil.setPerfiles(tiposCuentasPermitidas);
            perfil.setCliente(cliente);
        }
        return perfil;
    }
}
