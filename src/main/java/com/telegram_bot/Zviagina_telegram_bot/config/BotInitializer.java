package com.telegram_bot.Zviagina_telegram_bot.config;

import javax.annotation.PostConstruct;
import com.telegram_bot.Zviagina_telegram_bot.service.MyTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class BotInitializer {
    private final MyTelegramBot bot;

    @Autowired
    public BotInitializer(MyTelegramBot bot) {
        this.bot = bot;
    }

    @PostConstruct
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try {
            api.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error("Failed to initialize bot", e);
        }
    }
}
