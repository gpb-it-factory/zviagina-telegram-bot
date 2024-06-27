package com.telegram_bot.Zviagina_telegram_bot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StartCommandHandler implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartCommandHandler.class);

    @Override
    public boolean canHandle(String command) {
        return "/start".equalsIgnoreCase(command);
    }

    @Override
    public String handle(long chatId, String command) {
        String response = "Добро пожаловать в телеграм-бот мини-банка. Напишите мне \"Ping\" - и я отвечу \"Pong\"!";
        logger.info("Received 'Ping' command from chat ID: {}", chatId);
        logger.info("Handled command /start: {}", response);
        return response;
    }
}
