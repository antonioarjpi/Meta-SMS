package com.devsimple.Meta.config;

import com.devsimple.Meta.dto.SaleDTO;
import com.devsimple.Meta.model.Sale;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public Sale toSaleCreate(SaleDTO dto){
        return MODEL_MAPPER.map(dto, Sale.class);
    }

}
