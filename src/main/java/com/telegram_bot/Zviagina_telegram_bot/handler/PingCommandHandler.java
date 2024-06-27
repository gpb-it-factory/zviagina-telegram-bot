package com.telegram_bot.Zviagina_telegram_bot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PingCommandHandler implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(PingCommandHandler.class);
    @Override
    public boolean canHandle(String command) {
        return "Ping".equalsIgnoreCase(command);
    }

    @Override
    public String handle(long chatId, String command) {
        String response = "Pong";
        logger.info("Received 'Ping' command from chat ID: {}", chatId);
        logger.info("Handled command Ping: {}", response);
        return response;
    }
}
