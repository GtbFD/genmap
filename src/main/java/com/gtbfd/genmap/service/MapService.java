package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Map;
import com.gtbfd.genmap.dto.ItemDTO;
import com.gtbfd.genmap.dto.MapDTO;
import com.gtbfd.genmap.mapper.MapMapper;
import com.gtbfd.genmap.repository.MapRepository;
import com.gtbfd.genmap.vo.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapMapper mapMapper;

    private Logger LOGGER = LoggerFactory.getLogger(MapService.class);

    private String className = MapService.class.getName();

    public MapVO create(MapDTO mapDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new Map", className);

        if (Objects.nonNull(mapDTO)){
            Map map = mapMapper.toMap(mapDTO);

            Map mapSaved = mapRepository.save(map);
            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map created sucessfuly", mapSaved,className);

            return mapMapper.toVO(mapSaved);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new Map", className);
        return null;
    }

    public MapVO findById(Long id){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a Map by ID", className);
        Optional<Map> mapFound = mapRepository.findById(id);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()){
            Map mapEntity = mapFound.get();
            MapVO mappedMap = mapMapper.toVO(mapEntity);

            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map found by ID", mappedMap, className);
            return mappedMap;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a Map by ID", className);
        return null;
    }

    public MapVO patch(Long id, MapDTO mapDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch a Map by ID", className);
        Optional<Map> mapFound = mapRepository.findById(id);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()){
            Map mapEntity = mapFound.get();
            mapEntity.setProcessNumber(mapDTO.processNumber());
            mapEntity.setPbDocProcess(mapDTO.pbDocProcess());
            mapEntity.setSector(mapDTO.sector());
            mapEntity.setTopic(mapDTO.topic());

            Map mapPatched = mapRepository.save(mapEntity);
            MapVO mappedMap = mapMapper.toVO(mapPatched);

            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map patched by ID", mappedMap, className);
            return mappedMap;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to patch a Map by ID", className);
        return null;
    }

    public boolean delete(Long id){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch a Map by ID", className);
        Optional<Map> mapFound = mapRepository.findById(id);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()){
            Map mapEntity = mapFound.get();
            mapEntity.setDeleted(true);

            Map mapDeleted = mapRepository.save(mapEntity);
            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map patched by ID", mapDeleted, className);

            return true;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete a Map by ID", className);
        return false;
    }
}
