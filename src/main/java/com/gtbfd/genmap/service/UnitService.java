package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.mapper.UnitMapper;
import com.gtbfd.genmap.repository.UnitRepository;
import com.gtbfd.genmap.util.CompanySearch;
import com.gtbfd.genmap.vo.CompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CompanySearch companySearch;

    @Autowired
    private UnitMapper unitMapper;

    private Logger LOGGER = LoggerFactory.getLogger(UnitService.class);

    private final String className = UnitService.class.getName();

    public CompanyVO create(CompanyDTO companyDTO){
        if (Objects.nonNull(companyDTO)){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a unit", className);
            Unit unitFound = (Unit) companySearch.searchCompanyByCnpjOnInternet(companyDTO.cnpj());
            if (Objects.nonNull(unitFound)) {
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Company found successfuly", unitFound, className);
                Unit unitCreated = unitRepository.save(unitFound);
                CompanyVO companyVO = unitMapper.toVO(unitCreated);
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Unit created successfuly", companyVO, className);
                return companyVO;
            }
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new unit", className);
        return null;
    }

    public CompanyVO findByCnpj(CompanyDTO companyDTO){
        if (Objects.nonNull(companyDTO)){
            Optional<Unit> unit = unitRepository.findByCnpj(companyDTO.cnpj());
            if (unit.isPresent()){
                return unitMapper.toVO(unit.orElseThrow());
            }
        }
        return null;
    }

    public CompanyVO findById(Long id){
        if (Objects.nonNull(id)){
            Optional<Unit> unit = unitRepository.findById(id);
            if (unit.isPresent()){
                return unitMapper.toVO(unit.orElseThrow());
            }
        }
        return null;
    }

    public boolean delete(Long id){
        if (Objects.nonNull(id)){
            Optional<Unit> unitFound = unitRepository.findById(id);
            if (unitFound.isPresent()){
                unitRepository.delete(unitFound.get());
                return true;
            }
        }
        return false;
    }
}
