package com.telegram_bot.Zviagina_telegram_bot.service;

import com.telegram_bot.Zviagina_telegram_bot.config.BotConfig;
import com.telegram_bot.Zviagina_telegram_bot.handler.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.NonNull;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class MyTelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final List<CommandHandler> commandHandlers;

    @Autowired
    public MyTelegramBot(BotConfig config, List<CommandHandler> commandHandlers) {
        this.config = config;
        this.commandHandlers = commandHandlers;
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            log.info("Bot registered successfully: {}", getBotUsername());
        } catch (TelegramApiException e) {
            log.error("Failed to initialize bot", e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
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
        for (CommandHandler handler : commandHandlers) {
            if (handler.canHandle(messageText)) {
                String response = handler.handle(chatId, messageText);
                sendTextMessage(chatId, response);
                return;
            }
        }
        sendTextMessage(chatId, "Я пока могу обрабатывать только две команды: \"/start\" и \"Ping\"");
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
}

