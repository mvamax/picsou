package io.picsou.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Table(name="T_ADRESSE",indexes={
		@Index(name = "IDX_T_ADRESSE_PK", columnList = "id")
})
public class Adresse {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	public String champ1;
	
	public String champ2;
	public String champ3;
	public String champ4;
	public String champ5;
	public String champ6;
	public String champ7;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChamp1() {
		return champ1;
	}
	public void setChamp1(String champ1) {
		this.champ1 = champ1;
	}
	public String getChamp2() {
		return champ2;
	}
	public void setChamp2(String champ2) {
		this.champ2 = champ2;
	}
	public String getChamp3() {
		return champ3;
	}
	public void setChamp3(String champ3) {
		this.champ3 = champ3;
	}
	public String getChamp4() {
		return champ4;
	}
	public void setChamp4(String champ4) {
		this.champ4 = champ4;
	}
	public String getChamp5() {
		return champ5;
	}
	public void setChamp5(String champ5) {
		this.champ5 = champ5;
	}
	public String getChamp6() {
		return champ6;
	}
	public void setChamp6(String champ6) {
		this.champ6 = champ6;
	}
	public String getChamp7() {
		return champ7;
	}
	public void setChamp7(String champ7) {
		this.champ7 = champ7;
	}
	
	
}
