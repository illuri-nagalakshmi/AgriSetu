package com.agrisetu.repository;

import com.agrisetu.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByLabourerIdOrderBySavedAtDesc(Long labourerId);
    boolean existsByLabourerIdAndJobId(Long labourerId, Long jobId);
    Optional<SavedJob> findByLabourerIdAndJobId(Long labourerId, Long jobId);
    void deleteByLabourerIdAndJobId(Long labourerId, Long jobId);
}
