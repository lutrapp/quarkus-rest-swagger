package acc.br.quarkus.swagger.service.impl;

import acc.br.quarkus.swagger.entity.ContaCorrente;
import acc.br.quarkus.swagger.entity.User;
import acc.br.quarkus.swagger.exception.ContaCorrenteNotFoundException;
import acc.br.quarkus.swagger.exception.UserNotFoundException;
import acc.br.quarkus.swagger.repository.ContaCorrenteRepository;
import acc.br.quarkus.swagger.repository.UserRepository;
import acc.br.quarkus.swagger.service.ContaCorrenteService;
import acc.br.quarkus.swagger.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ContaCorrenteServiceImpl implements ContaCorrenteService {

	private final ContaCorrenteRepository contaCorrenteRepository;
	
	@Inject
	public ContaCorrenteServiceImpl(ContaCorrenteRepository contaCorrenteRepository) {
		this.contaCorrenteRepository = contaCorrenteRepository;
	}
	
	@Override
	public ContaCorrente getContaCorrenteById(long id) throws ContaCorrenteNotFoundException {
		return contaCorrenteRepository.findByIdOptional(id).orElseThrow(() -> new ContaCorrenteNotFoundException("Este usuário não existe!"));
	}

	@Override
	public List<ContaCorrente> getAllContaCorrente() {
		return contaCorrenteRepository.listAll();
	}

	@Transactional
	@Override
	public ContaCorrente updateContaCorrente(long id, ContaCorrente contaCorrente)
			throws ContaCorrenteNotFoundException {
		ContaCorrente existingContaCorrente = getContaCorrenteById(id);
		existingContaCorrente.setAgencia(contaCorrente.getAgencia());
		existingContaCorrente.setIdCliente(contaCorrente.getIdCliente());
		existingContaCorrente.setNumero(contaCorrente.getNumero());
		existingContaCorrente.setSaldo(contaCorrente.getSaldo());
		existingContaCorrente.setId(contaCorrente.getId());
		contaCorrenteRepository.persist(existingContaCorrente);
        return existingContaCorrente;
	}

	@Transactional
	@Override
	public ContaCorrente saveContaCorrente(ContaCorrente contaCorrente) {
		contaCorrenteRepository.persistAndFlush(contaCorrente);
		return contaCorrente;
	}

	@Transactional
	@Override
	public void deleteContaCorrente(long id) throws ContaCorrenteNotFoundException {
		contaCorrenteRepository.delete(getContaCorrenteById(id));
		
	}

   
}
