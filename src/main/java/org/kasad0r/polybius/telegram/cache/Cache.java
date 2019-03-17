package org.kasad0r.polybius.telegram.cache;

public interface Cache {
    TelegramUser add(final TelegramUser user);

    TelegramUser update(final TelegramUser user);

    TelegramUser find(final long chatId);

    boolean delete(final long chatId);

    TelegramUser register(final long chatId);
}
