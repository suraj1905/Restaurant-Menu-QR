package com.menuqr.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "item", schema = "identity")
public class ItemModel {

	@Id
	@Column(name = "id", length = 50, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "itemname")
	private String itemName;
	
	@Column(name = "itemprice")
	private double itemPrice;
	
	@Column(name = "itemdescription")
	private String itemDescription;
	
	@Column(name = "isveg")
	private int isVeg;
	
	@Column(name = "isavailable")
	private int isAvailable;
	
	@Column(name = "itemcategoryid")
	private int itemCategoryId;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "imageurl")
	private String imageUrl;
	
	@Column(name = "created", updatable = false)
	@CreationTimestamp
	private LocalDateTime created;
	
	@Column(name = "modified")
	@UpdateTimestamp
	private LocalDateTime modified;
	
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}	
	
	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getIsVeg() {
		return isVeg;
	}

	public void setIsVeg(int isVeg) {
		this.isVeg = isVeg;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(int itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
