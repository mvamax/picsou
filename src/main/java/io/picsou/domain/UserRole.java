package io.picsou.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.boot.autoconfigure.web.ResourceProperties.Strategy;

@Entity
@Table(name = "T_USER_ROLE", 
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "username" }))
public class UserRole{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_role_id", 
		unique = true, nullable = false)
	private Integer userRoleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	@Column(name = "role", nullable = false, length = 45)
	private String role;

	public UserRole() {
	}

	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}

	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}