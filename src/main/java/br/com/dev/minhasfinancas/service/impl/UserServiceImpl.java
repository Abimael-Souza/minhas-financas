package br.com.dev.minhasfinancas.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dev.minhasfinancas.config.MsgConfigration;
import br.com.dev.minhasfinancas.exception.AuthenticationError;
import br.com.dev.minhasfinancas.exception.BusinessRuleException;
import br.com.dev.minhasfinancas.model.entity.UserEntity;
import br.com.dev.minhasfinancas.model.repository.UserRepository;
import br.com.dev.minhasfinancas.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MsgConfigration messages;
	
	@Override
	public UserEntity authenticate(String email, String password) {

		Optional<UserEntity> user = userRepository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new AuthenticationError(messages.get("user.service.user-not-found"));
		}
		
		if (!user.get().getPassword().equals(password)) {
			throw new AuthenticationError(messages.get("user.service.password-invalid"));
		}
		
		return user.get();
	}

	@Override
	@Transactional
	public UserEntity saveUser(UserEntity user) {
		emailValidate(user.getEmail());
		return userRepository.save(user);
	}

	@Override
	public void emailValidate(String email) {
		
		if(userRepository.existsByEmail(email)) {
			throw new BusinessRuleException(messages.get("user.service.user-existing"));
		}
	}

}
