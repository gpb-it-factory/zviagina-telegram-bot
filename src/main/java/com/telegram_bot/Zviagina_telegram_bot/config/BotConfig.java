package com.telegram_bot.Zviagina_telegram_bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
}
