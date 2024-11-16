package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("FROM Message WHERE postedBy = :postedVar")
    List<Message> findMessageByPostedBy(@Param("postedVar") long posted_by);

    @Query("FROM Message WHERE messageId = :idVar")
    Message findMessageByID(@Param("idVar") int message_id);
    
}
