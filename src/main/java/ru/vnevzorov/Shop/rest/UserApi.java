package ru.vnevzorov.Shop.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.rest.dto.user.AbstractUserRestDTO;
import ru.vnevzorov.Shop.service.user.AbstractUserService;

@RestController
@RequestMapping("api/users")
public class UserApi {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private AbstractUserService abstractUserService;

    @GetMapping("/{id}")
    public AbstractUser getOne(@PathVariable Long id) {
        return abstractUserService.getById(id);
    }

    // TODO написать DTO для каждого юзера. Что-то типо фабрики
    @PutMapping(value = "/passwordUpdate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AbstractUserRestDTO passwordUpdate(@PathVariable Long id, @RequestBody AbstractUserRestDTO user) {
        log.info("PUT: /api/users/passwordUpdate" + user.getId());

        abstractUserService.changePassword(user.getLogin(), user.getPassword());

        return user;
    }

    /*@PatchMapping
    public void changePassword(@RequestBody Map<String, String> passwordData) {
        System.out.println("passwordData = " + passwordData);
    }*/

}
