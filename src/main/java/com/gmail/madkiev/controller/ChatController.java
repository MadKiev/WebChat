package com.gmail.madkiev.controller;

import com.gmail.madkiev.bot.MyBot;
import com.gmail.madkiev.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ChatController {


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        //add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ArrayList<ChatMessage> botInput(@Payload ChatMessage userMessage) {
        MyBot bot = new MyBot();
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        ChatMessage botAnswer = new ChatMessage();
        botAnswer.setContent(bot.sayInReturn(userMessage.getContent()));
        botAnswer.setSender("J.A.R.V.I.S");
        botAnswer.setType(ChatMessage.MessageType.CHAT);
        chatMessages.add(userMessage);
        chatMessages.add(botAnswer);
        return chatMessages;
    }
}
