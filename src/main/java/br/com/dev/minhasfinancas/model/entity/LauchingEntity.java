 package br.com.dev.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.dev.minhasfinancas.model.enums.StatusLauching;
import br.com.dev.minhasfinancas.model.enums.TypeLauching;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(schema="financas", name="lancamento")
@Data
@Builder
public class LauchingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="descricao")
	private String description;
	
	@Column(name="mes")
	private Integer mounth;
		 	
	@Column(name="ano")
	private Integer year;
	
	@Column(name="valor")
	private BigDecimal value;
	 
	@Column(name="tipo")
	@Enumerated(EnumType.STRING)
	private TypeLauching type;
	
	@Enumerated(EnumType.STRING)
	private StatusLauching status;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private UserEntity user;
	
	@Column(name="data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate registrationDate;
	
}
