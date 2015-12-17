package io.picsou.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.internal.NotNull;

import sun.misc.Timeable;

@Entity
@Table(name = "T_CLIENT", indexes = { 
		@Index(name = "IDX_T_CLIENT_PK", columnList = "id")
		})
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -14739415035711801L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min=3, max=300)
	private String nom;

	private String prenom;

	@Pattern(regexp = "(\\+|[0-9]|\\-|\\ ){0,20}",message="le numéro de téléphone est mal formé")
	private String telephone;

	@Email
	private String email;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateNaissance;

	@OneToOne(cascade=CascadeType.ALL)
	public Adresse adresse;

	@OneToMany(mappedBy = "client")
	private List<Contrat> contrats = new java.util.ArrayList<Contrat>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Contrat> getContrats() {
		return contrats;
	}

	public void setContrats(List<Contrat> contrats) {
		this.contrats = contrats;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", adresse=" + adresse + "]";
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

}
