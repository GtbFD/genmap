package com.gtbfd.genmap.controller;

import com.gtbfd.genmap.dto.UnitDTO;
import com.gtbfd.genmap.service.UnitService;
import com.gtbfd.genmap.vo.UnitVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitController.class);

    private static final String className = UnitController.class.getName();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UnitDTO unitDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new unit", className);
        UnitVO unitCreated = unitService.create(unitDTO);

        if (Objects.nonNull(unitCreated)){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Unit created successfuly", className);
            return ResponseEntity.status(HttpStatus.CREATED).body(unitCreated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a  new user", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
}
