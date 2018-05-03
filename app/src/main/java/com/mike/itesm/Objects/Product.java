package com.mike.itesm.Objects;

/**
 * Created by mike on 03/05/18.
 */

public class Product {
    private Integer product_id;
    private Integer category_id;
    private String name;
    private String photo;
    private String video;
    private Double price;
    private String color;
    private String age;

    public Product(){

    }

    public Product(Integer product_id, Integer category_id, String name, String photo, String video, Double price, String color, String age) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.name = name;
        this.photo = photo;
        this.video = video;
        this.price = price;
        this.color = color;
        this.age = age;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}