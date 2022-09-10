package com.devsimple.Meta.controller;

import com.devsimple.Meta.dto.SaleDTO;
import com.devsimple.Meta.model.Sale;
import com.devsimple.Meta.service.SaleService;
import com.devsimple.Meta.service.SmsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private SaleService service;
    private SmsService smsService;

    public SaleController(SaleService service, SmsService smsService) {
        this.service = service;
        this.smsService = smsService;
    }

    @GetMapping
    public ResponseEntity<Page<Sale>> findAll(@RequestParam(defaultValue = "") String minDate,
                                              @RequestParam(defaultValue = "") String maxDate,
                                              Pageable pageable) {
        return ResponseEntity.ok(service.findAll(minDate, maxDate, pageable));
    }

    @GetMapping("/{id}/notification")
    public void notifySMS(@PathVariable String id) {
        smsService.sendSms(id);
    }

    @PostMapping
    public ResponseEntity<Sale> saveSale(@RequestBody @Valid SaleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveSale(dto));
    }
}
