package com.agrisetu.service;

import com.agrisetu.dto.NotificationDto;
import com.agrisetu.entity.Notification;
import com.agrisetu.entity.User;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.repository.NotificationRepository;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SecurityUtils securityUtils;

    public NotificationService(NotificationRepository notificationRepository, SecurityUtils securityUtils) {
        this.notificationRepository = notificationRepository;
        this.securityUtils = securityUtils;
    }

    @Transactional
    public void createNotification(User recipient, String type, String title, String message,
                                   String relatedEntityType, Long relatedEntityId) {
        Notification notification = new Notification(
                recipient, type, title, message, relatedEntityType, relatedEntityId
        );
        notificationRepository.save(notification);
    }

    public List<NotificationDto> getMyNotifications() {
        User user = securityUtils.getCurrentUser();
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public long getUnreadCount() {
        User user = securityUtils.getCurrentUser();
        return notificationRepository.countByRecipientIdAndIsReadFalse(user.getId());
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        User user = securityUtils.getCurrentUser();
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        if (notification.getRecipient().getId().equals(user.getId())) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void markAllAsRead() {
        User user = securityUtils.getCurrentUser();
        List<Notification> list = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(user.getId());
        for (Notification n : list) {
            n.setRead(true);
        }
        notificationRepository.saveAll(list);
    }

    private NotificationDto mapToDto(Notification n) {
        return new NotificationDto(
                n.getId(),
                n.getType(),
                n.getTitle(),
                n.getMessage(),
                n.getRelatedEntityType(),
                n.getRelatedEntityId(),
                n.isRead(),
                n.getCreatedAt()
        );
    }
}
