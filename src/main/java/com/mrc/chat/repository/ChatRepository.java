package com.mrc.chat.repository;


import com.mrc.chat.model.Chat;
import com.mrc.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByReceiver(User receiver);
    Optional<Chat> findBySenderAndReceiver(User sender, User receiver);
    Optional<Chat> findByReceiverIdAndSenderId(Long receiverid, Long senderid);

    Optional<Chat> findBySenderIdAndReceiverId(Long receiverid, Long senderid);

    @Query("SELECT c FROM Chat c WHERE (c.sender.id = :user1 AND c.receiver.id = :user2) OR (c.sender.id = :user2 AND c.receiver.id = :user1)")
    List<Chat> fetchChats(@Param("user1") Long user1, @Param("user2") Long user2);

}
