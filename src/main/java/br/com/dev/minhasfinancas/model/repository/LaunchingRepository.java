package br.com.dev.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dev.minhasfinancas.model.entity.LauchingEntity;

@Repository
public interface LaunchingRepository extends JpaRepository<LauchingEntity, Long>{

}
