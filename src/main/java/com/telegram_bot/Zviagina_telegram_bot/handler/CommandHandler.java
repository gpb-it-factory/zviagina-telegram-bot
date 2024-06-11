package com.telegram_bot.Zviagina_telegram_bot.handler;

public interface CommandHandler {
    boolean canHandle(String command);
    String handle(long chatId, String command);
}
