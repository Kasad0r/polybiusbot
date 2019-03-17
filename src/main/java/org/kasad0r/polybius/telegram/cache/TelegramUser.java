package org.kasad0r.polybius.telegram.cache;

import lombok.Data;


@Data
public class TelegramUser {
    private Long chatId;
    private Position position = Position.NONE;

    public enum Position {
        ADD_TEXT,
        ADD_ENCRYPTION,
        NONE
    }
}
