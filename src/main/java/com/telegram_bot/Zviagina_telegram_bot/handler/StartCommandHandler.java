package com.telegram_bot.Zviagina_telegram_bot.handler;

import org.springframework.stereotype.Component;

@Component
public class StartCommandHandler implements CommandHandler {

    @Override
    public boolean canHandle(String command) {
        return "/start".equalsIgnoreCase(command);
    }

    @Override
    public String handle(long chatId, String command) {
        return "Добро пожаловать в телеграм-бот мини-банка. Напишите мне \"Ping\" - и я отвечу \"Pong\"!";
    }
}