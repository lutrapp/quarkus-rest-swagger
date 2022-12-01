package acc.br.quarkus.swagger.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import acc.br.quarkus.swagger.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
