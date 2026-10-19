package com.agrisetu.repository;

import com.agrisetu.entity.Assignment;
import com.agrisetu.entity.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findByJobIdAndLabourerId(Long jobId, Long labourerId);

    boolean existsByJobIdAndLabourerId(Long jobId, Long labourerId);

    List<Assignment> findByJobIdOrderByCreatedAtDesc(Long jobId);

    List<Assignment> findByLabourerIdOrderByCreatedAtDesc(Long labourerId);

    long countByJobIdAndStatus(Long jobId, AssignmentStatus status);

    long countByLabourerId(Long labourerId);

    long countByLabourerIdAndStatus(Long labourerId, AssignmentStatus status);

    @Query("SELECT a FROM Assignment a WHERE a.job.farmer.id = :farmerId ORDER BY a.createdAt DESC")
    List<Assignment> findByFarmerId(@Param("farmerId") Long farmerId);

    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.job.farmer.id = :farmerId AND a.status = :status")
    long countByFarmerIdAndStatus(@Param("farmerId") Long farmerId, @Param("status") AssignmentStatus status);

    /**
     * Checks if two users share an active/confirmed assignment to authorize revealing phone numbers.
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Assignment a " +
           "WHERE ((a.job.farmer.id = :userId1 AND a.labourer.id = :userId2) OR " +
           "       (a.job.farmer.id = :userId2 AND a.labourer.id = :userId1)) " +
           "AND a.status IN ('CONFIRMED', 'IN_PROGRESS', 'COMPLETED')")
    boolean hasAuthorizedAssignmentBetween(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
