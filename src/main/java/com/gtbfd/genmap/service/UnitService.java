package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.UnitDTO;
import com.gtbfd.genmap.mapper.UnitMapper;
import com.gtbfd.genmap.repository.UnitRepository;
import com.gtbfd.genmap.util.CnpjFormatter;
import com.gtbfd.genmap.vo.UnitVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CnpjFormatter cnpjFormatter;

    @Autowired
    private UnitMapper unitMapper;

    private Logger LOGGER = LoggerFactory.getLogger(UnitService.class);

    private final String className = UnitService.class.getName();

    public UnitVO create(UnitDTO unitDTO){
        if (Objects.nonNull(unitDTO)){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create unit", className);
            Unit unitFound = searchCompanyByCnpjOnInternet(unitDTO.cnpj());
            if (Objects.nonNull(unitFound)) {
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Unit found successfuly", unitFound, className);
                Unit unitCreated = unitRepository.save(unitFound);
                UnitVO unitVO = unitMapper.toVO(unitCreated);
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Unit created successfuly", unitVO, className);
                return unitVO;
            }
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new unit", className);
        return null;
    }

    public Unit searchCompanyByCnpjOnInternet(String cnpj){

        String cnpjFormatted = cnpjFormatter.deformatCNPJ(cnpj);

        String url = "https://receitaws.com.br/v1/cnpj/" + cnpjFormatted;

        RestTemplate restTemplate = new RestTemplate();
        Unit unitFound = restTemplate.getForEntity(url, Unit.class).getBody();

        if (Objects.nonNull(unitFound)){
            return unitFound;
        }
        return null;
    }

    public UnitVO findByCnpj(UnitDTO unitDTO){
        if (Objects.nonNull(unitDTO)){
            Optional<Unit> unit = unitRepository.findByCnpj(unitDTO.cnpj());
            if (unit.isPresent()){
                return unitMapper.toVO(unit.orElseThrow());
            }
        }
        return null;
    }

    public UnitVO findById(Long id){
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
