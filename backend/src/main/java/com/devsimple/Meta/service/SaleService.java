package com.devsimple.Meta.service;

import com.devsimple.Meta.config.ModelMapperConfig;
import com.devsimple.Meta.dto.SaleDTO;
import com.devsimple.Meta.exceptions.RuleOfBusinessException;
import com.devsimple.Meta.model.Sale;
import com.devsimple.Meta.repository.SaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class SaleService {

    private SaleRepository repository;
    private ModelMapperConfig modelMapper;

    public SaleService(SaleRepository repository, ModelMapperConfig modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public static String getUUid() {
        return UUID.randomUUID().toString();
    }

    public Page<Sale> findAll(String minDate, String maxDate, Pageable pageable) {
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate min = minDate.equals("") ? today.minusYears(1) : LocalDate.parse(minDate);
        LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);
        return repository.findSales(min, max, pageable);
    }

    @Transactional
    public Sale saveSale(SaleDTO dto) {
        if (dto.getDeals() > dto.getVisited()){
            throw new RuleOfBusinessException("Erro! Visitas não pode ser maior que positivados.");
        }
        Sale sale = modelMapper.toSaleCreate(dto);
        sale.setId(getUUid());
        return repository.save(sale);
    }
}
