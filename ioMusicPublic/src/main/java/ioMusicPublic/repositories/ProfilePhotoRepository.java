package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.instructor.Instructor;
import ioMusicPublic.models.instructor.ProfilePhoto;

public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long>{
	
	ProfilePhoto findByInstructor(Instructor instructor);
}
