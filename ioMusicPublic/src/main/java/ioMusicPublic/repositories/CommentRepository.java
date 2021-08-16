package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Short>{

}
