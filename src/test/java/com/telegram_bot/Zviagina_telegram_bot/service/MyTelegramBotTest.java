package com.telegram_bot.Zviagina_telegram_bot.service;

import com.telegram_bot.Zviagina_telegram_bot.config.BotConfig;
import com.telegram_bot.Zviagina_telegram_bot.handler.StartCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MyTelegramBotTest {

    @Mock
    private BotConfig botConfig;

    @Mock
    private StartCommandHandler startCommandHandler;

    @InjectMocks
    private MyTelegramBot myTelegramBot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(botConfig.getName()).thenReturn("TestBotName");
        when(botConfig.getToken()).thenReturn("TestBotToken");

        // Инициализация commandHandlers вручную с моком
        myTelegramBot = new MyTelegramBot(botConfig, Collections.singletonList(startCommandHandler));
    }

    @Test
    public void testOnUpdateReceived_withMessage() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn("Ping");
        when(message.getChatId()).thenReturn(12345L);

        myTelegramBot.onUpdateReceived(update);

        assertNotNull(myTelegramBot);
        verify(update, times(2)).getMessage();
    }

    @Test
    public void testOnUpdateReceived_withMessage2() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn("/start");
        when(message.getChatId()).thenReturn(12345L);

        myTelegramBot.onUpdateReceived(update);

        assertNotNull(myTelegramBot);
        verify(update, times(2)).getMessage();
    }

    @Test
    public void testOnUpdateReceived_messageIsNull() {
        Update update = mock(Update.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(null);

        myTelegramBot.onUpdateReceived(update);

        assertNotNull(myTelegramBot);
        verify(update, times(1)).getMessage();
    }

    @Test
    public void testOnUpdateReceived_noMessage() {
        Update update = mock(Update.class);

        when(update.hasMessage()).thenReturn(false);

        myTelegramBot.onUpdateReceived(update);

        assertNotNull(myTelegramBot);
        verify(update, times(1)).getMessage();
    }
}
