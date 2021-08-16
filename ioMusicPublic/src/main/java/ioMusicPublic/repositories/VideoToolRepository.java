package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.VideoTool;

public interface VideoToolRepository extends JpaRepository<VideoTool, Short>{	
	VideoTool findByName(String name);
}
