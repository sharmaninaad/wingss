package com.example.android.wingss.Activities.JavaClasses;

/**
 * Created by Ninaad on 6/11/2018.
 */

public class Book {
    private int book_id;
    private String book_name;
    private int years;
    private float amount;
    private boolean isForRent;
    private boolean isForSale;
    private boolean isForDonation;
    private User owner;
    private boolean isAvailable;

    public Book(int book_id, String book_name, int years, float amount, boolean isForRent, boolean isForSale, boolean isForDonation, User owner, boolean isAvailable) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.years = years;
        this.amount = amount;
        this.isForRent = isForRent;
        this.isForSale = isForSale;
        this.isForDonation = isForDonation;
        this.owner = owner;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return book_id == book.book_id;

    }

    @Override
    public int hashCode() {
        return book_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public int getYears() {
        return years;
    }

    public float getAmount() {
        return amount;
    }

    public boolean isForRent() {
        return isForRent;
    }

    public boolean isForSale() {
        return isForSale;
    }

    public boolean isForDonation() {
        return isForDonation;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setYears(int years) {

        this.years = years;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setForRent(boolean forRent) {
        isForRent = forRent;
    }

    public void setForSale(boolean forSale) {
        isForSale = forSale;
    }

    public void setForDonation(boolean forDonation) {
        isForDonation = forDonation;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
