package com.agrisetu.repository;

import com.agrisetu.entity.LabourerProfile;
import com.agrisetu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabourerProfileRepository extends JpaRepository<LabourerProfile, Long> {
    Optional<LabourerProfile> findByUserId(Long userId);
    Optional<LabourerProfile> findByUser(User user);
}
