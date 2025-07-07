package ru.cft.test_task.dto;

import lombok.Builder;
import lombok.Data;
import ru.cft.test_task.entity.FileImport;

import java.time.LocalDateTime;

@Data
@Builder
public class FileImportDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileSubtype;
    private Long fileSize;
    private String status;
    private LocalDateTime dp;
    private LocalDateTime dw;

    public static FileImportDTO from(FileImport entity) {
        return FileImportDTO.builder()
                .id(entity.getId())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .fileSubtype(entity.getFileSubtype())
                .fileSize(entity.getFileSize())
                .status(entity.getStatus())
                .dp(entity.getDp())
                .dw(entity.getDw())
                .build();
    }
}
