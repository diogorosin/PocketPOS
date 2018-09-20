package br.com.pocketpos.data.room;

import java.util.List;

public class SaleItemModel {

    private SaleVO sale;

    private Integer item;

    private ProductVO product;

    private Double quantity;

    private Double price;

    private Double total;

    private List<SaleItemTicketModel> tickets;

    public SaleVO getSale() {

        return sale;

    }

    public void setSale(SaleVO sale) {

        this.sale = sale;

    }

    public Integer getItem() {

        return item;

    }

    public void setItem(Integer item) {

        this.item = item;

    }

    public ProductVO getProduct() {

        return product;

    }

    public void setProduct(ProductVO product) {

        this.product = product;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

    public List<SaleItemTicketModel> getTickets() {

        return tickets;

    }

    public void setTickets(List<SaleItemTicketModel> tickets) {

        this.tickets = tickets;

    }

}