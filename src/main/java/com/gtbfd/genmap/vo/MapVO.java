package com.gtbfd.genmap.vo;

import com.gtbfd.genmap.domain.MapItem;

import java.util.List;

public record MapVO (Long id, String processNumber, String pbDocProcess, String sector, String topic, List<MapItem> itens) {
}
