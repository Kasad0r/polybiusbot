package org.kasad0r.polybius.telegram.cache;

import java.util.ArrayList;
import java.util.List;

public class UserCache implements Cache {
    private static List<TelegramUser> users = new ArrayList<>();

    @Override
    public TelegramUser add(TelegramUser user) {
        users.add(user);
        return user;
    }

    @Override
    public TelegramUser update(final TelegramUser user) {
        users.removeIf(u -> u.getChatId().equals(user.getChatId()));
        return add(user);
    }

    @Override
    public TelegramUser find(long chatId) {
        return users
                .stream()
                .filter(user -> user.getChatId() == chatId)
                .findFirst()
                .orElseGet(() -> add(register(chatId)));
    }

    @Override
    public boolean delete(long chatId) {
        return users.removeIf(user -> user.getChatId() == chatId);
    }

    @Override
    public TelegramUser register(long chatId) {
        TelegramUser user = new TelegramUser();
        user.setChatId(chatId);
        return user;
    }
}
