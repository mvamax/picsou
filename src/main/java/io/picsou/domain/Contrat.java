package io.picsou.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.executable.ValidateOnExecution;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "T_CONTRAT", indexes = { 
		@Index(name = "IDX_T_CONTRAT_PK", columnList = "id"),
		@Index(name = "IDX_T_CONTRAT_CLIENT_FK", columnList = "client_id"),
		@Index(name = "IDX_T_CONTRAT_ETATCONTRAT_FK", columnList = "etatcontrat_id")
		})
public class Contrat {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	@Column
	public String intitule;
	
	@OneToMany(mappedBy="contrat",cascade=CascadeType.ALL)
	@NotEmpty(message="La liste des prestations ne peut pas être vide")
	public List<ProduitContrat> produitsContrat=new ArrayList<ProduitContrat>();

	@ManyToOne
	public Client client;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateExecutionContrat;
	
	@ManyToOne
	@JoinColumn(name="etatcontrat_id")
	private EtatContrat etatContrat;
	
	
	private Float prix;
	
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

	public List<ProduitContrat> getProduitsContrat() {
		return produitsContrat;
	}

	public void setProduitsContrat(List<ProduitContrat> produitsContrat) {
		this.produitsContrat = produitsContrat;
		
	}
	public void addProduitContrat(ProduitContrat produitContrat) {
		this.produitsContrat.add(produitContrat) ;
		
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public EtatContrat getEtatContrat() {
		return etatContrat;
	}

	public void setEtatContrat(EtatContrat etatContrat) {
		this.etatContrat = etatContrat;
	}
	
	public Date getDateExecutionContrat() {
		return dateExecutionContrat;
	}

	public void setDateExecutionContrat(Date dateExecutionContrat) {
		this.dateExecutionContrat = dateExecutionContrat;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "Contrat [id=" + id + ", intitule=" + intitule
				+ ", produitsContrat=" + produitsContrat + ", client=" + client
				+ ", dateExecutionContrat=" + dateExecutionContrat
				+ ", etatContrat=" + etatContrat + ", prix=" + prix + "]";
	}
	
	
}
