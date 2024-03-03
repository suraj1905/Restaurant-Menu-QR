package com.menuqr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.menuqr.models.ItemCategoryModel;

public interface IItemCategoryRepository extends JpaRepository<ItemCategoryModel,Integer>{
	
	@Query(value = "SELECT * FROM itemcategory WHERE businessid = ?1", nativeQuery = true)
	List<ItemCategoryModel> getByBusinessId(int id);	
	
	
	@Query(value = "SELECT itemcategory.id,itemcategory.businessid,itemcategory.itemcategoryname,itemcategory.created,\r\n"
			+ "itemcategory.modified FROM itemcategory JOIN business on business.id = itemcategory.businessid JOIN client \r\n"
			+ "on client.id = business.clientid WHERE itemcategory.id = ?1 and client.id = ?2", nativeQuery = true)
	ItemCategoryModel getById(int id, int clientId);
}
