package io.picsou.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "T_PRODUITCONTRAT", indexes = { 
		@Index(name = "IDX_T_PRODUITCONTRAT_PK", columnList = "id")
		})
public class ProduitContrat {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String typeProduit;
	
	@NotNull
	@Size(min=3,max=250)
	private String intitule;
	
	@NotNull
	private Float prix;
	
	private int ordre;
	
	private boolean estDeductible;

	@ManyToOne
	private Contrat contrat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public Float getPrix() {
		return prix;
	}

	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
	
	
	
	public boolean isEstDeductible() {
		return estDeductible;
	}

	public void setEstDeductible(boolean estDeductible) {
		this.estDeductible = estDeductible;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "ProduitContrat [id=" + id + ", typeProduit=" + typeProduit
				+ ", intitule=" + intitule + ", prix=" + prix + ", ordre="
				+ ordre +"]";
	}


		

	
	
}
