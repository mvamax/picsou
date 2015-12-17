package io.picsou.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name="T_CATALOGUEPRODUIT")
public class CatalogueProduit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private CatalogueType catalogueType;
	
	@Column
	@NotNull
	@Size(min=3, max=100)
	private String intitule;
	
	@Column
	@Size(max=400)
	private String description;
	
	@Column
	@Min(value=0)
	private Float prixReference;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CatalogueType getCatalogueType() {
		return catalogueType;
	}

	public void setCatalogueType(CatalogueType catalogueType) {
		this.catalogueType = catalogueType;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrixReference() {
		return prixReference;
	}

	public void setPrixReference(Float prixReference) {
		this.prixReference = prixReference;
	}
	
	
}
