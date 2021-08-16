package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Genre;


public interface GenreRepository extends JpaRepository<Genre, Short>{
	Genre findByGenreName(String genreName);
}
