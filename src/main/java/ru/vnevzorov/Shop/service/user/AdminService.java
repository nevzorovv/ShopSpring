package ru.vnevzorov.Shop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.user.Admin;
import ru.vnevzorov.Shop.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Iterable<Admin> getAllWithSendReportTrue() {
        return adminRepository.findAllBySendReportsIsTrue();
    }

}
