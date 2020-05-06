package ru.vnevzorov.Shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.vnevzorov.Shop.repository.OrderRepository;
import ru.vnevzorov.Shop.repository.UserRepository;
import ru.vnevzorov.Shop.service.TestEntityService;
import javax.validation.ConstraintViolationException;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableJpaAuditing
public class ShopApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	TestEntityService testEntityService;

	@Override
	public void run(String... args) throws Exception {
		/*//testEntityService.test();
		try {
			testEntityService.collectionValidate(List.of("a", " ", "b"));
			testEntityService.testValidate(null);
		} catch (ConstraintViolationException e) {
			e.getConstraintViolations().forEach(System.out::println);
		}

*/
	}
}
