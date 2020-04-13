package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.User;

import java.util.Optional;

/*@NoRepositoryBean*/
@Repository
public interface AbstractUserRepository<T extends AbstractUser> extends CrudRepository<T, Long> {

    AbstractUser findByLogin(String login);

}
