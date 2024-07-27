package com.gtbfd.genmap.dto;

import java.util.List;

public record MapItemDTO (Long mapId, String supplierCnpj, List<Long> items, List<Float> prices, List<Float> quantity) {
}
