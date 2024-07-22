package com.gtbfd.genmap.controller;

import com.gtbfd.genmap.dto.ItemDTO;
import com.gtbfd.genmap.service.ItemService;
import com.gtbfd.genmap.vo.ItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private final String className = ItemController.class.getName();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemDTO itemDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new Item", className);
        ItemVO itemCreated = itemService.create(itemDTO);
        if (Objects.nonNull(itemCreated)){
            LOGGER.info("[DEBUG]: Message = {}, Response = {}, Class = {}", "Item created successfuly", itemCreated, className);
            return ResponseEntity.status(HttpStatus.OK).body(itemCreated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new Item", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> findByCode(@PathVariable int code){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find an Item by code", className);
        ItemVO itemFound = itemService.findByCode(code);

        if (Objects.nonNull(itemFound)){
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Item found by code", itemFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(itemFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find an Item by code", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> path(@RequestBody ItemDTO itemDTO, @PathVariable int code){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch an Item by code", className);
        ItemVO itemFound = itemService.patchByCode(itemDTO, code);

        if (Objects.nonNull(itemFound)){
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Item patched by code", itemFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(itemFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to patch an Item by code", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable int code){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to delete an Item by code", className);
        boolean isDeleted = itemService.deleteByCode(code);

        if (isDeleted){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Item deleted sucessfuly", className);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete an Item by code", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isDeleted);
    }
}
