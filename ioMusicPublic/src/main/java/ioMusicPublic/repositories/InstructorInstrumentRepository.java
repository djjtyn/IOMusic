package ioMusicPublic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import compositePrimaryKeys.InstructorInstrumentKey;
import ioMusicPublic.models.Instrument;
import ioMusicPublic.models.instructor.InstructorInstrument;

public interface InstructorInstrumentRepository extends JpaRepository<InstructorInstrument, InstructorInstrumentKey>{
		
	//This method will allow 5 random instruments listed for instructors to be retrieved
	@Query(value = "SELECT DISTINCT instrumentId FROM InstructorInstrument ORDER BY RAND() LIMIT 5", nativeQuery = true)
	List<Short> getRandomInstruments();
	
	//This method will get all the instruments listed for instructors
	@Query(value = "SELECT DISTINCT instrumentId FROM InstructorInstrument", nativeQuery = true)
	List<Short> getAllInstruments();

}
