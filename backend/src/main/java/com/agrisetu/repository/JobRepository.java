package com.agrisetu.repository;

import com.agrisetu.entity.Job;
import com.agrisetu.entity.JobStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @EntityGraph(attributePaths = {"farmer"})
    List<Job> findByFarmerIdOrderByCreatedAtDesc(Long farmerId);

    @EntityGraph(attributePaths = {"farmer"})
    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"farmer"})
    Optional<Job> findById(Long id);

    long countByFarmerId(Long farmerId);

    long countByFarmerIdAndStatus(Long farmerId, JobStatus status);

    long countByStatus(JobStatus status);

    /**
     * Concurrency-safe pessimistic write lock to prevent race conditions during worker allocation.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT j FROM Job j WHERE j.id = :id")
    Optional<Job> findByIdWithPessimisticLock(@Param("id") Long id);

    @EntityGraph(attributePaths = {"farmer"})
    @Query("SELECT j FROM Job j WHERE (:status IS NULL OR j.status = :status) " +
           "AND (:category IS NULL OR :category = '' OR j.category = :category) " +
           "AND (:district IS NULL OR :district = '' OR LOWER(j.district) LIKE LOWER(CONCAT('%', :district, '%'))) " +
           "AND (:state IS NULL OR :state = '' OR LOWER(j.state) LIKE LOWER(CONCAT('%', :state, '%'))) " +
           "AND (:minWage IS NULL OR j.wageAmount >= :minWage) " +
           "AND (:maxWage IS NULL OR j.wageAmount <= :maxWage) " +
           "AND (:mealsProvided IS NULL OR j.mealsProvided = :mealsProvided) " +
           "AND (:accommodationProvided IS NULL OR j.accommodationProvided = :accommodationProvided)")
    Page<Job> searchJobs(
            @Param("status") JobStatus status,
            @Param("category") String category,
            @Param("district") String district,
            @Param("state") String state,
            @Param("minWage") BigDecimal minWage,
            @Param("maxWage") BigDecimal maxWage,
            @Param("mealsProvided") Boolean mealsProvided,
            @Param("accommodationProvided") Boolean accommodationProvided,
            Pageable pageable
    );
}
