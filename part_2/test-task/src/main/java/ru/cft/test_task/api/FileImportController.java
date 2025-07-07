package ru.cft.test_task.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.test_task.dto.FileImportDTO;
import ru.cft.test_task.service.FileImportService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(Path.FILES)
public class FileImportController {

    private final FileImportService fileImportService;

    public FileImportController(FileImportService fileImportService) {
        this.fileImportService = fileImportService;
    }

    @PostMapping("/process")
    public ResponseEntity<Void> processFiles() throws IOException {
        fileImportService.processFiles();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unprocessed")
    public List<FileImportDTO> getUnprocessed() {
        return fileImportService.getUnprocessed();
    }

    @GetMapping("/processed")
    public List<FileImportDTO> getProcessed(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return fileImportService.getProcessed(from, to);
    }

    @GetMapping
    public List<FileImportDTO> getAll() {
        return fileImportService.getAll();
    }
}

