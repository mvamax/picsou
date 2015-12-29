package io.picsou.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_PARAM_IMPOSITION", indexes = { 
		@Index(name = "IDX_T_PARAMIMPOSITION_PK", columnList = "id"),
		@Index(name = "IDX_T_PARAMIMPOSITION_YEAR", columnList = "year")
		})
public class ParametreImposition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String year;

	@NotNull
	private float taux;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public float getTaux() {
		return taux;
	}

	public void setTaux(float taux) {
		this.taux = taux;
	}
	
	
	
	
}
