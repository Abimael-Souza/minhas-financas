package br.com.dev.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dev.minhasfinancas.model.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);
	
	Optional<UserEntity> findByEmail(String email);
}
