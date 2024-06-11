package com.telegram_bot.Zviagina_telegram_bot.handler;

import org.springframework.stereotype.Component;

@Component
public class PingCommandHandler implements CommandHandler {

    @Override
    public boolean canHandle(String command) {
        return "Ping".equalsIgnoreCase(command);
    }

    @Override
    public String handle(long chatId, String command) {
        return "Pong";
    }
}
