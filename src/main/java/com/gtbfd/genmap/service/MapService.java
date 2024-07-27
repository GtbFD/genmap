package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Item;
import com.gtbfd.genmap.domain.Map;
import com.gtbfd.genmap.domain.MapItem;
import com.gtbfd.genmap.domain.Supplier;
import com.gtbfd.genmap.dto.ItemDTO;
import com.gtbfd.genmap.dto.MapDTO;
import com.gtbfd.genmap.mapper.MapMapper;
import com.gtbfd.genmap.repository.ItemRepository;
import com.gtbfd.genmap.repository.MapRepository;
import com.gtbfd.genmap.repository.SupplierRepository;
import com.gtbfd.genmap.util.CnpjFormatter;
import com.gtbfd.genmap.vo.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private MapMapper mapMapper;

    private Logger LOGGER = LoggerFactory.getLogger(MapService.class);

    private String className = MapService.class.getName();

    public MapVO create(MapDTO mapDTO) {
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new Map", className);

        if (Objects.nonNull(mapDTO)) {
            Map map = mapMapper.toMap(mapDTO);

            Map mapSaved = mapRepository.save(map);
            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map created sucessfuly", mapSaved, className);

            return mapMapper.toVO(mapSaved);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a new Map", className);
        return null;
    }

    public Map addItemInMap(Long idMap, String cnpj, List<Long> idItens, List<Float> prices, List<Float> quantities) {
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to add Item in Map", className);
        Optional<Map> mapFound = mapRepository.findById(idMap);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()) {
            Map mapEntity = mapFound.get();
            List<Item> itemsFound = itemRepository.findAllById(idItens);
            itemsFound.sort(Comparator.comparing(item -> idItens.indexOf(item.getId())));
            LOGGER.info("[DEBUG]: Message = {}, Items = {}, Class = {}", "Items found", itemsFound, className);
            if (!itemsFound.isEmpty()) {

                List<MapItem> itemsGenerated = prepareItems(mapEntity, itemsFound, prices, cnpj, quantities);

                mapEntity.getItens().addAll(itemsGenerated);

                Map mapSaved = mapRepository.save(mapEntity);
                LOGGER.info("[DEBUG]: Message = {}, Items = {}, Class = {}", "Items added sucessfuly", mapSaved, className);

                return mapSaved;
            }
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible add Items in Map", className);
        return null;
    }

    public List<MapItem> prepareItems(Map map, List<Item> itemsFound, List<Float> prices, String supplierCnpj, List<Float> quantities){
        List<MapItem> itemsCreated = createItemsWithPrices(itemsFound, prices, map);
        LOGGER.info("[DEBUG]: Message = {}, Items = {}, Class = {}", "Items matched with prices", itemsCreated, className);

        List<MapItem> itemsWithSupplier = includeSupplier(itemsCreated, supplierCnpj);

        List<MapItem> itemsWithQuantities = includeQuantities(itemsWithSupplier, quantities);

        LOGGER.info("[DEBUG]: Message = {}, Items = {}, Class = {}", "Generated MapItem", itemsWithQuantities, className);

        return itemsWithQuantities;
    }

    public List<MapItem> createItemsWithPrices(List<Item> itemsFound, List<Float> prices, Map map) {
        List<MapItem> mapItems = new ArrayList<>();

        for (int i = 0; i < itemsFound.size(); i++) {
            MapItem mapItem = MapItem.builder()
                    .item(itemsFound.get(i))
                    .price(prices.get(i))
                    .map(map)
                    .build();
            mapItems.add(mapItem);
        }

        return mapItems;
    }

    public List<MapItem> includeSupplier(List<MapItem> mapItems, String cnpj) {
        cnpj = new CnpjFormatter().deformatCNPJ(cnpj);
        Optional<Supplier> supplierFound = supplierRepository.findByCnpj(cnpj);

        for (int i = 0; i < mapItems.size(); i++){
            mapItems.get(i).setSupplier(supplierFound.orElseThrow());
        }

        return mapItems;
    }

    public List<MapItem> includeQuantities(List<MapItem> mapItems, List<Float> quantities) {
        for (int i = 0; i < mapItems.size(); i++) {
            mapItems.get(i).setQuantity(quantities.get(i));
        }

        return mapItems;
    }

    public MapVO findById(Long id) {
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a Map by ID", className);
        Optional<Map> mapFound = mapRepository.findById(id);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()) {
            Map mapEntity = mapFound.get();
            MapVO mappedMap = mapMapper.toVO(mapEntity);

            LOGGER.info("[DEBUG]: Message = {}, {}, Class = {}", "Map found by ID", mappedMap, className);
            return mappedMap;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a Map by ID", className);
        return null;
    }

    public MapVO patch(Long id, MapDTO mapDTO) {
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch a Map by ID", className);
        Optional<Map> mapFound = mapRepository.findById(id);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()) {
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

    public boolean delete(Long id) {
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch a Map by ID", className);
        Optional<Map> mapFound = mapRepository.findById(id);

        if (mapFound.isPresent() && !mapFound.get().isDeleted()) {
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
