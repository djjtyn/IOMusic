package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import compositePrimaryKeys.InstructorVideoToolKey;
import ioMusicPublic.models.instructor.InstructorVideoTools;

public interface InstructorVideoToolsRepository extends JpaRepository<InstructorVideoTools, InstructorVideoToolKey>{

}
