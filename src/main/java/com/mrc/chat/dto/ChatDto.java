package com.mrc.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private String from;
    private String photo;
    private String subject;
    private List<ChatMessageDto> chat;
}
