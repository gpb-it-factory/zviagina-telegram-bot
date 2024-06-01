package com.telegram_bot.Zviagina_telegram_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Data;
import lombok.Getter;

@Configuration
@Data
@Getter
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;
}
