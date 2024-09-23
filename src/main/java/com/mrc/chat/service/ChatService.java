package com.mrc.chat.service;



import com.mrc.chat.dto.ChatDto;
import com.mrc.chat.model.Chat;
import com.mrc.chat.model.User;

import java.util.List;

public interface ChatService {



    List<Chat> saveChat(String content, Long senderId, Long receiverId );

    List<ChatDto> loadChatList(Long userId);


    public void sendMessage(String chatMessage,String Mapping);


    Chat getChatBySenderAndReceiver(User sender, User receiver);

    List<Chat> initializeChats(Long receiverid) ;

    String updateSubject(String content);



}
