package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Map;
import com.gtbfd.genmap.dto.MapDTO;
import com.gtbfd.genmap.vo.MapVO;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    public Map toMap(MapDTO mapDTO) {
        return Map.builder()
                .processNumber(mapDTO.processNumber())
                .pbDocProcess(mapDTO.pbDocProcess())
                .sector(mapDTO.sector())
                .topic(mapDTO.topic())
                .build();
    }

    public MapDTO toDTO(Map map) {
        return new MapDTO(
                map.getProcessNumber(),
                map.getPbDocProcess(),
                map.getSector(),
                map.getTopic()
        );
    }

    public MapVO toVO(Map map) {
        return new MapVO(
                map.getId(),
                map.getProcessNumber(),
                map.getPbDocProcess(),
                map.getSector(),
                map.getTopic(),
                map.getItens()
        );
    }
}
