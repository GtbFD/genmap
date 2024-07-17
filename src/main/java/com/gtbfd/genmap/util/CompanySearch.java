package com.gtbfd.genmap.util;

import com.gtbfd.genmap.domain.Company;
import com.gtbfd.genmap.domain.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class CompanySearch {

    @Autowired
    private CnpjFormatter cnpjFormatter;

    public Company searchCompanyByCnpjOnInternet(String cnpj){

        String cnpjFormatted = cnpjFormatter.deformatCNPJ(cnpj);

        String url = "https://receitaws.com.br/v1/cnpj/" + cnpjFormatted;

        RestTemplate restTemplate = new RestTemplate();
        Unit unitFound = restTemplate.getForEntity(url, Unit.class).getBody();

        if (Objects.nonNull(unitFound)){
            return unitFound;
        }
        return null;
    }
}
