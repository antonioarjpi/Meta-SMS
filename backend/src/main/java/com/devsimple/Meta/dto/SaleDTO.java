package com.devsimple.Meta.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleDTO {

    private String id;
    @NotBlank(message = "Insira um nome válido!")
    @Size(min = 3, max = 140, message = "Nome precisa ser entre 3 a 140 caracteres!")
    private String sellerName;
    @NotNull(message = "É obrigatório informar a quantidade de visitas")
    @Min(value = 1, message = "Visitados deve ser maior ou igual a 1")
    private Integer visited;
    @NotNull(message = "É obrigatório informar a quantidade de clientes positivados")
    @Min(value = 1, message = "Positivados deve ser maior ou igual a 1")
    private Integer deals;
    @NotNull(message = "É obrigatório informar o valor")
    private BigDecimal amount;
    @NotNull(message = "É obrigatório informar a data de venda")
    private LocalDate date;

    public SaleDTO() {
    }

    public SaleDTO(String id, String sellerName, Integer visited, Integer deals, BigDecimal amount, LocalDate date) {
        this.id = id;
        this.sellerName = sellerName;
        this.visited = visited;
        this.deals = deals;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    public Integer getDeals() {
        return deals;
    }

    public void setDeals(Integer deals) {
        this.deals = deals;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
