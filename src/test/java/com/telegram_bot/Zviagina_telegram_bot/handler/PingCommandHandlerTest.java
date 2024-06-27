package com.telegram_bot.Zviagina_telegram_bot.handler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PingCommandHandlerTest {

    private PingCommandHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new PingCommandHandler();
    }

    @Test
    public void canHandle_pingCommand_true() {
        assertTrue(handler.canHandle("Ping"));
    }

    @Test
    public void handle_pingCommand_returnsPong() {
        String response = handler.handle(12345L, "Ping");
        assertEquals("Pong", response);
    }

    @Test
    public void testPingCommandHandlerWithNonPingCommand() {
        PingCommandHandler pingHandler = new PingCommandHandler();
        String nonPingCommand = "Hello";

        boolean canHandle = pingHandler.canHandle(nonPingCommand);
        assertFalse(canHandle, "PingCommandHandler should not handle commands other than 'Ping'");
    }

}
