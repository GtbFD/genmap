package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Company;
import com.gtbfd.genmap.domain.Supplier;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.mapper.SupplierMapper;
import com.gtbfd.genmap.repository.SupplierRepository;
import com.gtbfd.genmap.util.CnpjFormatter;
import com.gtbfd.genmap.util.CompanySearch;
import com.gtbfd.genmap.vo.CompanyVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private CnpjFormatter cnpjFormatter;

    @Mock
    private CompanySearch companySearch;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierService supplierService;

    private SupplierMapper supplierMapperTest;

    private Supplier supplier;

    @BeforeEach
    public void setUp(){
        supplierMapperTest = new SupplierMapper();

        supplier = Supplier.builder()
                .id(1L)
                .nome("SECRETARIA DE ESTADO DA SAUDE - SES")
                .cnpj("08778268002023")
                .logradouro("RUA CASTRO ALVES")
                .bairro("CENTRO")
                .municipio("CAJAZEIRAS")
                .uf("PB")
                .build();
    }

    @Test
    public void whenCreateThenReturnNewUnit(){
        CompanyDTO companyDTO = supplierMapperTest.toDTO(supplier);

        CompanyVO companyVO = new CompanyVO(supplier.getId(), supplier.getNome(), supplier.getCnpj(), supplier.getLogradouro(), supplier.getBairro(), supplier.getMunicipio(), supplier.getUf());

        Supplier supplierTest = Supplier.builder().build();

        Mockito.when(companySearch.searchCompanyByCnpjOnInternet(companyDTO.cnpj())).thenReturn(supplier);
        Mockito.when(supplierMapper.companyToSupplier(ArgumentMatchers.any(Company.class))).thenReturn(supplierTest);
        Mockito.when(supplierRepository.save(ArgumentMatchers.any(Supplier.class))).thenReturn(supplier);
        Mockito.when(supplierMapper.toVO(supplier)).thenReturn(companyVO);

        CompanyVO unitCreated = supplierService.create(companyDTO);

        System.out.println(unitCreated);
    }

    @Test
    public void whenInvalidCnpjThenReturnNull(){
        String cnpj = "00.000.000/0000-00";
        Supplier supplierTest = Supplier.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();
        CompanyDTO companyDTO = supplierMapperTest.toDTO(supplierTest);

        Mockito.when(companySearch.searchCompanyByCnpjOnInternet(cnpj)).thenReturn(null);

        CompanyVO response = supplierService.create(companyDTO);

        Assertions.assertNull(response);
        Mockito.verify(supplierRepository, Mockito.times(0)).save(supplier);
    }

    @Test
    public void whenFindByCnpjThenReturnUnit(){
        Supplier unitTest = Supplier.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();
        CompanyVO companyVO = new CompanyVO(unitTest.getId(), unitTest.getNome(), unitTest.getCnpj(), unitTest.getLogradouro(), unitTest.getBairro(), unitTest.getMunicipio(), unitTest.getUf());
        CompanyDTO companyDTO = supplierMapperTest.toDTO(supplier);


        Mockito.when(supplierRepository.findByCnpj(companyDTO.cnpj())).thenReturn(Optional.of(unitTest));
        Mockito.when(supplierMapper.toVO(unitTest)).thenReturn(companyVO);

        CompanyVO unitFound = supplierService.findByCnpj(companyDTO.cnpj());

        Assertions.assertNotNull(unitFound);
        Mockito.verify(supplierRepository, Mockito.times(1)).findByCnpj(companyDTO.cnpj());
        Assertions.assertEquals(unitTest.getCnpj(), unitFound.cpnj());
    }

    @Test
    public void whenFindByWithInvalidCnpjThenReturnNull(){
        Long id = 9108189L;
        Supplier supplierTest = Supplier.builder()
                .nome("Test")
                .cnpj("00.000.000/0001-99")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();

        Mockito.when(supplierRepository.findByCnpj(supplierTest.getCnpj())).thenReturn(Optional.empty());

        CompanyVO unitFound = supplierService.findByCnpj(supplierTest.getCnpj());

        Assertions.assertNull(unitFound);
        Mockito.verify(supplierMapper, Mockito.times(0)).toVO(supplierTest);
    }

    @Test
    public void whenDeleteThenTrue(){
        Long id = 1L;
        Supplier supplierTest = Supplier.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();

        Mockito.when(supplierRepository.findByCnpj(supplierTest.getCnpj())).thenReturn(Optional.of(supplierTest));

        boolean isDeleted = supplierService.deleteByCnpj(supplierTest.getCnpj());

        Assertions.assertTrue(isDeleted);
    }

    @Test
    public void whenDeleteThenFalse(){
        String cnpj = "00000000000000";

        Mockito.when(supplierRepository.findByCnpj(cnpj)).thenReturn(Optional.empty());

        boolean isDeleted = supplierService.deleteByCnpj(cnpj);

        Assertions.assertFalse(isDeleted);
    }
}
