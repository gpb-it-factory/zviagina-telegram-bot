package com.telegram_bot.Zviagina_telegram_bot.service;

import com.telegram_bot.Zviagina_telegram_bot.config.BotConfig;
import com.telegram_bot.Zviagina_telegram_bot.handler.CommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = {"logging.config=classpath:logback-test.xml"})
public class MyTelegramBotTest {
    private final String logPath = "build/logs/test-app.log";
    private MyTelegramBot myTelegramBot;
    private BotConfig botConfig;
    private List<CommandHandler> commandHandlers;

    @BeforeEach
    public void setUp() {
        new File(logPath).delete();
        botConfig = new BotConfig();
        botConfig.setName("TestBot");
        botConfig.setToken("TestToken");

        commandHandlers = new ArrayList<>();

        myTelegramBot = new MyTelegramBot(botConfig, commandHandlers);
    }

    @Test
    public void testOnUpdateReceived_withMessage2() {
        Update update = createUpdate("Ping", 12345L);

        myTelegramBot.onUpdateReceived(update);
        try {
            String logContent = Files.lines(Paths.get(logPath)).collect(Collectors.joining("\n"));
            assertTrue(logContent.contains("Received text message from chat ID 12345: Ping"));
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        }
    }

    private Update createUpdate(String text, long chatId) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(chatId);
        message.setChat(chat);
        message.setText(text);
        update.setMessage(message);
        return update;
    }
}
/*
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void testHandleNullMessage() {
        Update nullUpdate = null;

        assertThrows(NullPointerException.class, () -> {
            myTelegramBot.onUpdateReceived(nullUpdate);
        });
    }

}*/
