package com.agrisetu.repository;

import com.agrisetu.entity.Application;
import com.agrisetu.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByJobIdAndLabourerId(Long jobId, Long labourerId);

    boolean existsByJobIdAndLabourerId(Long jobId, Long labourerId);

    List<Application> findByJobIdOrderByAppliedAtDesc(Long jobId);

    List<Application> findByLabourerIdOrderByAppliedAtDesc(Long labourerId);

    long countByJobIdAndStatus(Long jobId, ApplicationStatus status);

    long countByLabourerId(Long labourerId);

    long countByLabourerIdAndStatus(Long labourerId, ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.farmer.id = :farmerId")
    long countTotalApplicationsForFarmer(@Param("farmerId") Long farmerId);

    @Query("SELECT a FROM Application a WHERE a.job.farmer.id = :farmerId ORDER BY a.appliedAt DESC")
    List<Application> findRecentApplicationsForFarmer(@Param("farmerId") Long farmerId);
}
