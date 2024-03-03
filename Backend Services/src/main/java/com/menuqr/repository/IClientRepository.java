package com.menuqr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.menuqr.models.ClientModel;



public interface IClientRepository extends JpaRepository<ClientModel,Integer>{
	
	@Query(value = "SELECT * FROM client WHERE email = ?1", nativeQuery = true)
	ClientModel getUser(String email);
	
	@Query(value = "SELECT * FROM client WHERE id = ?1", nativeQuery = true)
	ClientModel getById(int id);
	
	@Query(value = "SELECT * FROM client order by created desc", nativeQuery = true)
	ClientModel getAll();

}
