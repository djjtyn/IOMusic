package ioMusicPublic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ioMusicPublic.models.LessonRequest;
import ioMusicPublic.models.Student;
import ioMusicPublic.models.instructor.Instructor;

public interface LessonRequestRepository extends JpaRepository<LessonRequest, Long>{
	
	//Method below finds a list of lesson requests using a student
	List<LessonRequest> findByStudent(Student student);
	
	//Method below finds a list of lesson requests using an instructor
	List<LessonRequest> findByInstructor(Instructor student);
	
	//Method below counts the amount of lesson requests requiring action by Instructors
	@Query(value = "SELECT Count(*) FROM LessonRequest WHERE (statusId = 1 OR statusId = 4) AND instructorId = ?1", nativeQuery = true)
	Long countRequestsRequiringInstructorAction(Long instructorId);
	
	//Method below counts the amount of lesson requests requiring action by Students
	@Query(value = "SELECT Count(*) FROM LessonRequest WHERE statusId = 2 AND studentId = ?1", nativeQuery = true)
	Long countRequestsRequiringStudentAction(Long studentId);

}
