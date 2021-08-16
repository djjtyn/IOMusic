package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Short>{
	
	Instrument findByName(String name);
}
