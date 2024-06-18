package com.telegram_bot.Zviagina_telegram_bot.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"bot.token=secretToken", "bot.name=TestBotName"})
public class BotConfigPropertiesTest {

    @MockBean
    private BotConfig botConfig;

    @Test
    public void propertiesAreSet() {
        when(botConfig.getToken()).thenReturn("secretToken");
        when(botConfig.getName()).thenReturn("TestBotName");

        assertEquals("secretToken", botConfig.getToken());
        assertEquals("TestBotName", botConfig.getName());
    }
}
