package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Lesson;
import ioMusicPublic.models.LessonRequest;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	Lesson findByRequest(LessonRequest request);

}
