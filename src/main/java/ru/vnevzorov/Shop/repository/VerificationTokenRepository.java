package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vnevzorov.Shop.model.VerificationToken;
import ru.vnevzorov.Shop.model.user.AbstractUser;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

}
