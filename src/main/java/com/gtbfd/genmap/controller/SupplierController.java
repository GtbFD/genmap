package com.gtbfd.genmap.controller;

import com.gtbfd.genmap.dto.CompanyDTO;
import com.gtbfd.genmap.service.SupplierService;
import com.gtbfd.genmap.vo.CompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    private final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    private final String className = SupplierController.class.getName();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CompanyDTO companyDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new Supplier", className);
        CompanyVO supplierCreated = supplierService.create(companyDTO);
        if (Objects.nonNull(supplierCreated)) {
            LOGGER.info("[DEBUG]: Message = {}, Response = {}, Class = {}", "Supplier created successfuly", supplierCreated, className);
            return ResponseEntity.status(HttpStatus.OK).body(supplierCreated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new Supplier", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> findByCnpj(@PathVariable String cnpj){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a Supplier by CNPJ", className);
        CompanyVO supplierFound = supplierService.findByCnpj(cnpj);
        if (Objects.nonNull(supplierFound)){
            LOGGER.info("[DEBUG]: Message = {}, Response = {}, Class = {}", "Supplier found successfuly", supplierFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(supplierFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to found a Supplier", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/search/name/{nome}")
    public ResponseEntity<?> findByNome(@PathVariable String nome){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find supplier by name", className);
        List<CompanyVO> suppliers = supplierService.findByNome(nome);

        if (suppliers.isEmpty()){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find suppliers by name", className);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("[DEBUG]: Message = {}, Suppliers = {}, Class = {}", "Suppliers found by name", suppliers, className);
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }

    @PatchMapping("/{cnpj}")
    public ResponseEntity<?> delete(@PathVariable String cnpj){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to delete a Supplier", className);
        boolean isDeleted = supplierService.deleteByCnpj(cnpj);

        if (isDeleted) {
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Supplier deleted successfuly", className);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
        }

        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete a Supplier", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
