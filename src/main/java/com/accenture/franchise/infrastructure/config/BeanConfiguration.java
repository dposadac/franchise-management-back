package com.accenture.franchise.infrastructure.config;

import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.application.mapper.MasterMapper;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.application.usecase.*;
import com.accenture.franchise.domain.repository.AfiliacionRepository;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.repository.InventarioRepository;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.repository.TopProductRepository;
import com.accenture.franchise.domain.service.BranchValidationService;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import com.accenture.franchise.domain.service.ProductValidationService;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaAfiliacionRepository;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaAfiliacionRepositorySpring;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaEstadoPagoRepositorySpring;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaFranquiciaRepository;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaFranquiciaRepositorySpring;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaInventarioRepository;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaInventarioRepositorySpring;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaProductoRepository;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaProductoRepositorySpring;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaSucursalRepository;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaSucursalRepositorySpring;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaTopProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    // ── Franchise ────────────────────────────────────────────────────────────

    @Bean
    public FranchiseMapper franchiseMapper() {
        return new FranchiseMapper();
    }

    @Bean
    public FranchiseValidationService franchiseValidationService() {
        return new FranchiseValidationService();
    }

    @Bean
    public ProductValidationService productValidationService() {
        return new ProductValidationService();
    }

    @Bean
    public BranchValidationService branchValidationService() {
        return new BranchValidationService();
    }

    @Bean
    public FranquiciaRepository franchiseRepository(JpaFranquiciaRepositorySpring springRepository) {
        return new JpaFranquiciaRepository(springRepository);
    }

    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(FranquiciaRepository franchiseRepository,
                                                          FranchiseValidationService validationService,
                                                          FranchiseMapper franchiseMapper) {
        return new CreateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Bean
    public GetFranchisesUseCase getFranchisesUseCase(FranquiciaRepository franchiseRepository,
                                                      FranchiseMapper franchiseMapper) {
        return new GetFranchisesUseCase(franchiseRepository, franchiseMapper);
    }

    @Bean
    public GetFranchiseByIdUseCase getFranchiseByIdUseCase(FranquiciaRepository franchiseRepository,
                                                            FranchiseMapper franchiseMapper, FranchiseValidationService franchiseValidationService) {
        return new GetFranchiseByIdUseCase(franchiseRepository, franchiseMapper, franchiseValidationService);
    }

    @Bean
    public UpdateFranchiseUseCase updateFranchiseUseCase(FranquiciaRepository franchiseRepository,
                                                          FranchiseValidationService validationService,
                                                          FranchiseMapper franchiseMapper) {
        return new UpdateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Bean
    public DeleteFranchiseUseCase deleteFranchiseUseCase(FranquiciaRepository franchiseRepository, FranchiseValidationService franchiseValidationService) {
        return new DeleteFranchiseUseCase(franchiseRepository, franchiseValidationService);
    }

    // ── Producto ─────────────────────────────────────────────────────────────

    @Bean
    public ProductoMapper productoMapper() {
        return new ProductoMapper();
    }

    @Bean
    public ProductoRepository productoRepository(JpaProductoRepositorySpring springRepository) {
        return new JpaProductoRepository(springRepository);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(ProductoRepository productoRepository,
                                                    ProductoMapper productoMapper, ProductValidationService productValidationService) {
        return new CreateProductUseCase(productoRepository, productoMapper, productValidationService);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductoRepository productoRepository,
                                                ProductoMapper productoMapper) {
        return new GetProductUseCase(productoRepository, productoMapper);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase(ProductoRepository productoRepository,
                                                        ProductoMapper productoMapper, ProductValidationService productValidationService) {
        return new GetProductByIdUseCase(productoRepository, productoMapper, productValidationService);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductoRepository productoRepository, ProductValidationService productValidationService) {
        return new DeleteProductUseCase(productoRepository, productValidationService);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(ProductoRepository productoRepository, ProductoMapper productoMapper, ProductValidationService productValidationService) {
        return new UpdateProductUseCase(productoRepository, productoMapper, productValidationService);
    }

    // ── Sucursal ─────────────────────────────────────────────────────────────

    @Bean
    public SucursalMapper sucursalMapper() {
        return new SucursalMapper();
    }

    @Bean
    public SucursalRepository sucursalRepository(JpaSucursalRepositorySpring springRepository) {
        return new JpaSucursalRepository(springRepository);
    }

    @Bean
    public CreateBranchUseCase createBranchUseCase(SucursalRepository sucursalRepository,
                                                    SucursalMapper sucursalMapper, BranchValidationService branchValidationService) {
        return new CreateBranchUseCase(sucursalRepository, sucursalMapper, branchValidationService);
    }

    @Bean
    public GetBranchUseCase getBranchUseCase(SucursalRepository sucursalRepository,
                                              SucursalMapper sucursalMapper) {
        return new GetBranchUseCase(sucursalRepository, sucursalMapper);
    }

    @Bean
    public GetBranchByIdUseCase getBranchByIdUseCase(SucursalRepository sucursalRepository,
                                                      SucursalMapper sucursalMapper, BranchValidationService branchValidationService) {
        return new GetBranchByIdUseCase(sucursalRepository, sucursalMapper, branchValidationService);
    }

    @Bean
    public DeleteBranchUseCase deleteBranchUseCase(SucursalRepository sucursalRepository, BranchValidationService branchValidationService) {
        return new DeleteBranchUseCase(sucursalRepository, branchValidationService);
    }

    @Bean
    public UpdateBranchUseCase updateBranchUseCase(SucursalRepository sucursalRepository, SucursalMapper sucursalMapper, BranchValidationService branchValidationService) {
        return new UpdateBranchUseCase(sucursalRepository, sucursalMapper, branchValidationService);
    }

    // ── Top Product Query ────────────────────────────────────────────────────

    @Bean
    public TopProductRepository topProductRepository(EntityManager entityManager) {
        return new JpaTopProductRepository(entityManager);
    }

    // ── Afiliacion ───────────────────────────────────────────────────────────

    @Bean
    public AfiliacionRepository afiliacionRepository(JpaAfiliacionRepositorySpring springRepository,
                                                      JpaSucursalRepositorySpring sucursalRepo,
                                                      JpaFranquiciaRepositorySpring franquiciaRepo) {
        return new JpaAfiliacionRepository(springRepository, sucursalRepo, franquiciaRepo);
    }

    // ── Inventario ───────────────────────────────────────────────────────────

    @Bean
    public InventarioRepository inventarioRepository(JpaInventarioRepositorySpring springRepository,
                                                      JpaSucursalRepositorySpring sucursalRepo,
                                                      JpaProductoRepositorySpring productoRepo,
                                                      JpaEstadoPagoRepositorySpring estadoPagoRepo) {
        return new JpaInventarioRepository(springRepository, sucursalRepo, productoRepo, estadoPagoRepo);
    }

    // ── Maestros ────────────────────────────────────────────────────

    @Bean
    public MasterMapper masterMapper() {
        return new MasterMapper();
    }

    // ── Use Cases (Afiliacion / Inventario) ──────────────────────────────────

    @Bean
    public AddBranchToFranquiseUseCase addBranchToFranquiseUseCase(SucursalRepository sucursalRepository,
                                                                    FranquiciaRepository franquiciaRepository,
                                                                    AfiliacionRepository afiliacionRepository,
                                                                    MasterMapper masterMapper) {
        return new AddBranchToFranquiseUseCase(sucursalRepository, franquiciaRepository, afiliacionRepository, masterMapper);
    }

    @Bean
    public AddProductToBranchUseCase addProductToBranchUseCase(SucursalRepository sucursalRepository,
                                                                ProductoRepository productoRepository,
                                                                InventarioRepository inventarioRepository,
                                                                MasterMapper masterMapper) {
        return new AddProductToBranchUseCase(sucursalRepository, productoRepository, inventarioRepository, masterMapper);
    }

    @Bean
    public DeleteProductFromBranchUseCase deleteProductFromBranchUseCase(ProductoRepository productoRepository,
                                                                         SucursalRepository sucursalRepository,
                                                                         InventarioRepository inventarioRepository,
                                                                         MasterMapper masterMapper) {
        return new DeleteProductFromBranchUseCase(productoRepository, sucursalRepository, inventarioRepository, masterMapper);
    }

    @Bean
    public GetStockMaxByBranchAndFranquiseFromInventory getStockMaxByBranchAndFranquiseFromInventory(
            TopProductRepository topProductRepository,
            FranquiciaRepository franchiseRepository,
            SucursalRepository sucursalRepository,
            MasterMapper masterMapper) {
        return new GetStockMaxByBranchAndFranquiseFromInventory(topProductRepository, franchiseRepository, sucursalRepository, masterMapper);
    }

    @Bean
    public UpdateStockFromInventoryProduct updateStockFromInventoryProduct(SucursalRepository sucursalRepository,
                                                                           ProductoRepository productoRepository,
                                                                           InventarioRepository inventarioRepository,
                                                                           MasterMapper masterMapper) {
        return new UpdateStockFromInventoryProduct(sucursalRepository, productoRepository, inventarioRepository, masterMapper);
    }
}
