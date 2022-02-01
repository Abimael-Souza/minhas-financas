package br.com.dev.minhasFinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dev.minhasfinancas.model.entity.UserEntity;
import br.com.dev.minhasfinancas.model.repository.UserRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void checkEmailExists() {

		String email = "teste@gmail.com";
		
		//Crio o usuário na base;
		UserEntity user = UserEntity.builder().name("Abimael").email(email).build();
		entityManager.persist(user);
		
		//Verifico se existe o usuário na base e valido;
		Assertions.assertThat(userRepository.existsByEmail(email)).isTrue();
	}
	
	@Test
	public void returnFalseNotFoundEmailRegisteredToUser() {
		
		Assertions.assertThat(userRepository.existsByEmail("teste@gmail.com")).isFalse();
	}

	@Test
	public void mustPersistUserDataBase() {
		
		UserEntity user = UserEntity
				.builder()
				.name("Abimael")
				.email("teste@gmail.com")
				.password("senha")
				.build();
		
		Assertions.assertThat(userRepository.save(user).getId()).isNotNull();
		
	}
	
	@Test
	public void findUserByEmail() {
		
		UserEntity userEnt = createUser();
		
		userRepository.save(userEnt);
		
		Optional<UserEntity> entResult = userRepository.findByEmail(userEnt.getEmail());
		
		Assertions.assertThat(entResult).isPresent();
	}
	
	@Test
	public void returnEmptFetchUserByEmail() {
		
		Optional<UserEntity> entResult = userRepository.findByEmail("teste@gmail.com");
		
		Assertions.assertThat(!entResult.isPresent()).isTrue();
	}
	
	public static UserEntity createUser() {
		return UserEntity
				.builder()
				.name("Abimael")
				.email("teste@gmail.com")
				.password("senha")
				.build();
	}
}
