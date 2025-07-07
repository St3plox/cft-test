package ru.cft.test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.test_task.entity.FileImport;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileImportRepository extends JpaRepository<FileImport, Long> {
    List<FileImport> findByStatus(String status);
    List<FileImport> findByDpBetween(LocalDateTime from, LocalDateTime to);
}