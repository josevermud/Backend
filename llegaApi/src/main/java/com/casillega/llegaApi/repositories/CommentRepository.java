package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "CALL LookCommentsByEventId(:eventId)", nativeQuery = true)
    List<Object[]> getCommentsByEventId(@Param("eventId") int eventId);

    @Transactional
    @Procedure(procedureName = "AddCommentToEvent")
    void addCommentToEvent(
            @Param("user_id") long userId,
            @Param("event_id") long eventId,
            @Param("comment_text") String commentText,
            @Param("created_date") String createdDate
    );
}
