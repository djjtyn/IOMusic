package ioMusicPublic.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ioMusicPublic.models.instructor.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{
	
	//No 2 instructors will have the same email address so this method just returns a single InstructorCandidate instance
	Instructor findByEmail(String emailAddress);
	
	//The method below will allow an instructor to be searched for using the search bar
	@Query("SELECT i from Instructor i WHERE (i.firstName LIKE %:query%) OR (i.lastName LIKE %:query%)")
	List<Instructor> searchByQuery(String query);
	
	//The method below will count how many instructors there are
	@Query(value = "SELECT Count(*) FROM Instructor", nativeQuery = true)
	Long countInstructors();

	Page<Instructor> findAll(Pageable pageable);
}
