package com.example.kpopstore.DTO;

public class OrderItemDTO {
    private String orderId;
    private String albumId;
    private int quantity;


    public OrderItemDTO(String orderId, String albumId, int quantity) {
        this.orderId = orderId;
        this.albumId = albumId;
        this.quantity = quantity;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
