package acc.br.quarkus.swagger.repository;

import javax.enterprise.context.ApplicationScoped;

import acc.br.quarkus.swagger.entity.ContaCorrente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ContaCorrenteRepository implements PanacheRepository<ContaCorrente> {
}
