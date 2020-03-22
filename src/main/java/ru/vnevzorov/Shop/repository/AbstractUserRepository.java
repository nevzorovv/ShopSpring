package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vnevzorov.Shop.model.user.AbstractUser;

/*@NoRepositoryBean*/
@Repository
public interface AbstractUserRepository<T extends AbstractUser> extends CrudRepository<T, Long> {



}
