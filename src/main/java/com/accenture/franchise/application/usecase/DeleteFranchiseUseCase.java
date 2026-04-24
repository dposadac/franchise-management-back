package com.accenture.franchise.application.usecase;

import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.repository.FranchiseRepository;

import java.util.UUID;

public class DeleteFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public DeleteFranchiseUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public void execute(UUID id) {
        franchiseRepository.findById(id)
                .orElseThrow(() -> new FranchiseNotFoundException(id));
        franchiseRepository.deleteById(id);
    }
}
