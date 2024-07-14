package com.gtbfd.genmap.util;

import org.springframework.stereotype.Component;

@Component
public class CnpjFormatter {

    public String formatCNPJ(String cnpj){
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "."
                + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
    }

    public String deformatCNPJ(String cnpj){
        return cnpj.replaceAll("[^0-9]", "").replaceAll("\\.", "")
                .replaceAll("/", "")
                .replaceAll("-", "");
    }
}
