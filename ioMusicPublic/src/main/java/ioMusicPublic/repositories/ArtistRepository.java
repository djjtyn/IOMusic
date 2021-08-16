package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
	
	Artist findByArtistName(String artistName);

}
