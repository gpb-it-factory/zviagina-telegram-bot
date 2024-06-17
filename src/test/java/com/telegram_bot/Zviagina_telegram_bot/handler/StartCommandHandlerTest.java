package com.telegram_bot.Zviagina_telegram_bot.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StartCommandHandlerTest {

    private StartCommandHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new StartCommandHandler();
    }

    @Test
    public void canHandle_startCommand_true() {
        assertTrue(handler.canHandle("/start"));
    }

    @Test
    public void handle_startCommand_returnsWelcomeMessage() {
        String response = handler.handle(12345L, "/start");
        assertEquals("Добро пожаловать в телеграм-бот мини-банка. Напишите мне \"Ping\" - и я отвечу \"Pong\"!", response);
    }

    @Test
    public void testPingCommandHandlerWithNonPingCommand() {
        StartCommandHandler pingHandler = new StartCommandHandler();
        String nonStartCommand = "Hello";

        boolean canHandle = pingHandler.canHandle(nonStartCommand);
        assertFalse(canHandle, "PingCommandHandler should not handle commands other than '/start'");
    }
}
