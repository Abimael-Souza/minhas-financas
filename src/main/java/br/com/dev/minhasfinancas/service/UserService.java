package br.com.dev.minhasfinancas.service;

import br.com.dev.minhasfinancas.model.entity.UserEntity;

public interface UserService {

	UserEntity authenticate(String email, String password);
	
	UserEntity saveUser(UserEntity usuario);
	
	void emailValidate(String email);
	
}
