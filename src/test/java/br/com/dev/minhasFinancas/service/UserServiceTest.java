package br.com.dev.minhasFinancas.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dev.minhasfinancas.config.MsgConfiguration;
import br.com.dev.minhasfinancas.exception.AuthenticationError;
import br.com.dev.minhasfinancas.exception.BusinessRuleException;
import br.com.dev.minhasfinancas.model.entity.UserEntity;
import br.com.dev.minhasfinancas.model.repository.UserRepository;
import br.com.dev.minhasfinancas.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

	@MockBean
	MsgConfiguration messages;
	
	@MockBean
	UserRepository userRepository;
	
	@SpyBean
	UserServiceImpl userServiceImpl;
	
	@Test(expected = Test.None.class)
	public void authenticateUserSuccessfully() {
		
		//Cenário
		String email 	= "teste@gmail.com";
		String password = "senha";
		
		UserEntity user = UserEntity
				.builder()
				.email(email)
				.password(password)
				.id(1l)
				.build();

		//Ação
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		
		UserEntity result = userServiceImpl.authenticate(email, password);
		
		//Verifição
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void throwExceptionWhenThereIsNoRegisteredUserForTheInformedEmail() {
		
		//Ação
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		
		when(messages.getMessage("user.service.user-not-found")).thenReturn("Usuário não encontrado para o email informado.");
		
		Throwable exception = Assertions.catchThrowable( () -> userServiceImpl.authenticate("teste@gmail.com", "senha") );
		
		//Verifição
		Assertions.assertThat(exception).isInstanceOf(AuthenticationError.class).hasMessage("Usuário não encontrado para o email informado.");
	}
	
	@Test
	public void throwExceptionWhenPasswordIsIncorret() {
		
		//Cenário
		UserEntity user = UserEntity.builder().email("teste@gmail.com").password("senha").build();
		
		//Ação
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		
		when(messages.getMessage("user.service.password-invalid")).thenReturn("Senha inválida.");
		
		Throwable exception = Assertions.catchThrowable( () -> userServiceImpl.authenticate("teste@gmail.com", "senha123") );
		
		//Verifição
		Assertions.assertThat(exception).isInstanceOf(AuthenticationError.class).hasMessage("Senha inválida.");
	}
	
	@Test(expected = Test.None.class)
	public void saveUser() {

		//Cenário
		doNothing().when(userServiceImpl).emailValidate(anyString());
		
		UserEntity user = UserEntity
				.builder()
				.id(1l)
				.name("Abimael")
				.email("teste@gmail.com")
				.password("senha")
				.build();
		
		when(userRepository.save(any(UserEntity.class))).thenReturn(user);
		
		//Ação
		UserEntity userSave = userServiceImpl.saveUser(new UserEntity());
		
		//Verifição
		Assertions.assertThat(userSave).isNotNull();
		Assertions.assertThat(userSave.getId()).isEqualTo(1l);
		Assertions.assertThat(userSave.getName()).isEqualTo("Abimael");
		Assertions.assertThat(userSave.getEmail()).isEqualTo("teste@gmail.com");
		Assertions.assertThat(userSave.getPassword()).isEqualTo("senha");
	}
	
	@Test(expected = BusinessRuleException.class)
	public void shouldNotSaveUserIfEmailAlreadyRegistered() {
		
		//Cenário
		String email = "teste@gmail.com";
		
		UserEntity user = UserEntity
				.builder()
				.email(email)
				.build();
		
		//Ação
		doThrow(BusinessRuleException.class).when(userServiceImpl).emailValidate(email);
		
		userServiceImpl.saveUser(user); 
		
		//Verificação
		verify(userRepository, never()).save(user);
	}
	
	@Test(expected = Test.None.class)
	public void validEmail() {
		//Cenário
		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		
		//Ação
		userServiceImpl.emailValidate("teste@gmail.com");
	}
	
	@Test
	public void throwExceptionWhenEmailIsAlreadyRegistered() {
		
		//Cenário
		when(userRepository.existsByEmail(anyString())).thenReturn(true);
		
		when(messages.getMessage("user.service.user-existing")).thenReturn("Já existe um usuário cadastrado com este email.");
		
		//Ação
		Throwable exception = Assertions.catchThrowable( () -> userServiceImpl.emailValidate("teste@gmail.com") );
		
		//Verificação
		Assertions.assertThat(exception).isInstanceOf(BusinessRuleException.class).hasMessage("Já existe um usuário cadastrado com este email.");
	}
	
}
