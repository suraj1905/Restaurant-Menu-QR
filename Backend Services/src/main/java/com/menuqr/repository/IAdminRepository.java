package com.menuqr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.menuqr.models.AdminModel;

public interface IAdminRepository extends JpaRepository<AdminModel,Integer>{
	
	@Query(value = "SELECT * FROM admin WHERE username = ?1", nativeQuery = true)
    AdminModel getUsername(String username);
	
	@Query(value = "SELECT * FROM admin WHERE id = ?1", nativeQuery = true)
    AdminModel getById(int id);

}
