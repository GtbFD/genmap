package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.Company;
import com.gtbfd.genmap.domain.Supplier;
import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.vo.CompanyVO;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier companyToSupplier(Company company){
        return Supplier.builder()
                .nome(company.getNome())
                .cnpj(company.getCnpj())
                .logradouro(company.getLogradouro())
                .bairro(company.getBairro())
                .municipio(company.getMunicipio())
                .uf(company.getUf())
                .build();
    }
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
