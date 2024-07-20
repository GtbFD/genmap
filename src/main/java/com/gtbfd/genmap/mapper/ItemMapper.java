package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.Item;
import com.gtbfd.genmap.dto.ItemDTO;
import com.gtbfd.genmap.vo.ItemVO;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toMap(ItemDTO itemDTO) {
        return Item.builder()
                .description(itemDTO.description())
                .painelPreco(itemDTO.painelPreco())
                .precoReferencia(itemDTO.precoReferencia())
                .precoBps(itemDTO.precoBps())
                .code(itemDTO.code())
                .unit(itemDTO.unit())
                .build();
    }

    public ItemDTO toDTO(Item item){
        return new ItemDTO(
                item.getDescription(),
                item.getPainelPreco(),
                item.getPrecoReferencia(),
                item.getPrecoBps(),
                item.getCode(),
                item.getUnit()
        );
    }

    public ItemVO toVO(Item item){
        return new ItemVO(
                item.getId(),
                item.getDescription(),
                item.getPainelPreco(),
                item.getPrecoReferencia(),
                item.getPrecoBps(),
                item.getCode(),
                item.getUnit()
        );
    }
}
