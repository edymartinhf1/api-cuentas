package com.bootcamp.bank.cuentas.strategy.cuentas;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class CuentaAhorroStrategy implements CuentasStrategy{

    /**
     * Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo
     * Si se va a crear una cuenta de ahorro no deberia de pre-existir una cuenta previa
     * @param cuentaRepository
     * @param clientApiClientes
     * @param clientApiCreditos
     * @param cuentaDao
     * @return
     */
    @Override
    public Mono<Boolean> verifyCuenta(CuentaRepository cuentaRepository,
                                      ClientApiClientes clientApiClientes,
                                      ClientApiCreditos clientApiCreditos,
                                      CuentaDao cuentaDao,
                                      PerfilInfo perfilInfo
    ) {

        String idCliente  = cuentaDao.getIdCliente();
        String tipoCuenta = cuentaDao.getTipoCuenta();

        if (!perfilInfo.getPerfiles().contains(cuentaDao.getTipoCuenta().trim())){
            log.info("No es un tipo de cuenta permitido "+cuentaDao.getTipoCuenta()+" para el tipo de cliente "+cuentaDao.getIdCliente());
            return Mono.just(Boolean.FALSE);
        }

        return clientApiCreditos.getCreditosDeudaPorIdCliente(idCliente)
                .collectList()
                .flatMap(listDeudas->{
                    if (!listDeudas.isEmpty()){
                        log.info(" contiene productos de credito con deuda con deuda");
                        return Mono.just(Boolean.FALSE);
                    }
                    log.info(" list deuda "+listDeudas.toString());
                    return cuentaRepository.findByIdClienteAndTipoCuenta(idCliente,tipoCuenta)
                            .collectList()
                            .flatMap(list -> {
                                log.info(" cuentas de tipo "+tipoCuenta +" obtenidos ="+list.size());
                                log.info(" el cliente : " + idCliente + " ya tiene cuentas de tipo :" + tipoCuenta +" registros "+list.size());
                                return !list.isEmpty() ? Mono.just(Boolean.FALSE) : Mono.just(Boolean.TRUE);
                            });
                });









    }
}

