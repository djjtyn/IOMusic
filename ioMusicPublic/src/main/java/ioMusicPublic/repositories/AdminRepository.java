package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ioMusicPublic.models.Admin;


public interface AdminRepository extends JpaRepository<Admin, Short>{
	
	//Method to count amount of admins and return a Byte data type
	@Query("SELECT count(*) FROM Admin")
	Byte countRecords();
	
	//No 2 admins will have the same email address so this method just returns a single Admin instance
	Admin findByEmail(String emailAddress);

}
