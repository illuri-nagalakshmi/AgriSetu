package com.agrisetu.controller;

import com.agrisetu.dto.ApiResponse;
import com.agrisetu.dto.AssignmentDto;
import com.agrisetu.dto.AssignmentStatusUpdateRequest;
import com.agrisetu.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<AssignmentDto>>> getMyAssignments() {
        List<AssignmentDto> list = assignmentService.getMyAssignments();
        return ResponseEntity.ok(ApiResponse.success("Assignments retrieved", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssignmentDto>> getAssignmentById(@PathVariable Long id) {
        AssignmentDto assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Assignment details retrieved", assignment));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AssignmentDto>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentStatusUpdateRequest request) {
        AssignmentDto updated = assignmentService.updateAssignmentStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Assignment status updated", updated));
    }

    @PostMapping("/{id}/confirm-completion")
    public ResponseEntity<ApiResponse<AssignmentDto>> confirmCompletion(@PathVariable Long id) {
        AssignmentDto updated = assignmentService.confirmCompletion(id);
        return ResponseEntity.ok(ApiResponse.success("Completion confirmed successfully", updated));
    }
}
