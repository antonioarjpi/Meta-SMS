package com.devsimple.Meta.controller;

import com.devsimple.Meta.dto.SaleDTO;
import com.devsimple.Meta.model.Sale;
import com.devsimple.Meta.service.SaleService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SaleControllerTest {

    @LocalServerPort
    private int randomPort;

    @InjectMocks
    private SaleController controller;

    @Mock
    private SaleService service;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;

        PageImpl<Sale> pageSale = new PageImpl<>(List.of(createSale()));
        BDDMockito.when(service.findAll(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(pageSale);
    }

    @Test
    void findAll_ReturnListOfSalesInsidePageObject_WhenSuccessful() {
        String expectedSellerName = createSale().getSellerName();
        Page<Sale> salesPage = controller.findAll("2022-09-05", "2022-09-06", PageRequest.of(1, 1)).getBody();

        assertThat(salesPage).isNotNull();
        assertThat(salesPage.toList().get(0).getSellerName()).isEqualTo(expectedSellerName);

        RestAssured.given()
                .when()
                .get("/sales")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void notifySMS_SendSMS_WhenSucessful() {
        String idImportSql = "f76acbc2-8f8e-4b81-a17b-e04eae3bdc23";
        RestAssured.given()
                .when()
                .get("/sales/" + idImportSql + "/notification")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenSaveThenSaleIsCreated() {
        BigDecimal price = new BigDecimal("2341.00")
                .setScale(2, RoundingMode.DOWN);

        SaleDTO dto = new SaleDTO();
        dto.setSellerName("Antônio");
        dto.setDeals(20);
        dto.setVisited(21);
        dto.setAmount(price);
        dto.setDate(LocalDate.parse("2022-07-09"));

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .post("/sales")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("sellerName", Matchers.equalTo("Antônio"))
                .body("deals", Matchers.equalTo(20))
                .body("visited", Matchers.equalTo(21))
                .body("amount", Matchers.equalTo(2341.0f))
                .body("date", Matchers.equalTo("2022-07-09"));
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