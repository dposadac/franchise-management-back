package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.dto.ProductRequest;
import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.dto.UpdateBranchRequest;
import com.accenture.franchise.application.dto.UpdateProductRequest;
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
@RequestMapping("/api/products")
@Tag(name = "Products", description = "API for managing products")
public class ProductoController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    public ProductoController(CreateProductUseCase createProductUseCase,
                              GetProductUseCase getProductUseCase,
                              GetProductByIdUseCase getProductByIdUseCase,
                              DeleteProductUseCase deleteProductUseCase,
                              UpdateProductUseCase updateProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a product", description = "Creates a new product")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductUseCase.execute(request));
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(getProductUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<ProductResponse> getById(
            @Parameter(description = "Product UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(getProductByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<ProductResponse> update(
            @Parameter(description = "Product UUID") @PathVariable String id,
            @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(updateProductUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Product UUID") @PathVariable UUID id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
