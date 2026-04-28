package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.dto.BranchRequest;
import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.dto.UpdateBranchRequest;
import com.accenture.franchise.application.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branches", description = "API for managing branches")
public class SucursalController {

    private final CreateBranchUseCase createBranchUseCase;
    private final GetBranchUseCase getBranchUseCase;
    private final GetBranchByIdUseCase getBranchByIdUseCase;
    private final DeleteBranchUseCase deleteBranchUseCase;
    private final UpdateBranchUseCase updateBranchUseCase;

    public SucursalController(CreateBranchUseCase createBranchUseCase,
                              GetBranchUseCase getBranchUseCase,
                              GetBranchByIdUseCase getBranchByIdUseCase,
                              DeleteBranchUseCase deleteBranchUseCase,
                              UpdateBranchUseCase updateBranchUseCase) {
        this.createBranchUseCase = createBranchUseCase;
        this.getBranchUseCase = getBranchUseCase;
        this.getBranchByIdUseCase = getBranchByIdUseCase;
        this.deleteBranchUseCase = deleteBranchUseCase;
        this.updateBranchUseCase = updateBranchUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a branch", description = "Creates a new branch")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Branch created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createBranchUseCase.execute(request));
    }

    @GetMapping
    @Operation(summary = "Get all branches", description = "Returns a list of all branches")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    public ResponseEntity<List<BranchResponse>> getAll() {
        return ResponseEntity.ok(getBranchUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Branch found"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    public ResponseEntity<BranchResponse> getById(
            @Parameter(description = "Branch UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(getBranchByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a branch")
    @ApiResponse(responseCode = "200", description = "Branch updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Branch not found")
    public ResponseEntity<BranchResponse> update(
            @Parameter(description = "Branch UUID") @PathVariable String id,
            @Valid @RequestBody UpdateBranchRequest request) {
        return ResponseEntity.ok(updateBranchUseCase.execute(id, request));
    }
  
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a branch")
    @ApiResponse(responseCode = "204", description = "Branch deleted successfully")
    @ApiResponse(responseCode = "404", description = "Branch not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Branch UUID") @PathVariable UUID id) {
        deleteBranchUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
