package org.kasad0r.polybius;

import org.kasad0r.polybius.telegram.PolybiusSquareBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Application {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new PolybiusSquareBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
