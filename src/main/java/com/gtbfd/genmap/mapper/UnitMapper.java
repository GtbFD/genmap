package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.vo.CompanyVO;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    public Unit toMap(CompanyDTO companyDTO){
        return Unit.builder().build();
    }

    public CompanyDTO toDTO(Unit unit){
        return new CompanyDTO(unit.getCnpj());
    }

    public CompanyVO toVO(Unit unit){
        return new CompanyVO(unit.getId(), unit.getNome(), unit.getCnpj(), unit.getLogradouro(), unit.getBairro(), unit.getMunicipio(), unit.getUf());
    }
}
