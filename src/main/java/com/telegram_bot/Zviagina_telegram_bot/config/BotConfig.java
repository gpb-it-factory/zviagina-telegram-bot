package com.telegram_bot.Zviagina_telegram_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Data;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

//    @Value("${telegram.bot.name}")
//    private String botName;
//
//    @Value("${telegram.bot.token}")
//    private String botToken;

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

}
