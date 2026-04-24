package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.dto.UpdateFranchiseRequest;
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
@RequestMapping("/api/franchises")
@Tag(name = "Franchises", description = "API for managing franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final GetFranchisesUseCase getFranchisesUseCase;
    private final GetFranchiseByIdUseCase getFranchiseByIdUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final DeleteFranchiseUseCase deleteFranchiseUseCase;

    public FranchiseController(CreateFranchiseUseCase createFranchiseUseCase,
                                GetFranchisesUseCase getFranchisesUseCase,
                                GetFranchiseByIdUseCase getFranchiseByIdUseCase,
                                UpdateFranchiseUseCase updateFranchiseUseCase,
                                DeleteFranchiseUseCase deleteFranchiseUseCase) {
        this.createFranchiseUseCase = createFranchiseUseCase;
        this.getFranchisesUseCase = getFranchisesUseCase;
        this.getFranchiseByIdUseCase = getFranchiseByIdUseCase;
        this.updateFranchiseUseCase = updateFranchiseUseCase;
        this.deleteFranchiseUseCase = deleteFranchiseUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a franchise", description = "Creates a new franchise with ACTIVE status")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Franchise created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<FranchiseResponse> create(@Valid @RequestBody FranchiseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createFranchiseUseCase.execute(request));
    }

    @GetMapping
    @Operation(summary = "Get all franchises", description = "Returns a list of all franchises")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    public ResponseEntity<List<FranchiseResponse>> getAll() {
        return ResponseEntity.ok(getFranchisesUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get franchise by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Franchise found"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    public ResponseEntity<FranchiseResponse> getById(
            @Parameter(description = "Franchise UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(getFranchiseByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Franchise updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    public ResponseEntity<FranchiseResponse> update(
            @Parameter(description = "Franchise UUID") @PathVariable UUID id,
            @Valid @RequestBody UpdateFranchiseRequest request) {
        return ResponseEntity.ok(updateFranchiseUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Franchise deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Franchise UUID") @PathVariable UUID id) {
        deleteFranchiseUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
