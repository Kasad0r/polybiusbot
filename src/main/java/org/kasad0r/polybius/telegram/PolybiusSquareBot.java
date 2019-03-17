package org.kasad0r.polybius.telegram;

import org.kasad0r.polybius.algorithms.Cipherable;
import org.kasad0r.polybius.algorithms.PolybiusAlgorithm;
import org.kasad0r.polybius.telegram.cache.Cache;
import org.kasad0r.polybius.telegram.cache.TelegramUser;
import org.kasad0r.polybius.telegram.cache.UserCache;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class PolybiusSquareBot extends TelegramLongPollingBot {

    private static final String USERNAME = "KotlinTestTestBot";
    private static final String TOKEN = "583578387:AAEo_CmeLbSyLjcYWzPZFPaFES4g1cG70qA";
    private final transient Cipherable polybius = new PolybiusAlgorithm();
    private final transient Cache userCache = new UserCache();

    @Override

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            ;
            final Long chatId = update.getMessage().getChatId();
            final String text = update.getMessage().getText();
            if (update.getMessage().hasText()) {
                System.out.println(text);
                TelegramUser telegramUser = userCache.find(chatId);
                if (text.equals("/start")) {
                    executeMessage(getMenu(chatId));
                } else if (text.contains("Зашифрувати")) {
                    executeMessage(sendRequestEncodeMsg(chatId));
                } else if (text.contains("Розшифрувати")) {
                    executeMessage(sendRequestDecodeMsg(chatId));
                } else if (text.contains("Інфо")) {
                    executeMessage(getInfo(chatId));
                } else if (telegramUser.getPosition() == TelegramUser.Position.ADD_TEXT) {
                    executeMessage(new SendMessage(chatId, "<b>" + polybius.encode(text) + "</b>"));
                } else if (telegramUser.getPosition() == TelegramUser.Position.ADD_ENCRYPTION) {
                    executeMessage(new SendMessage(chatId, "<b>" + polybius.decode(text) + "</b>"));
                }
            }
        } else if (update.hasChosenInlineQuery()) {
            
        }
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message.enableHtml(true));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    private SendMessage getInfo(final long chatId) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText("<b>Розробник бота: Пронякін Дмитро\nВерсія програми: 0.7.8\nПосилання на репозиторій</b> <a href =\"\">Перейти</a>");
        return sm;
    }

    private SendMessage getMenu(final long chatId) {
        final SendMessage sm = new SendMessage();
        final ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        final KeyboardRow row = new KeyboardRow();
        final KeyboardRow row2 = new KeyboardRow();
        final KeyboardRow row3 = new KeyboardRow();
        sm.setText("Головне меню:");
        sm.setChatId(chatId);
        row.add("Зашифрувати");
        row2.add("Розшифрувати");
        row3.add("Інфо");
        markup.setResizeKeyboard(true);
        markup.setKeyboard(Arrays.asList(row, row2, row3));
        sm.setReplyMarkup(markup);
        return sm;
    }

    private SendMessage sendRequestEncodeMsg(final Long chatId) {
        TelegramUser user = userCache.find(chatId);
        user.setPosition(TelegramUser.Position.ADD_TEXT);
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText("<b>Введіть текст який бажаєте зашифрувати:</b>");
        return sm;
    }

    private SendMessage sendRequestDecodeMsg(final Long chatId) {
        TelegramUser user = userCache.find(chatId);
        user.setPosition(TelegramUser.Position.ADD_ENCRYPTION);
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText("<b>Введіть зашифроване повідомлення:</b>");
        return sm;
    }
}
