package com.gtbfd.genmap.controller;

import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.service.UnitService;
import com.gtbfd.genmap.vo.CompanyVO;
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
    public ResponseEntity<?> create(@RequestBody CompanyDTO companyDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new unit", className);
        CompanyVO unitCreated = unitService.create(companyDTO);

        if (Objects.nonNull(unitCreated)){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Unit created successfuly", className);
            return ResponseEntity.status(HttpStatus.CREATED).body(unitCreated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a  new user", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> findByCnpj(@PathVariable String cnpj){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a Unit by CNPJ", className);
        CompanyVO unitFound = unitService.findByCnpj(cnpj);

        if (Objects.nonNull(unitFound)){
            LOGGER.info("[DEBUG]: Message = {}, Response = {}, Class = {}", "Unit found successfuly", unitFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(unitFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a Unit", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{cnpj}")
    public ResponseEntity<?> deleteByCnpj(@PathVariable String cnpj){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to delete a unit", className);
        boolean isDeleted = unitService.deleteByCnpj(cnpj);

        if (isDeleted) {
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Unit deleted successfuly", className);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete a Unit", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
