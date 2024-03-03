package com.menuqr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.menuqr.models.ItemModel;

public interface IItemRepository extends JpaRepository<ItemModel,Integer> {
	
	@Query(value = "SELECT * FROM item WHERE itemcategoryid = ?1 order by created desc", nativeQuery = true)
	List<ItemModel> getByItemCategoryId(int id);
	
	@Query(value = "SELECT item.id, item.created, item.imageurl, item.isavailable, item.isveg, \r\n"
			+ "item.itemcategoryid, item.itemname, item.itemprice, item.modified, \r\n"
			+ "item.quantity, item.itemdescription FROM item \r\n"
			+ "JOIN itemcategory on item.itemcategoryid = itemcategory.id \r\n"
			+ "JOIN business on itemcategory.businessid = business.id \r\n"
			+ "JOIN client on client.id = business.clientid WHERE item.id = ?1 and client.id = ?2", nativeQuery = true)
	ItemModel getByItemId(int id,int clientId);
	
	@Query(value = "select count(*) from item where id in :ids", nativeQuery = true)
    int itemsCount(List<Integer> ids);

}
