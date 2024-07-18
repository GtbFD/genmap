package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.mapper.UnitMapper;
import com.gtbfd.genmap.repository.UnitRepository;
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
public class UnitServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private CnpjFormatter cnpjFormatter;

    @Mock
    private CompanySearch companySearch;

    @Mock
    private UnitMapper unitMapper;

    @InjectMocks
    private UnitService unitService;

    private UnitMapper unitMapperTest;

    private Unit unit;

    @BeforeEach
    public void setUp(){
        unitMapperTest = new UnitMapper();

        unit = Unit.builder()
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
        CompanyDTO companyDTO = unitMapperTest.toDTO(unit);

        CompanyVO companyVO = new CompanyVO(unit.getId(), unit.getNome(), unit.getCnpj(), unit.getLogradouro(), unit.getBairro(), unit.getMunicipio(), unit.getUf());

        Mockito.when(companySearch.searchCompanyByCnpjOnInternet(companyDTO.cnpj())).thenReturn(unit);
        Mockito.when(unitRepository.save(ArgumentMatchers.any(Unit.class))).thenReturn(unit);
        Mockito.when(unitMapper.toVO(unit)).thenReturn(companyVO);

        CompanyVO unitCreated = unitService.create(companyDTO);

        System.out.println(unitCreated);
    }

    @Test
    public void whenInvalidCnpjThenReturnNull(){
        String cnpj = "00.000.000/0000-00";
        Unit unitTest = Unit.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();
        CompanyDTO companyDTO = unitMapperTest.toDTO(unitTest);

        Mockito.when(companySearch.searchCompanyByCnpjOnInternet(cnpj)).thenReturn(null);

        CompanyVO response = unitService.create(companyDTO);

        Assertions.assertNull(response);
        Mockito.verify(unitRepository, Mockito.times(0)).save(unit);
    }

    @Test
    public void whenFindByCnpjThenReturnUnit(){
        Unit unitTest = Unit.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();
        CompanyVO companyVO = new CompanyVO(unitTest.getId(), unitTest.getNome(), unitTest.getCnpj(), unitTest.getLogradouro(), unitTest.getBairro(), unitTest.getMunicipio(), unitTest.getUf());
        CompanyDTO companyDTO = unitMapperTest.toDTO(unit);


        Mockito.when(unitRepository.findByCnpj(companyDTO.cnpj())).thenReturn(Optional.of(unitTest));
        Mockito.when(unitMapper.toVO(unitTest)).thenReturn(companyVO);

        CompanyVO unitFound = unitService.findByCnpj(companyDTO.cnpj());

        Assertions.assertNotNull(unitFound);
        Mockito.verify(unitRepository, Mockito.times(1)).findByCnpj(companyDTO.cnpj());
        Assertions.assertEquals(unitTest.getCnpj(), unitFound.cpnj());
    }

    @Test
    public void whenFindByWithInvalidCnpjThenReturnNull(){
        Unit unitTest = Unit.builder()
                .nome("Test")
                .cnpj("00.000.000/0001-99")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();
        CompanyVO companyVO = new CompanyVO(unitTest.getId(), unitTest.getNome(), unitTest.getCnpj(), unitTest.getLogradouro(), unitTest.getBairro(), unitTest.getMunicipio(), unitTest.getUf());
        CompanyDTO companyDTO = unitMapperTest.toDTO(unit);

        Mockito.when(unitRepository.findByCnpj(companyDTO.cnpj())).thenReturn(Optional.empty());

        CompanyVO unitFound = unitService.findByCnpj(companyDTO.cnpj());

        Assertions.assertNull(unitFound);
        Mockito.verify(unitMapper, Mockito.times(0)).toVO(unitTest);
    }

    @Test
    public void whenFindByIdThenReturnUnit(){
        Long id = 1L;
        Unit unitTest = Unit.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();
        CompanyVO companyVO = new CompanyVO(unitTest.getId(), unitTest.getNome(), unitTest.getCnpj(), unitTest.getLogradouro(), unitTest.getBairro(), unitTest.getMunicipio(), unitTest.getUf());


        Mockito.when(unitRepository.findById(id)).thenReturn(Optional.of(unitTest));
        Mockito.when(unitMapper.toVO(unitTest)).thenReturn(companyVO);

        CompanyVO unitFound = unitService.findById(id);

        Assertions.assertNotNull(unitFound);
        Mockito.verify(unitRepository, Mockito.times(1)).findById(id);
        Assertions.assertEquals(unitTest.getCnpj(), unitFound.cpnj());
    }

    @Test
    public void whenFindByWithInvalidIdThenReturnNull(){
        Long id = 9108189L;
        Unit unitTest = Unit.builder()
                .nome("Test")
                .cnpj("00.000.000/0001-99")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();

        Mockito.when(unitRepository.findById(id)).thenReturn(Optional.empty());

        CompanyVO unitFound = unitService.findById(id);

        Assertions.assertNull(unitFound);
        Mockito.verify(unitMapper, Mockito.times(0)).toVO(unitTest);
    }

    @Test
    public void whenDeleteThenTrue(){
        Long id = 1L;
        Unit unitTest = Unit.builder()
                .nome("Test")
                .cnpj("00.000.000/0000-00")
                .logradouro("Test")
                .bairro("Test")
                .municipio("Test")
                .uf("Test")
                .build();

        Mockito.when(unitRepository.findById(id)).thenReturn(Optional.of(unitTest));

        boolean isDeleted = unitService.delete(id);

        Assertions.assertTrue(isDeleted);
    }

    @Test
    public void whenDeleteThenFalse(){
        Long id = 578908L;

        Mockito.when(unitRepository.findById(id)).thenReturn(Optional.empty());

        boolean isDeleted = unitService.delete(id);

        Assertions.assertFalse(isDeleted);
    }
}
