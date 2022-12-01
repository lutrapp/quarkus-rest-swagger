package acc.br.quarkus.swagger.service;

import java.util.List;

import acc.br.quarkus.swagger.entity.ContaCorrente;
import acc.br.quarkus.swagger.exception.ContaCorrenteNotFoundException;

public interface ContaCorrenteService {

	ContaCorrente getContaCorrenteById(long id) throws ContaCorrenteNotFoundException;

    List<ContaCorrente> getAllContaCorrente();

    ContaCorrente updateContaCorrente(long id, ContaCorrente contaCorrente) throws ContaCorrenteNotFoundException;

    ContaCorrente saveContaCorrente(ContaCorrente contaCorrente);

    void deleteContaCorrente(long id) throws ContaCorrenteNotFoundException;
}

