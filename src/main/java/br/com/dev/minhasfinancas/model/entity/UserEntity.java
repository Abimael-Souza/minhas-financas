package br.com.dev.minhasfinancas.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema="financas", name="usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="nome")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="senha")
	private String password;
	
	@Column(name="data_cadastro")
	private LocalDate registrationDate;
}
