package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import compositePrimaryKeys.InstructorFavouriteArtistKey;
import ioMusicPublic.models.instructor.InstructorFavouriteArtist;

public interface InstructorFavouriteArtistRepository extends JpaRepository<InstructorFavouriteArtist, InstructorFavouriteArtistKey>{

}
