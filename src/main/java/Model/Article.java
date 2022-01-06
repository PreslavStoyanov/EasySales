package Model;

import java.sql.Date;
import java.time.LocalDate;

public class Article {
    private String name;
    private double price;
    private final String category;
    private final String ownerName;
    private final Date date;
    private boolean active;
    private Date deactivationDate;

    public Article(String name, double price, String category, String ownerName) {
        this(name, price, category, ownerName, Date.valueOf(LocalDate.now()), true);
    }

    public Article(String name, double price, String category, String ownerName, Date date, boolean status) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.ownerName = ownerName;
        this.date = date;
        this.active = status;
    }

    public Date getDeactivationDate() {
        return this.deactivationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDeactivationDate() {
        this.deactivationDate = Date.valueOf(LocalDate.now());
    }

    public String getActivateMessage() {
        String message;
        if (this.active) {
            message = "Active";
        } else {
            message = "Inactive";
        }
        return message;
    }
}
