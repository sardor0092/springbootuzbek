package net.idrok.idrok.repository;

import net.idrok.idrok.entity.FileStoregeStatus;
import net.idrok.idrok.entity.FileStorge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorgeRepository extends JpaRepository<FileStorge, Long> {
    FileStorge findByHashId(String hashId);

    List<FileStorge> findAllByFileStorageStatus(FileStoregeStatus status);

}
