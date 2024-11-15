package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("FROM Message WHERE account_id = :idVar")
    Message findMessageByID(@Param ("idVar") int account_id);
}
