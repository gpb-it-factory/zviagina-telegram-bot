package com.telegram_bot.Zviagina_telegram_bot.service;

import com.telegram_bot.Zviagina_telegram_bot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTelegramBot extends TelegramLongPollingBot {

    final private BotConfig config;


    public MyTelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equalsIgnoreCase("Ping")) {
                sendTextMessage(update.getMessage().getChatId(), "Pong");
                log.info("Received message: {}", messageText);
            } else if (messageText.equalsIgnoreCase("/start")) {
                startCommandReceived(chatId);
                log.info("Received message: {}", messageText);
            } else {
                sendTextMessage(update.getMessage().getChatId(), "Я пока могу обрабатывать только две команды: \"/start\" и \"Ping\"");
                log.info("Received message: {}", messageText);
            }
        }
    }

    private void startCommandReceived(long chatId) {
        String answer = "Добро пожаловать в телеграм-бот мини-банка. Напишите мне \"Ping\" - и я отвечу \"Pong\"! ";
        sendTextMessage(chatId, answer);
    }


    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }
}