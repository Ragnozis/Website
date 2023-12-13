package com.Bookshop.models;

import jakarta.persistence.*;


@Entity
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        private long idC;

    @ManyToOne
    private PortfolioBooks books;

    public PortfolioBooks GetBooks() {
        return books;
    }

    public void setBooks(PortfolioBooks books) {
        this.books = books;
    }

    private String surname, name, patronymic, mobile, email;

        public long getIdC() {
            return idC;
        }

        public void setIdC(long idC) {
            this.idC = idC;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Clients() {
        }
        public Clients(String surname, String name, String patronymic, String mobile, String email, PortfolioBooks books) {
            this.surname = surname;
            this.name = name;
            this.patronymic = patronymic;
            this.mobile = mobile;
            this.email = email;
            this.books = books;
        }

}
