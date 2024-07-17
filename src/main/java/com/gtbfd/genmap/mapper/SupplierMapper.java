package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.Supplier;
import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.vo.CompanyVO;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toMap(CompanyDTO companyDTO){
        return Supplier.builder().build();
    }

    public CompanyDTO toDTO(Supplier supplier){
        return new CompanyDTO(supplier.getCnpj());
    }

    public CompanyVO toVO(Supplier supplier){
        return new CompanyVO(supplier.getId(), supplier.getNome(), supplier.getCnpj(), supplier.getLogradouro(), supplier.getBairro(), supplier.getMunicipio(), supplier.getUf());
    }
}
