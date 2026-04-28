package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.GetAffiliationRequest;
import com.accenture.franchise.application.dto.Response;
import com.accenture.franchise.application.mapper.MasterMapper;
import com.accenture.franchise.domain.model.TopProductByBranch;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.repository.TopProductRepository;

import java.util.List;

public class GetStockMaxByBranchAndFranquiseFromInventory {

    private final TopProductRepository topProductRepository;
    private final FranquiciaRepository franchiseRepository;
    private final SucursalRepository sucursalRepository;
    private final MasterMapper mapperMaster;

    public GetStockMaxByBranchAndFranquiseFromInventory(
        TopProductRepository topProductRepository,
        FranquiciaRepository franchiseRepository,
        SucursalRepository sucursalRepository,
        MasterMapper mapper
    ) {
        this.topProductRepository = topProductRepository;
        this.franchiseRepository = franchiseRepository;
        this.sucursalRepository = sucursalRepository;
        this.mapperMaster = mapper;
    }

    public Response execute(GetAffiliationRequest request) {
        franchiseRepository.findById(request.franchiseId())
                .orElseThrow(() -> new RuntimeException("Franchise not found with id: " + request.franchiseId()));

        sucursalRepository.findById(request.branchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + request.branchId()));

        List<TopProductByBranch> topProducts = topProductRepository
                .findTopProductsByFranchiseAndBranch(request.franchiseId(), request.branchId());

        return mapperMaster.toResponse("Top products retrieved successfully", true, null, List.copyOf(topProducts));
    }
}
