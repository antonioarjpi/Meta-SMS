package com.devsimple.Meta.controller;

import com.devsimple.Meta.model.Sale;
import com.devsimple.Meta.service.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Sale>> findAll(@RequestParam(defaultValue = "") String minDate,
                                              @RequestParam(defaultValue = "") String maxDate,
                                              Pageable pageable){
        return ResponseEntity.ok(service.findAll(minDate, maxDate, pageable));
    }
}
