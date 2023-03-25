package io.staxex.api;

import io.staxex.api.authentication.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(RoleRepository roleRepository, BankAccountRepository bankAccountRepository, TraderRepository traderRepository) {
//		Role TRADER = roleRepository.save(new Role(ERole.ROLE_TRADER));
//		Role ADMIN = roleRepository.save(new Role(ERole.ROLE_TRADER));
//
//		String encodedPassWord =  new BCryptPasswordEncoder().encode("kwesijoe1");
//
//		Set<Role> role = new HashSet<>();
//		role.add(ADMIN);
//
//		Trader ADMINUSER = new Trader("joe","larbi","joe@here.com",encodedPassWord);
//		ADMINUSER.setRoles(role);
//
//		BankAccount bankAccount = new BankAccount(ADMINUSER, "Ecobank", "Kwesi Fynn","123456789");
//
//
//
//		return args -> {
//			roleRepository.save(new Role(ERole.ROLE_TRADER));
//			roleRepository.save(new Role(ERole.ROLE_ADMIN));
//
//			traderRepository.save(ADMINUSER);
//
//			bankAccountRepository.save(bankAccount);
//
//		};
//	}

//	@Bean
//	CommandLineRunner commandLineRunner(RoleRepository roleRepository){
//		return args -> {
//			roleRepository.save(new Role(ERole.ROLE_TRADER));
//			roleRepository.save(new Role(ERole.ROLE_ADMIN));
//		};
//	}
}
