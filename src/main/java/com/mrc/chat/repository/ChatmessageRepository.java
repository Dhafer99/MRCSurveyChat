package com.mrc.chat.repository;


import com.mrc.chat.model.Chatmessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatmessageRepository extends JpaRepository<Chatmessage, Long> {
}
