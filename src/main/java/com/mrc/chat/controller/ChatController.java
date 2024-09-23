package com.mrc.chat.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mrc.chat.dto.ChatDto;
import com.mrc.chat.dto.ChatMessageDto;
import com.mrc.chat.model.Chat;
import com.mrc.chat.model.User;
import com.mrc.chat.repository.UserRepository;
import com.mrc.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final UserRepository userRepository ;
    private final ChatService chatService ;
    private final MessageSendingOperations<String> messagingTemplate;


    //sender is equivalent to from in angular side
    @PostMapping("/initializechat/{receiverid}")
    public List<Chat> InitializeChat(@PathVariable("receiverid") Long receiverid) {
        return chatService.initializeChats(receiverid);
    }



    @GetMapping("/chats/{userId}")
    public List<ChatDto> getChats(@PathVariable Long userId) {
        return chatService.loadChatList(userId);
    }
    @MessageMapping("/chat/sendMessage/{senderID}/{receiverID}")
    @SendTo("/topic/public")
    public void sendMessage(
            @Payload String chatMessage,
            @DestinationVariable Long senderID ,
            @DestinationVariable Long receiverID


    ) throws JsonProcessingException {

        log.info("payload : {}",chatMessage);
        User sender = userRepository.findUserById(senderID);
        User receiver = userRepository.findUserById(receiverID);

        chatService.saveChat(chatMessage,senderID,receiverID);

        Chat chat = chatService.getChatBySenderAndReceiver(sender,receiver);

        ChatMessageDto chatMessageDto = new ChatMessageDto();

        chatMessageDto.setFrom(sender.getUsername());
        chatMessageDto.setMsg(chatMessage);
        if(chat.getSender().getId().equals(senderID) ){
            chatMessageDto.setType("even");
        }else{
            chatMessageDto.setType("odd");
        }
      chatMessageDto.setDate(LocalDate.now());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String messageDTOJSON = mapper.writeValueAsString(chatMessageDto);

        chatService.sendMessage(messageDTOJSON,"/topic/public");



    }


}
