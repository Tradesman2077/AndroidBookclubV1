package com.example.compscilibrary;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String subject;

    public Book(String title, String author, String ISBN, String subject) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
