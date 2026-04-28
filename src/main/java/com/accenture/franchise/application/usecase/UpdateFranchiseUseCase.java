package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.dto.UpdateFranchiseRequest;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Franquicia;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import com.accenture.franchise.infrastructure.shared.DateUtils;

public class UpdateFranchiseUseCase {

    private final FranquiciaRepository franchiseRepository;
    private final FranchiseValidationService validationService;
    private final FranchiseMapper franchiseMapper;

    public UpdateFranchiseUseCase(FranquiciaRepository franchiseRepository,
                                   FranchiseValidationService validationService,
                                   FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.validationService = validationService;
        this.franchiseMapper = franchiseMapper;
    }

    public FranchiseResponse execute(String id, UpdateFranchiseRequest request) {
        Franquicia existing = franchiseRepository.findById(id)
                .orElseThrow(() -> new FranchiseNotFoundException(id));

        validationService.isUuidValidate(id);
        
        Franquicia updated = new Franquicia(
                existing.getIdFranquicia(),
                request.name(),
                existing.getFechaCreacion(),
                DateUtils.nowBogota()
        );

        validationService.validate(updated);
        Franquicia saved = franchiseRepository.save(updated);
        return franchiseMapper.toResponse(saved);
    }
}
