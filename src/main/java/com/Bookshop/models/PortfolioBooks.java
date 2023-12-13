package com.Bookshop.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PortfolioBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany (mappedBy = "books")
    private List <Clients> clientsList;

    public List<Clients> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Clients> clientsList) {
        this.clientsList = clientsList;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Set<String> images;

    public Set<String> getImages(){
        if (null == images){
            images = new HashSet<>();
        }
        return images;
    }

    public void addImage(String path){
        getImages().add(path);
    }

    private String name, author, description, year, price, genre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public PortfolioBooks() {
    }

    public PortfolioBooks(String name, String author, String description, String year, String price, String genre) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.year = year;
        this.price = price;
        this.genre = genre;
    }



}
