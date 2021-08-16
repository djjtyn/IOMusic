package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	 
	//No 2 students will have the same email address so this method just returns a single Student instance
	Student findByEmail(String emailAddress);
	

}
