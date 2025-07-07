package ru.cft.test_task.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    PAYMENTS("Payments", "payment");

    private final String folderName;
    private final String typeValue;

    public static String fromFolder(String folderName) {
        for (FileType type : values()) {
            if (type.folderName.equalsIgnoreCase(folderName)) {
                return type.typeValue;
            }
        }
        return "unprocessed";
    }
}
