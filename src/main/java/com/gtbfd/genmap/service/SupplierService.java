package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Company;
import com.gtbfd.genmap.domain.Supplier;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.mapper.SupplierMapper;
import com.gtbfd.genmap.repository.SupplierRepository;
import com.gtbfd.genmap.util.CompanySearch;
import com.gtbfd.genmap.vo.CompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CompanySearch companySearch;

    @Autowired
    private SupplierMapper supplierMapper;

    private Logger LOGGER = LoggerFactory.getLogger(UnitService.class);

    private final String className = UnitService.class.getName();

    public CompanyVO create(CompanyDTO companyDTO) {
        if (Objects.nonNull(companyDTO)) {
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a supplier", className);
            Company company = companySearch.searchCompanyByCnpjOnInternet(companyDTO.cnpj());
            Supplier companyFound = supplierMapper.companyToSupplier(company);
            if (Objects.nonNull(companyFound)) {
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Company found successfuly", companyFound, className);
                Supplier supplierCreated = supplierRepository.save(companyFound);
                CompanyVO supplierVO = supplierMapper.toVO(supplierCreated);
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Supplier created successfuly", supplierVO, className);
                return supplierVO;
            }
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new supplier", className);
        return null;
    }

    public boolean deleteByCnpj(String cnpj) {
        Optional<Supplier> companyFound = supplierRepository.findByCnpj(cnpj);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to delete a supplier", className);

        if (companyFound.isPresent() && !companyFound.get().isDeleted()) {
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Company found successfuly", companyFound.get(), className);

            Supplier supplier = companyFound.orElseThrow();
            supplier.setDeleted(true);
            supplierRepository.save(supplier);
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Company deleted successfuly", supplier, className);

            return true;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete a supplier", className);

        return false;
    }

    public CompanyVO findByCnpj(String cnpj) {
        Optional<Supplier> companyFound = supplierRepository.findByCnpj(cnpj);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a supplier by CNPJ", className);

        if (companyFound.isPresent() && !companyFound.get().isDeleted()) {
            Supplier supplier = companyFound.get();
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Company found successfuly", supplier, className);
            return supplierMapper.toVO(supplier);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a supplier", className);
        return null;
    }

    public List<CompanyVO> findByNome(String nome){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to findsupplier by name", className);
        List<Supplier> companiesFound = supplierRepository.findByNomeContainingIgnoreCase(nome);

        if (companiesFound.isEmpty()){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find supplier by name", className);
            return null;
        }
        List<CompanyVO> companies = new ArrayList<>();
        companiesFound.forEach(supplier -> {
            companies.add(supplierMapper.toVO(supplier));
        });
        LOGGER.info("[DEBUG]: Message = {}, Suppliers = {}, Class = {}", "Suppliers found by name", companies, className);
        return companies;
    }
}
