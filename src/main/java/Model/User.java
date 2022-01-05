package Model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.CATALOGUE_JSON;
import static Constants.BasicConstants.USERS_JSON;
import static Controller.CatalogueController.catalogue;
import static Controller.FileController.updateFiles;
import static Controller.UserController.*;

public class User {
    private String username;
    private String password;
    private LinkedHashMap<String, Article> favorites;

    public void listItem(String nameOfArticle, Article article) throws IOException {
        catalogue.put(nameOfArticle, article);
        updateFiles(CATALOGUE_JSON, catalogue);
    }

    public void buyItem(String nameOfArticle, Article article) throws IOException {
        catalogue.remove(nameOfArticle, article);
        updateFiles(CATALOGUE_JSON, catalogue);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.favorites = new LinkedHashMap<>();
    }

    public User(String username, String password, LinkedHashMap<String, Article> favorites) {
            this.username = username;
            this.password = password;
            this.favorites = favorites;

    }

    public void addToFavourites(Article article) throws IOException {
        this.favorites.put(article.getName(), article);
        updateFiles(USERS_JSON, users);
    }

    public void removeFromFavourites(Article article) throws IOException {
        this.favorites.remove(article.getName());
        updateFiles(USERS_JSON, users);
    }

    public void showFavourites() {
        for (Map.Entry<String, Article> article : this.favorites.entrySet()) {
            System.out.printf("%s | %.2f BGN | %s %n",
                    article.getKey(), article.getValue().getPrice(), article.getValue().getCategory());
        }
    }

    public LinkedHashMap<String, Article> getFavorites() {
        return this.favorites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IOException {
        this.username = username;
        updateFiles(USERS_JSON, users);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IOException {
        this.password = password;
        updateFiles(USERS_JSON, users);

    }

}
