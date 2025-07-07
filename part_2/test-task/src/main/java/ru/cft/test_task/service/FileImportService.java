package ru.cft.test_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.test_task.dto.FileImportDTO;
import ru.cft.test_task.entity.FileImport;
import ru.cft.test_task.entity.FileSubtype;
import ru.cft.test_task.entity.FileType;
import ru.cft.test_task.repository.FileImportRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileImportService {

    @Value("${import.directory}")
    private String importDir;

    private final FileImportRepository repository;

    @Transactional
    public void processFiles() throws IOException {

        if (importDir == null) {
            throw new IllegalStateException("Import directory not set");
        }

        var resource = new ClassPathResource(importDir);
        var root = resource.getFile();
        try {
            Files.walk(root.toPath())
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        var folder = file.getParent().getFileName().toString();
                        var type = FileType.fromFolder(folder);

                        var fileParts = file.toString().split("\\.");
                        var extension = fileParts[fileParts.length - 1];
                        var subtype = FileSubtype.fromExtension(extension);

                        var fileImport = FileImport.builder()
                                .fileName(file.getFileName().toString())
                                .fileType(type)
                                .fileSubtype(subtype)
                                .fileSize(file.toFile().length())
                                .dp(LocalDateTime.now())
                                .status(type == null ? "error" : "ok")
                                .build();

                        repository.save(fileImport);
                    });
        } catch (IOException e) {
            throw new RuntimeException("Scan failed", e);
        }
    }

    public List<FileImportDTO> getUnprocessed() {
        return repository.findByStatus("unprocessed")
                .stream()
                .map(FileImportDTO::from)
                .toList();
    }

    public List<FileImportDTO> getProcessed(LocalDateTime from, LocalDateTime to) {
        return repository.findByDpBetween(from, to)
                .stream()
                .map(FileImportDTO::from)
                .toList();
    }

    public List<FileImportDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(FileImportDTO::from)
                .toList();
    }
}
