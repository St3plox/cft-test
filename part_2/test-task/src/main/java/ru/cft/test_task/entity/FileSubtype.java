package ru.cft.test_task.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileSubtype {
    BANK1("bank1", "Bank_name");

    private final String extension;
    private final String subtypeValue;

    public static String fromExtension(String extension) {
        for (FileSubtype subtype : values()) {
            if (subtype.extension.equalsIgnoreCase(extension)) {
                return subtype.subtypeValue;
            }
        }
        return "unprocessed";
    }
}
