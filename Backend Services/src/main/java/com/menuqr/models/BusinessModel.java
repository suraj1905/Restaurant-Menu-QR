package com.menuqr.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "business", schema = "identity")
public class BusinessModel {

	@Id
	@Column(name = "id", length = 36, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "addressline")
	private String addressLine;
	
	@Column(name = "pincode")
	private String pinCode;
	
	@Column(name = "clientid")
	private int clientId;

	@Column(name = "isopen")
	private int isOpen;
	
	@Column(name = "imageurl")
	private String imageUrl;
	
	@Column(name = "created", updatable = false)
	@CreationTimestamp
	private LocalDateTime created;
	
	@Column(name = "modified")
	@UpdateTimestamp
	private LocalDateTime modified;
	
	@Transient
	private List<ItemCategoryModel> itemCategories;

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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public List<ItemCategoryModel> getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(List<ItemCategoryModel> itemCategories) {
		this.itemCategories = itemCategories;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
		
	
}
