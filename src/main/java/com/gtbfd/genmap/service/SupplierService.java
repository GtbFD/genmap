package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Company;
import com.gtbfd.genmap.domain.Supplier;
import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.mapper.SupplierMapper;
import com.gtbfd.genmap.mapper.UnitMapper;
import com.gtbfd.genmap.repository.SupplierRepository;
import com.gtbfd.genmap.repository.UnitRepository;
import com.gtbfd.genmap.util.CompanySearch;
import com.gtbfd.genmap.vo.CompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public CompanyVO create(CompanyDTO companyDTO){
        if (Objects.nonNull(companyDTO)){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a supplier", className);
            Supplier companyFound = (Supplier) companySearch.searchCompanyByCnpjOnInternet(companyDTO.cnpj());
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
}
