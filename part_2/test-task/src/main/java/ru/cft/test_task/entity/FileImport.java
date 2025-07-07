package ru.cft.test_task.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_import")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileImport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    @SequenceGenerator(name = "file_seq", sequenceName = "file_import_pk", allocationSize = 1)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_subtype", nullable = false)
    private String fileSubtype;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "dp", nullable = false)
    private LocalDateTime dp;

    @Column(name = "dw", insertable = false, updatable = false)
    private LocalDateTime dw;
}

