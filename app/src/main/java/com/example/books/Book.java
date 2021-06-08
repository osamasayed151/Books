package com.example.books;

public class Book {
    private int id;
    private String name;
    private String author;
    private String department;
    private String language;
    private String publisher;
    private  int pages;
    private String image;
    private float rating;

    public Book(int id, String name, String author, String department, String language, String publisher, int pages,String image,float rating) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.department = department;
        this.language = language;
        this.publisher = publisher;
        this.pages = pages;
        this.image = image;
        this.rating = rating;

    }

    public Book(String name, String author, String department, String language, String publisher, int pages,String image,float rating) {
        this.name = name;
        this.author = author;
        this.department = department;
        this.language = language;
        this.publisher = publisher;
        this.pages = pages;
        this.image = image;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
