--liquibase formatted sql

--changeset egor:1
CREATE TABLE FILE_IMPORT
(
    ID           NUMBER        NOT NULL,
    FILE_NAME    VARCHAR2(255) NOT NULL,
    FILE_TYPE    VARCHAR2(100) NOT NULL,
    FILE_SUBTYPE VARCHAR2(100) NOT NULL,
    FILE_SIZE    NUMBER        NOT NULL,
    STATUS       VARCHAR2(50)  NOT NULL,
    DP           TIMESTAMP     NOT NULL,
    DW           TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

ALTER TABLE FILE_IMPORT ADD (
  CONSTRAINT file_import_pk PRIMARY KEY (ID)
);

CREATE SEQUENCE file_import_pk START WITH 1;

COMMENT ON TABLE FILE_IMPORT IS 'Таблица для хранения информации о загруженных и обрабатываемых файлах';

COMMENT ON COLUMN FILE_IMPORT.ID IS 'Уникальный идентификатор файла';
COMMENT ON COLUMN FILE_IMPORT.FILE_NAME IS 'Имя файла';
COMMENT ON COLUMN FILE_IMPORT.FILE_TYPE IS 'Тип файла';
COMMENT ON COLUMN FILE_IMPORT.FILE_SUBTYPE IS 'Подтип файла';
COMMENT ON COLUMN FILE_IMPORT.FILE_SIZE IS 'Размер файла в байтах';
COMMENT ON COLUMN FILE_IMPORT.STATUS IS 'Статус обработки файла';
COMMENT ON COLUMN FILE_IMPORT.DP IS 'Время завершения обработки';
COMMENT ON COLUMN FILE_IMPORT.DW IS 'Дата и время вставки записи';

