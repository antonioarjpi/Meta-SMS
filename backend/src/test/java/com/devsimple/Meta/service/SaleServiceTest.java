package com.devsimple.Meta.service;

import com.devsimple.Meta.dto.SaleDTO;
import com.devsimple.Meta.model.Sale;
import com.devsimple.Meta.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("SaleService TEST")
class SaleServiceTest {

    @SpyBean
    SaleService service;
    @MockBean
    SaleRepository repository;

    @BeforeEach
    void setUp() {
        PageImpl<Sale> salePage = new PageImpl<>(List.of(createSale()));

        BDDMockito.when(repository.findSales(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(salePage);
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createSale()));
        BDDMockito.when(repository.save(ArgumentMatchers.any(Sale.class)))
                .thenReturn(createSale());
    }

    @Test
    @DisplayName("save Should Save Sale When Successful")
    void save_SaveSale_WhenSuccessful() {
        SaleDTO dto = createSaleDTO();

        Sale saleSave = service.saveSale(dto);

        assertThat(saleSave.getId()).isEqualTo(dto.getId());
        assertThat(saleSave.getSellerName()).isEqualTo(dto.getSellerName());
        assertThat(saleSave.getDeals()).isEqualTo(dto.getDeals());
        assertThat(saleSave.getVisited()).isEqualTo(dto.getVisited());
        assertThat(saleSave.getDate()).isEqualTo(dto.getDate());
        assertThat(saleSave.getAmount()).isEqualTo(dto.getAmount());
    }

    @Test
    @DisplayName("listAll Return List Pageable of Sales When Successful")
    void listAll_ReturnListPageableOfComputers_WHenSuccessful() {
        String expected = createSale().getSellerName();
        Page<Sale> page = service.findAll("2022-09-05", "2022-09-06", PageRequest.of(1, 1));

        assertThat(page).isNotNull();
        assertThat(page.toList()).isNotEmpty().hasSize(1);
        assertThat(page.toList().get(0).getSellerName()).isEqualTo(expected);
    }

    private SaleDTO createSaleDTO() {
        SaleDTO dto = new SaleDTO();
        dto.setId("1234-4567-1234-1234");
        dto.setSellerName("Antônio");
        dto.setVisited(20);
        dto.setDeals(10);
        dto.setAmount(BigDecimal.valueOf(200.00));
        dto.setDate(LocalDate.of(2022, 9, 7));
        return dto;
    }

    private Sale createSale() {
        Sale sale = new Sale();
        sale.setId("1234-4567-1234-1234");
        sale.setSellerName("Antônio");
        sale.setVisited(20);
        sale.setDeals(10);
        sale.setAmount(BigDecimal.valueOf(200.00));
        sale.setDate(LocalDate.of(2022, 9, 7));
        return sale;
    }
}