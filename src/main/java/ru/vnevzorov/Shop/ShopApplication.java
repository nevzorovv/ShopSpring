package ru.vnevzorov.Shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.User;
import ru.vnevzorov.Shop.repository.OrderRepository;
import ru.vnevzorov.Shop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Override
	public void run(String... args) throws Exception {

/*		User user1 = new User("firstUser", "123", "name1");
		User user2 = new User("secondUser", "123", "name2");
		User user3 = new User("thirdUser", "123", "name3");

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);

		List<User> list = (List<User>) userRepository.findAll();
		list.forEach(System.out::println);

		User dbUser = userRepository.findById(2L).get();

		orderRepository.save(new Order("A12", LocalDateTime.now(), user1));
		orderRepository.save(new Order("A13", LocalDateTime.now(), user1));
		orderRepository.save(new Order("A14", LocalDateTime.now(), dbUser));

		List<Order> orders = (List<Order>) orderRepository.findAll();
		orders.forEach(System.out::println);

		User dbUser2 = userRepository.findByLogin("thirdUser");
		System.out.println("dbUser2 = " + dbUser2);

		//dbUser2.getOrders().forEach(System.out::println);*/
	}
}
