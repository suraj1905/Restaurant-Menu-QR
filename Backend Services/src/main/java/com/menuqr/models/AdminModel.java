package com.menuqr.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin", schema = "identity")
public class AdminModel {
	
	@Id
	@Column(name = "id", length = 36, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "isMasterAdmin")
	private int isMasterAdmin;
	
	@Column(name = "created", updatable = false)
	@CreationTimestamp
	private LocalDateTime created;
	
	@Column(name = "modified")
	@UpdateTimestamp
	private LocalDateTime modified;
	
	public int getIsMasterAdmin() {
		return isMasterAdmin;
	}
	public void setIsMasterAdmin(int isMasterAdmin) {
		this.isMasterAdmin = isMasterAdmin;
	} 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getModified() {
		return modified;
	}
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}
}


