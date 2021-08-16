package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import compositePrimaryKeys.CandidateFavouriteArtistKey;
import ioMusicPublic.models.instructorCandidate.CandidateFavouriteArtist;

public interface CandidateFavouriteArtistRepository extends JpaRepository<CandidateFavouriteArtist, CandidateFavouriteArtistKey>{

}
