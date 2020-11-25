package com.ffb.tradevalidator;

import com.ffb.tradevalidator.api.configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppConfiguration.class)
@SpringBootApplication
public class TradeApp implements CommandLineRunner
{
    private static final Logger log = LoggerFactory.getLogger(TradeApp.class);

    public static void main(String[] args) {
        SpringApplication.run(TradeApp.class, args);
    }

    @Override
    public void run(String... strings) {
        log.info("Starting Rental Store App");
    }
}
