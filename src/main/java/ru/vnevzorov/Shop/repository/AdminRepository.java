package ru.vnevzorov.Shop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.user.Admin;

@Repository
@Transactional
public interface AdminRepository extends AbstractUserRepository<Admin> {



}
