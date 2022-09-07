package com.devsimple.Meta.repository;

import com.devsimple.Meta.model.Sale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SaleRepositoryTest {

    @Autowired
    private SaleRepository repository;

    @Test
    void save_PersistSale_WhenSuccessful() {
        Sale sale = createSale();

        Sale saleSaved = repository.save(sale);

        assertThat(saleSaved).isNotNull();
        assertThat(saleSaved.getId()).isNotNull();
        assertThat(saleSaved.getSellerName()).isEqualTo(sale.getSellerName());
        assertThat(saleSaved.getVisited()).isEqualTo(sale.getVisited());
        assertThat(saleSaved.getDeals()).isEqualTo(sale.getDeals());
        assertThat(saleSaved.getDate()).isEqualTo(sale.getDate());
        assertThat(saleSaved.getAmount()).isEqualTo(sale.getAmount());
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