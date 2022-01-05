package Model;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static Constants.BasicConstants.CATALOGUE_JSON;
import static Controller.CatalogueController.catalogue;
import static Controller.FileController.updateFiles;

public class Article {
    private String name;
    private double price;
    private final String category;
    private String ownerName;
    private Date date;
    private boolean active;
    private Date dateOfDeactivation;

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

    public Date getDateOfDeactivation() {
        return this.dateOfDeactivation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IOException {
        this.name = name;
        updateFiles(CATALOGUE_JSON, catalogue);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws IOException {
        this.price = price;
        updateFiles(CATALOGUE_JSON, catalogue);
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

    public void deactivate() throws IOException {
        this.active = false;
        this.dateOfDeactivation = Date.valueOf(LocalDate.now());
        updateFiles(CATALOGUE_JSON, catalogue);
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
