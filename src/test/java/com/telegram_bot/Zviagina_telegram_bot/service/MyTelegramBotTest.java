package com.telegram_bot.Zviagina_telegram_bot.service;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import com.telegram_bot.Zviagina_telegram_bot.config.BotConfig;
import com.telegram_bot.Zviagina_telegram_bot.handler.CommandHandler;
import com.telegram_bot.Zviagina_telegram_bot.handler.PingCommandHandler;
import com.telegram_bot.Zviagina_telegram_bot.handler.StartCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Level;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

@TestPropertySource(properties = {"logging.config=classpath:logback-test.xml"})
public class MyTelegramBotTest {
    private Path logPath;
    private MyTelegramBot myTelegramBot;
    private BotConfig botConfig;
    private List<CommandHandler> commandHandlers;

    @BeforeEach
    public void setUp() throws IOException {
        logPath = Files.createTempFile("test-log", ".log");
        reloadLogbackConfiguration(logPath.toString());
        botConfig = new BotConfig();
        commandHandlers = new ArrayList<>();
        commandHandlers.add(new StartCommandHandler());
        commandHandlers.add(new PingCommandHandler());

        myTelegramBot = new MyTelegramBot(botConfig, commandHandlers);
    }

    private void reloadLogbackConfiguration(String logFilePath) {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            context.reset();

            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
            encoder.setContext(context);
            encoder.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
            encoder.start();

            FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
            fileAppender.setContext(context);
            fileAppender.setFile(logFilePath);
            fileAppender.setEncoder(encoder);
            fileAppender.start();

            ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT");
            rootLogger.addAppender(fileAppender);
            rootLogger.setLevel(Level.DEBUG);

        } catch (Exception e) {
            System.err.println("Error reloading logback configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testOnUpdateReceived_withMessagePing() {
        Update update = createUpdate("Ping", 12345L);
        myTelegramBot.onUpdateReceived(update);
        try {
            String logContent = Files.lines(Paths.get(logPath.toUri())).collect(Collectors.joining("\n"));
            assertTrue(logContent.contains("Received text message from chat ID 12345: Ping"));
            assertTrue(logContent.contains("Handled command Ping: Pong"), "Log should contain 'Handled command Ping: Pong'");
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void testOnUpdateReceived_withMessageStart() {
        Update update = createUpdate("/start", 12345L);
        myTelegramBot.onUpdateReceived(update);
        try {
            String logContent = Files.lines(Paths.get(logPath.toUri())).collect(Collectors.joining("\n"));
            assertTrue(logContent.contains("Received text message from chat ID 12345: /start"));
            assertTrue(logContent.contains("Handled command /start: Добро пожаловать в телеграм-бот мини-банка. Напишите мне \"Ping\" - и я отвечу \"Pong\"!"), "Log should contain 'Handled command Ping: Pong'");
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void testOnUpdateReceived_messageIsNull() {
        Update update = new Update();
        myTelegramBot.onUpdateReceived(update);
        try {
            String logContent = Files.lines(Paths.get(logPath.toUri())).collect(Collectors.joining("\n"));
            assertTrue(logContent.contains("Received update does not contain a message."));
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected error: " + e.getMessage());
        }
    }


    @Test
    public void testOnUpdateReceived_noMessage() {
        // Используем метод createUpdate для создания объекта Update с пустым текстом сообщения
        Update update = createUpdate(null, 12345L);

        myTelegramBot.onUpdateReceived(update);
        try {
            String logContent = Files.lines(Paths.get(logPath.toUri())).collect(Collectors.joining("\n"));
            assertTrue(logContent.contains("Received message does not contain text."));
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void testHandleNullMessage() {
        Update update = null;

        assertThrows(NullPointerException.class, () -> {
            myTelegramBot.onUpdateReceived(update);
        });
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

