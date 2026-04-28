package com.accenture.franchise.infrastructure.entrypoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accenture.franchise.application.dto.DeleteInventoryRequest;
import com.accenture.franchise.application.dto.GetAffiliationRequest;
import com.accenture.franchise.application.dto.InventoryRequest;
import com.accenture.franchise.application.dto.Response;
import com.accenture.franchise.application.dto.UpdateInventoryRequest;
import com.accenture.franchise.application.usecase.AddBranchToFranquiseUseCase;
import com.accenture.franchise.application.usecase.AddProductToBranchUseCase;
import com.accenture.franchise.application.usecase.DeleteProductFromBranchUseCase;
import com.accenture.franchise.application.usecase.GetStockMaxByBranchAndFranquiseFromInventory;
import com.accenture.franchise.application.usecase.UpdateStockFromInventoryProduct;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master")
@Tag(name = "Master", description = "API for managing Master")
public class MaestroController {
    
    private final AddBranchToFranquiseUseCase addBranchToFranquiseUseCase;
    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final DeleteProductFromBranchUseCase deleteProductFromBranchUseCase;
    private final GetStockMaxByBranchAndFranquiseFromInventory getStockMaxByBranchAndFranquiseFromInventory;
    private final UpdateStockFromInventoryProduct updateStockFromInventoryProduct;

    public MaestroController(
        AddBranchToFranquiseUseCase addBranchToFranquiseUseCase,
        AddProductToBranchUseCase addProductToBranchUseCase,
        DeleteProductFromBranchUseCase deleteProductFromBranchUseCase,
        GetStockMaxByBranchAndFranquiseFromInventory getStockMaxByBranchAndFranquiseFromInventory,
        UpdateStockFromInventoryProduct updateStockFromInventoryProduct
    ){
        this.addBranchToFranquiseUseCase = addBranchToFranquiseUseCase;
        this.addProductToBranchUseCase = addProductToBranchUseCase;
        this.deleteProductFromBranchUseCase = deleteProductFromBranchUseCase;
        this.getStockMaxByBranchAndFranquiseFromInventory = getStockMaxByBranchAndFranquiseFromInventory;
        this.updateStockFromInventoryProduct = updateStockFromInventoryProduct;
    }

    @PostMapping("/inventory/addProductToBranch")
    @Operation(summary = "Add product to branch", description = "Adds a product to a specific branch")
    @ApiResponse(responseCode = "201", description = "Product added successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<Response> addProductToBranch(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductToBranchUseCase.execute(request));
    }

    @PostMapping("/affiliation/addBranchByFranchise")
    @Operation(summary = "Add branch to franchise", description = "Adds a new branch to an existing franchise")
    @ApiResponse(responseCode = "201", description = "Branch added successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<Response> addBranchToFranchise(@Valid @RequestBody GetAffiliationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addBranchToFranquiseUseCase.execute(request));
    }

    @PostMapping("/inventory/deleteProductFromBranch")
    @Operation(summary = "Delete product from branch", description = "Removes a product from a specific branch")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<Response> deleteProductFromBranch(@Valid @RequestBody DeleteInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(deleteProductFromBranchUseCase.execute(request));
    }

    @PostMapping("/inventory/getStockMaxByProduct")
    @Operation(summary = "Get max stock by branch and franchise", description = "Returns the product with max stock per branch for a franchise")
    @ApiResponse(responseCode = "200", description = "Stock information retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<Response> getStockMaxByBranchAndFranchise(@Valid @RequestBody GetAffiliationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(getStockMaxByBranchAndFranquiseFromInventory.execute(request));
    }

    @PostMapping("/inventory/updateStock")
    @Operation(summary = "Update product stock in branch", description = "Updates the stock of a product in a specific branch")
    @ApiResponse(responseCode = "200", description = "Stock updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<Response> updateStockFromInventory(@Valid @RequestBody UpdateInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(updateStockFromInventoryProduct.execute(request));
    }
}
