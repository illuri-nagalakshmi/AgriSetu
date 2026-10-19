package com.agrisetu.repository;

import com.agrisetu.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByAssignmentIdAndReviewerId(Long assignmentId, Long reviewerId);

    List<Review> findByTargetUserIdOrderByCreatedAtDesc(Long targetUserId);

    @Query("SELECT COALESCE(AVG(CAST(r.rating AS double)), 0.0) FROM Review r WHERE r.targetUser.id = :userId")
    Double getAverageRatingForUser(@Param("userId") Long userId);

    long countByTargetUserId(Long targetUserId);
}
