package com.accenture.franchise.infrastructure.config;

import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.application.usecase.*;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaFranchiseRepository;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaFranchiseRepositorySpring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FranchiseMapper franchiseMapper() {
        return new FranchiseMapper();
    }

    @Bean
    public FranchiseValidationService franchiseValidationService() {
        return new FranchiseValidationService();
    }

    @Bean
    public FranchiseRepository franchiseRepository(JpaFranchiseRepositorySpring springRepository) {
        return new JpaFranchiseRepository(springRepository);
    }

    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(FranchiseRepository franchiseRepository,
                                                          FranchiseValidationService validationService,
                                                          FranchiseMapper franchiseMapper) {
        return new CreateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Bean
    public GetFranchisesUseCase getFranchisesUseCase(FranchiseRepository franchiseRepository,
                                                      FranchiseMapper franchiseMapper) {
        return new GetFranchisesUseCase(franchiseRepository, franchiseMapper);
    }

    @Bean
    public GetFranchiseByIdUseCase getFranchiseByIdUseCase(FranchiseRepository franchiseRepository,
                                                            FranchiseMapper franchiseMapper) {
        return new GetFranchiseByIdUseCase(franchiseRepository, franchiseMapper);
    }

    @Bean
    public UpdateFranchiseUseCase updateFranchiseUseCase(FranchiseRepository franchiseRepository,
                                                          FranchiseValidationService validationService,
                                                          FranchiseMapper franchiseMapper) {
        return new UpdateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Bean
    public DeleteFranchiseUseCase deleteFranchiseUseCase(FranchiseRepository franchiseRepository) {
        return new DeleteFranchiseUseCase(franchiseRepository);
    }
}
