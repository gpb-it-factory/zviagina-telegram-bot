package com.telegram_bot.Zviagina_telegram_bot.service;

import com.telegram_bot.Zviagina_telegram_bot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.NonNull;

@Component
@Slf4j
public class MyTelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    public MyTelegramBot(BotConfig config) {
        this.config = config;
    }
    @Override
    public void onUpdateReceived(@NonNull Update update) {
        if (update.getMessage() == null) {
            return;
        }
        final Message message = update.getMessage();
        long chatId = message.getChatId();
        if (message.hasText()) {
            String messageText = message.getText();
            handleIncomingMessage(chatId, messageText);
        }
    }

    private void handleIncomingMessage(long chatId, String messageText) {
        if (messageText.equalsIgnoreCase("Ping")) {
            handlePingCommand(chatId, messageText);
        } else if (messageText.equalsIgnoreCase("/start")) {
            handleStartCommand(chatId, messageText);
        } else {
            handleUnknownCommand(chatId, messageText);
        }
    }

    private void handlePingCommand(long chatId, String messageText) {
        sendTextMessage(chatId, "Pong");
        log.info("Received message: {}", messageText);
    }

    private void handleStartCommand(long chatId, String messageText) {
        startCommandReceived(chatId);
        log.info("Received message: {}", messageText);
    }

    private void handleUnknownCommand(long chatId, String messageText) {
        sendTextMessage(chatId, "Я пока могу обрабатывать только две команды: \"/start\" и \"Ping\"");
        log.info("Received message: {}", messageText);
    }

    private void startCommandReceived(long chatId) {
        String answer = "Добро пожаловать в телеграм-бот мини-банка. Напишите мне \"Ping\" - и я отвечу \"Pong\"!";
        sendTextMessage(chatId, answer);
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
