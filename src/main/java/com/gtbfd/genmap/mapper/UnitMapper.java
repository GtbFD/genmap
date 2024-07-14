package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.UnitDTO;
import com.gtbfd.genmap.vo.UnitVO;
import com.gtbfd.genmap.vo.UserVO;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    public Unit toMap(UnitDTO unitDTO){
        return Unit.builder().build();
    }

    public UnitDTO toDTO(Unit unit){
        return new UnitDTO(unit.getCnpj());
    }

    public UnitVO toVO(Unit unit){
        return new UnitVO(unit.getId(), unit.getNome(), unit.getCnpj(), unit.getLogradouro(), unit.getBairro(), unit.getMunicipio(), unit.getUf());
    }
}
