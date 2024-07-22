package com.gtbfd.genmap.service;

import com.gtbfd.genmap.controller.ItemController;
import com.gtbfd.genmap.domain.Item;
import com.gtbfd.genmap.dto.ItemDTO;
import com.gtbfd.genmap.mapper.ItemMapper;
import com.gtbfd.genmap.repository.ItemRepository;
import com.gtbfd.genmap.vo.ItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    private final String className = ItemService.class.getName();

    public ItemVO create(ItemDTO itemDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new Item", className);
        if (Objects.nonNull(itemDTO)){
            Item item = itemMapper.toMap(itemDTO);
            Item itemCreated = itemRepository.save(item);

            ItemVO itemResponse = itemMapper.toVO(itemCreated);
            LOGGER.info("[DEBUG]: Message = {}, Response = {}, Class = {}", "Item created successfuly", itemResponse, className);
            return itemMapper.toVO(itemCreated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new Item", className);
        return null;
    }

    public ItemVO findByCode(int code){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find an Item by code", className);
        Optional<Item> itemFound = itemRepository.findByCode(code);

        if (itemFound.isPresent() && !itemFound.get().isDeleted()){
            Item itemMapped = itemFound.get();
            ItemVO itemView = itemMapper.toVO(itemMapped);
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Item found by code", itemMapped, className);

            return itemView;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find an Item by code", className);
        return null;
    }

    public ItemVO patchByCode(ItemDTO itemDTO, int code) {
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch an Item by code", className);
        Optional<Item> itemFound = itemRepository.findByCode(code);

        if (itemFound.isPresent()){
            Item itemMapped = itemFound.get();
            itemMapped.setDescription(itemDTO.description());
            itemMapped.setPainelPreco(itemDTO.painelPreco());
            itemMapped.setPrecoReferencia(itemDTO.precoReferencia());
            itemMapped.setPrecoBps(itemDTO.precoBps());
            itemMapped.setCode(code);
            itemMapped.setUnit(itemDTO.unit());

            Item itemPatched = itemRepository.save(itemMapped);
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Item patched by code", itemPatched, className);

            return itemMapper.toVO(itemPatched);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to patch an Item by code", className);
        return null;
    }

    public boolean deleteByCode(int code){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to delete an Item by code", className);
        Optional<Item> itemFound = itemRepository.findByCode(code);

        if (itemFound.isPresent() && !itemFound.get().isDeleted()){
            Item itemMapped = itemFound.get();
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Item found by code", itemMapped, className);

            itemMapped.setDeleted(true);

            Item itemDeleted = itemRepository.save(itemMapped);
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Item deleted sucessfuly", itemDeleted, className);

            return true;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to delete an Item by code", className);
        return false;
    }
}
