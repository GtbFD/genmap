package com.gtbfd.genmap.controller;

import com.gtbfd.genmap.domain.Item;
import com.gtbfd.genmap.domain.Map;
import com.gtbfd.genmap.dto.MapDTO;
import com.gtbfd.genmap.dto.MapItemDTO;
import com.gtbfd.genmap.service.MapService;
import com.gtbfd.genmap.vo.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService mapService;

    private Logger LOGGER = LoggerFactory.getLogger(MapController.class);

    private String className = MapController.class.getName();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MapDTO mapDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new Map", className);
        MapVO mapCreated = mapService.create(mapDTO);

        if (Objects.nonNull(mapCreated)){
            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map created sucessfuly", mapCreated,className);
            return ResponseEntity.status(HttpStatus.OK).body(mapCreated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new Map", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/add/item")
    public ResponseEntity<?> addItem(@RequestBody MapItemDTO mapItemDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to add Item in Map", className);

        Map map = mapService.addItemInMap(mapItemDTO.mapId(), mapItemDTO.supplierCnpj(),
                mapItemDTO.items(), mapItemDTO.prices(), mapItemDTO.quantity());

        if (Objects.nonNull(map)) {
            LOGGER.info("[DEBUG]: Message = {}, Items = {}, Class = {}", "Items added in Map", map, className);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to add Items in Map", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a Map by ID", className);
        MapVO mapFound = mapService.findById(id);

        if (Objects.nonNull(mapFound)){
            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map found by ID", mapFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(mapFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a Map by ID", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestBody MapDTO mapDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch a Map by ID", className);
        MapVO mapPatched = mapService.patch(id, mapDTO);

        if (Objects.nonNull(mapPatched)){
            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map patched by ID", mapPatched, className);
            return ResponseEntity.status(HttpStatus.OK).body(mapPatched);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to patch a Map by ID", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to delete a Map by ID", className);
        boolean isDeleted = mapService.delete(id);

        if (isDeleted){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Map deleted sucessfuly", className);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete a Map by ID", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isDeleted);
    }
}
