package com.ten022.diary.repository;

import com.ten022.diary.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
