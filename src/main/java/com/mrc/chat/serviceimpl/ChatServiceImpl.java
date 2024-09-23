package com.mrc.chat.serviceimpl;

import org.springframework.messaging.core.MessageSendingOperations;
import com.mrc.chat.dto.ChatDto;
import com.mrc.chat.dto.ChatMessageDto;
import com.mrc.chat.exception.UserException;
import com.mrc.chat.exception.UserNotFoundException;
import com.mrc.chat.model.Chat;
import com.mrc.chat.model.Chatmessage;
import com.mrc.chat.model.User;
import com.mrc.chat.repository.ChatRepository;
import com.mrc.chat.repository.UserRepository;
import com.mrc.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageSendingOperations<String> messagingTemplate;

    @Override
    public List<Chat> saveChat(String content, Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        Chatmessage message = Chatmessage.builder()
                .msg(content)
                .type(sender.getId().equals(senderId) ? "even" : "odd")
                .date(LocalDate.now())
                .sender(receiver.getUsername())
                .build();

        log.info(" SENDDDDDDDDDDDDDDDDDDER ID {}",senderId);
        log.info(" RECEIVERRRRRRRRRRRRRRR ID {}",receiverId);

        List<Chat> chats = chatRepository.fetchChats(senderId,receiverId).stream().toList();

        //log.info("Found chat sender{} receiver{}",chat.getSender().getUsername(),chat.getReceiver().getUsername());
                /*.orElseGet(() -> {
                    Chat newChat = new Chat();
                    newChat.setSender(sender);
                    newChat.setReceiver(receiver);
                    newChat.setMessages(new ArrayList<>());
                    return chatRepository.save(newChat);
                });*/
        for (Chat chat:chats
             ) {
            chat.getMessages().add(message);
        }

        return chatRepository.saveAll(chats);
    }


    @Override
    public List<ChatDto> loadChatList(Long userId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found in chat"));


        List<Chat> chats = chatRepository.findByReceiver(receiver);

        // Setting the message type based on the current user
        chats.forEach(chat ->

                chat.getMessages().forEach(message -> {
                    if (chat.getReceiver().getUsername().equals(message.getSender())) {
                        message.setType("even");
                    } else {
                        message.setType("odd");
                    }
                })

        );
        chats.forEach(chat ->

                chat.setPhoto("assets/images/profile/user-1.jpg")

        );

        return chats.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void sendMessage(String chatMessage,String Mapping) {

        messagingTemplate.convertAndSend(Mapping,chatMessage);
    }

    @Override
    public Chat getChatBySenderAndReceiver(User sender , User receiver) {

        if(chatRepository.findBySenderAndReceiver(sender,receiver).isPresent()){
            return chatRepository.findBySenderAndReceiver(sender,receiver).get();
        }
      return null ;

    }

    //this will be used each time a user creates an account to initialize a chat
    @Override
    public List<Chat> initializeChats(Long receiverid) {

        log.info("STARTED SERVICE");
        List<Chat> chats = new ArrayList<>();
        List<User> users = userRepository.findAll();
        log.info("users {}",users);
        int i = 0 ;
        for( i=0;i<users.size()-1;i++){
            chats.add( new Chat());
        }
        User receiver = userRepository.findUserById(receiverid);
        for (Chat chat: chats
             ) {
            log.info("Chats {}",chat);
            chat.setReceiver(receiver);
            userRepository.save(receiver);
            for (User user:users
                 ) {
                log.info("users list :{}", user.getEmail());
                if(!Objects.equals(user.getId(), receiver.getId())) {
                    chat.setSender(user);
                    userRepository.save(user);
                }
            }
        }
        return chatRepository.saveAll(chats) ;

    }

    @Override
    public String updateSubject(String content) {
        return null;
    }

    //todo
    /*@Override
    public String updateSubject(String content) {

    }*/


    private ChatDto convertToDto(Chat chat) {
        List<ChatMessageDto> chatMessages = chat.getMessages().stream()
                .map(this::convertMessageToDto)
                .collect(Collectors.toList());

        return ChatDto.builder()
                .from(chat.getSender().getEmail()) // Assuming the sender's name is what you want to display in the 'from' field
                .photo(chat.getPhoto())
                .subject(chat.getSubject())
                .chat(chatMessages)
                .build();
    }

    private ChatMessageDto convertMessageToDto(Chatmessage message) {
        return ChatMessageDto.builder()
                .type(message.getType())
                .msg(message.getMsg())
                .date(message.getDate())
                .build();
    }
}
