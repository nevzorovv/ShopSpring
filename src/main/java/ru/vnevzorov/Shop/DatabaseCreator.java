package ru.vnevzorov.Shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vnevzorov.Shop.enumeration.Status;
import ru.vnevzorov.Shop.model.*;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Admin;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.*;
import ru.vnevzorov.Shop.security.UserDetailsImpl;
import ru.vnevzorov.Shop.service.user.AbstractUserService;
import ru.vnevzorov.Shop.service.user.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Component
@Profile("dev")
public class DatabaseCreator {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AbstractUserService abstractUserService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {

        Category elDevicesCategory = new Category("Electronic Devices");
        Category goodForHouseCategory = new Category("Goods for house");
        Category booksCategory = new Category("Books");
        categoryRepository.save(elDevicesCategory);
        categoryRepository.save(goodForHouseCategory);
        categoryRepository.save(booksCategory);

        categoryRepository.findAll().forEach(log::info);

        Discount discount1 = new Discount(10.0, "%");
        Discount discount2 = new Discount(15.0, "%");
        Discount discount3 = new Discount(10.0, "$");
        Discount discount4 = new Discount(30.0, "$");
        Discount discount5 = new Discount(1.0, "$");
        discountRepository.save(discount1);
        discountRepository.save(discount2);
        discountRepository.save(discount3);
        discountRepository.save(discount4);
        discountRepository.save(discount5);

        discountRepository.findAll().forEach(log::info);

        Payment cashPayment = new Payment("cash");
        Payment onlinePayment = new Payment("online");
        Payment debitPayment = new Payment("debit");
        paymentRepository.save(cashPayment);
        paymentRepository.save(onlinePayment);
        paymentRepository.save(debitPayment);

        Shipment courierShipment = new Shipment("courier");
        Shipment pickpointShipment = new Shipment("pickpoint");
        Shipment pickupShipment = new Shipment("pickup");
        shipmentRepository.save(courierShipment);
        shipmentRepository.save(pickpointShipment);
        shipmentRepository.save(pickupShipment);

        Supplier supplier1 = new Supplier("supplier1");
        Supplier supplier2 = new Supplier("supplier2");
        Supplier supplier3 = new Supplier("supplier3");
        supplierRepository.save(supplier1);
        supplierRepository.save(supplier2);
        supplierRepository.save(supplier3);

        Product iPhone10Product = new Product("Apple", "iPhone 10", 80000.0, elDevicesCategory, discount1);
        iPhone10Product.getSuppliers().add(supplier1);
        iPhone10Product.getSuppliers().add(supplier3);
        Product matebookXProProduct = new Product("Huawei", "Matebook X Pro", 100000.0, elDevicesCategory, discount2);
        Product evgeniOneginProduct = new Product("Pushkin", "Evgeni Onegin", 500.0, booksCategory, discount5);
        productRepository.save(iPhone10Product);
        productRepository.save(matebookXProProduct);
        productRepository.save(evgeniOneginProduct);

        User user1 = new User("Vladimir", passwordEncoder.encode("123"), "Vladimir", "Nevzorov", LocalDate.of(2000, 1, 1), "pupkin.1994@yandex.ru", "testUserField1", "testUserField2");
        user1.setEnabled(true);
        user1.setPasswordChanged(true);
        User user2 = new User("secondUser", passwordEncoder.encode("123"), "name1", "lastName1", LocalDate.of(2000, 1, 1), "testEmail", "testUserField1", "testUserField2");
        user2.setEnabled(true);
        user2.setPasswordChanged(true);
        Admin admin1 = new Admin("Vladimir_admin", passwordEncoder.encode("123"), "Vladimir", "Nevzorov", LocalDate.of(2000, 1, 1), "pupkin.1994@yandex.ru", "testAdminField1", "testAdminField2", true);
        admin1.setEnabled(true);
        admin1.setPasswordChanged(true);
        admin1.setRole(Role.ADMIN);

        userRepository.save(user1);
        userRepository.save(user2);
        abstractUserService.save(admin1);

        //Test
        AbstractUser abstractUser = abstractUserService.getByLogin("firstUser");
        AbstractUser abstractUserAnon = abstractUserService.getByLogin("AnonymousUser");

        //TODO проверить функцию кэша
        User user1test = userService.getUserByLogin("firstUser");
        User user2test = userService.getUserByLogin("secondUser");
        User user1test2 = userService.getUserByLogin("firstUser");

        Iterable<AbstractUser> users = abstractUserService.getAll();
        Iterable<AbstractUser> users1 = abstractUserService.getAll();

        Order order1 = new Order("A12", LocalDateTime.now(), user1, cashPayment, 1.0, pickupShipment, Status.CREATED);
        Order order2 = new Order("A13", LocalDateTime.now(), user1, onlinePayment, 80000.0, pickpointShipment, Status.CREATED);
        Order order3 = new Order("A14", LocalDateTime.now(), user2, debitPayment, 100000.0, courierShipment, Status.CREATED);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

    }


/*
    @Autowired
    ComputerRepository computerRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    TVRepository tvRepository;

    @PostConstruct
    private void init() {
        Computer comp1 = new Computer("laptop", "Dell", "model_1", "Windows", 8, 50000);
        Computer comp2 = new Computer("PC", "Dell", "model_2", "Windows", 16, 65000);
        Computer comp3 = new Computer("PC", "Asus", "model_3", "MacOS", 8, 30000);

        Phone phone1 = new Phone("Xiaomi", "model_1", "Android", 6.3, 10000);
        Phone phone2 = new Phone("Apple", "model_2", "IOS", 5.2, 80000);
        Phone phone3 = new Phone("Samsung", "model_3", "Android", 5.8, 40000);

        TV tv1 = new TV("smartTV", "Samsung", "model_1", 40.0, 40000);
        TV tv2 = new TV("smartTV", "Philips", "model_2", 53.0, 65000);
        TV tv3 = new TV("smartTV", "HP", "model_3", 38.0, 35000);

        computerRepository.save(comp1);
        computerRepository.save(comp2);
        computerRepository.save(comp3);

        phoneRepository.save(phone1);
        phoneRepository.save(phone2);
        phoneRepository.save(phone3);

        tvRepository.save(tv1);
        tvRepository.save(tv2);
        tvRepository.save(tv3);
    }*/
}
