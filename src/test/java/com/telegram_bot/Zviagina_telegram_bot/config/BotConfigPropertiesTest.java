package com.telegram_bot.Zviagina_telegram_bot.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"bot.token=secretToken", "bot.name=TestBotName"})
public class BotConfigPropertiesTest {

    @Autowired
    private BotConfig botConfig;

    @Test
    public void propertiesAreSet() {
//        assertEquals("secretToken", botConfig.getToken());
        assertEquals("Zviagina_telegram_bot", botConfig.getName());
    }
}
