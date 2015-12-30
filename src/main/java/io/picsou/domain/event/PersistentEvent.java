package io.picsou.domain.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "T_PERSISTENTEVENT", indexes = { 
		@Index(name = "IDX_T_PERSISTENTEVENT_PK", columnList = "id"),
		@Index(name = "IDX_PERSISTENTEVENT_TYPE", columnList = "type")
		})
public class PersistentEvent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String type;
	
	private String message;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date date;
	
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PersistentEvent() {
		super();
	}
	
	
	
}
