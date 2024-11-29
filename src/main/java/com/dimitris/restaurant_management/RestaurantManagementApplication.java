package com.dimitris.restaurant_management;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RestaurantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerOrderRepository customerOrderRepository,
							 ProductRepository productRepository,
							 RestaurantRepository restaurantRepository,
							 UserRepository userRepository,
							 PasswordEncoder passwordEncoder) {
		return args -> {

			User tassos = new User(null,"Tassos", passwordEncoder.encode("123"), UserRoles.ROLE_OWNER,null);
			User atzar = new User(null,"Atzar",passwordEncoder.encode("123"),UserRoles.ROLE_OWNER,null);
			User admin = new User(null,"Admin",passwordEncoder.encode("123"),UserRoles.ROLE_ADMIN,null);

			userRepository.save(tassos);
			userRepository.save(atzar);
			userRepository.save(admin);

			Restaurant restaurant1 = new Restaurant(null,"Tassos",true,"Tassoss",tassos);
			Restaurant restaurant2 = new Restaurant(null,"Atzar",true,"Atzar's",atzar);
			restaurantRepository.save(restaurant1);
			restaurantRepository.save(restaurant2);

			tassos.setRestaurant(restaurant1);
			atzar.setRestaurant(restaurant2);
			userRepository.save(tassos);
			userRepository.save(atzar);

			Product product11 = new Product("Patates", BigDecimal.valueOf(5), ProductCategory.APPETIZER,true,null,restaurant1);
			Product product12 = new Product("Kotopoulo", BigDecimal.valueOf(15), ProductCategory.APPETIZER,true,null,restaurant1);
			Product product21 = new Product("Coca Cola", BigDecimal.valueOf(2), ProductCategory.SOFT_DRINK,true,null,restaurant2);
			Product product22 = new Product("Profiterol", BigDecimal.valueOf(10.5), ProductCategory.DESSERT,true,null,restaurant2);

			productRepository.save(product11);
			productRepository.save(product12);
			productRepository.save(product21);
			productRepository.save(product22);


		};
	}

}
