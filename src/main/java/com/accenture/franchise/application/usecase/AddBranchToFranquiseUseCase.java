package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.GetAffiliationRequest;
import com.accenture.franchise.application.dto.Response;
import com.accenture.franchise.application.mapper.MasterMapper;
import com.accenture.franchise.domain.exception.BranchNotFoundException;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Afiliacion;
import com.accenture.franchise.domain.repository.AfiliacionRepository;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.infrastructure.shared.DateUtils;
import com.accenture.franchise.infrastructure.shared.Functions;

public class AddBranchToFranquiseUseCase {
    
    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;
    private final AfiliacionRepository afiliacionRepository;
    private final MasterMapper masterMapper;

    public AddBranchToFranquiseUseCase(
        SucursalRepository sucursalRepository,
        FranquiciaRepository franquiciaRepository,
        AfiliacionRepository afiliacionRepository,
        MasterMapper masterMapper
    ) {
        this.sucursalRepository = sucursalRepository;
        this.franquiciaRepository = franquiciaRepository;
        this.afiliacionRepository = afiliacionRepository;
        this.masterMapper = masterMapper;
    }

    public Response execute(GetAffiliationRequest request) {

        Functions.isUuidValidate(request.branchId());
        Functions.isUuidValidate(request.franchiseId());

        franquiciaRepository.findById(request.franchiseId())
                .orElseThrow(() -> new FranchiseNotFoundException(request.franchiseId()));

        sucursalRepository.findById(request.branchId())
                .orElseThrow(() -> new BranchNotFoundException(request.branchId()));

        Afiliacion afiliacion = new Afiliacion(
                request.branchId(),
                request.franchiseId(),
                DateUtils.nowBogota(),
                null
        );

        afiliacionRepository.save(afiliacion);
        
        return masterMapper.toResponse("Affiliation created successfully", true, null);
    }
}
