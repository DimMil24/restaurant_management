package com.dimitris.restaurant_management;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.repositories.*;
import com.dimitris.restaurant_management.services.RestaurantService;
import com.dimitris.restaurant_management.services.RoleService;
import com.dimitris.restaurant_management.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class RestaurantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(ProductRepository productRepository,
//							 RestaurantService restaurantService,
//							 UserService userService,
//							 RoleService roleService,
//							 PasswordEncoder passwordEncoder) {
//		return args -> {
//
//			roleService.createRole("ROLE_ADMIN");
//			roleService.createRole("ROLE_USER");
//			roleService.createRole("ROLE_OWNER");
//
//			var user1 = userService.createUser("Tassos", passwordEncoder.encode("123"));
//			var user2 = userService.createUser("Atzar", passwordEncoder.encode("123"));
//			var user3 = userService.createUser("Admin", passwordEncoder.encode("123"));
//
//			roleService.addRolesToUser(List.of("ROLE_OWNER", "ROLE_ADMIN"), user1);
//			roleService.addRolesToUser(List.of("ROLE_OWNER", "ROLE_ADMIN"),user2);
//			roleService.addRolesToUser(List.of("ROLE_ADMIN"),user3);
//
//			var restaurant1 = restaurantService.addRestaurant("Tassos","Magazi tou Tassou");
//			var restaurant2 = restaurantService.addRestaurant("Atzar","Atzar's");
//
//			userService.associateUserToRestaurant(user1,restaurant1);
//			userService.associateUserToRestaurant(user2,restaurant2);
//
//
//			Product product11 = new Product("Patates", BigDecimal.valueOf(5), ProductCategory.APPETIZER,true,null,restaurant1);
//			Product product12 = new Product("Kotopoulo", BigDecimal.valueOf(15), ProductCategory.APPETIZER,true,null,restaurant1);
//			Product product21 = new Product("Coca Cola", BigDecimal.valueOf(2), ProductCategory.SOFT_DRINK,true,null,restaurant2);
//			Product product22 = new Product("Profiterol", BigDecimal.valueOf(10.5), ProductCategory.DESSERT,true,null,restaurant2);
//
//			productRepository.save(product11);
//			productRepository.save(product12);
//			productRepository.save(product21);
//			productRepository.save(product22);
//
//
//		};
//	}

}
