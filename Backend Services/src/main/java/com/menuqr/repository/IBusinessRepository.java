package com.menuqr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.menuqr.models.BusinessModel;

public interface IBusinessRepository extends JpaRepository<BusinessModel,Integer>{
	
	@Query(value = "SELECT * FROM business WHERE clientid = ?1", nativeQuery = true)
    BusinessModel getBusinessByClientId(int clientId);
	
	@Query(value = "SELECT * FROM business WHERE id = ?1", nativeQuery = true)
    BusinessModel getBusinessById(int id);
	
	@Query(value = "SELECT categoryname from category WHERE categoryid = ?1", nativeQuery = true)
    String getCategoryName(int id);
	
	@Query(value = "SELECT * FROM business WHERE clientid = ?1", nativeQuery = true)
	List<BusinessModel> getBusinessesByClientId(int id);
	
//	@Query(value = "SELECT *\r\n"
//			+ "FROM business\r\n"
//			+ "WHERE ST_Distance_Sphere(\r\n"
//			+ "  point(longitude, latitude),\r\n"
//			+ "  point(?1, ?2)\r\n"
//			+ ") < 5000;", nativeQuery = true)
	@Query(value = "SELECT *\r\n"
			+ "FROM business\r\n", nativeQuery = true)
	List<BusinessModel> getBusinessByLocation(String logintude, String latitude);
	
	@Query(value = "SELECT business.name from business inner join itemcategory on "
			+ "business.id = itemcategory.businessid where itemcategory.id = ?1", nativeQuery = true)
	String getBusinessByCategoryId(int categoryId);
	
	@Query(value = "SELECT clientid from business where id = ?1", nativeQuery = true)
	int getClientIdByBusinessId(int businessId);
	
	@Modifying
	@Transactional
	@Query(value = "update business set  isopen = ?1 , isdelivering = ?2 where id = ?3", nativeQuery = true)
	void updateStoreStatus(int isOpen, int isDelivering, int id);
}
