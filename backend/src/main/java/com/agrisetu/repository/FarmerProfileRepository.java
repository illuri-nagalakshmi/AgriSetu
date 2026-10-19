package com.agrisetu.repository;

import com.agrisetu.entity.FarmerProfile;
import com.agrisetu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, Long> {
    Optional<FarmerProfile> findByUserId(Long userId);
    Optional<FarmerProfile> findByUser(User user);
}
