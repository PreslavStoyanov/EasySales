package Model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Controller.CatalogueController.catalogue;
import static Controller.FileController.updateFiles;
import static Controller.UserController.*;

public class User {
    private String username;
    private String password;
    private LinkedHashMap<String, Article> favorites;

    public void listItem(String nameOfArticle, Article article) throws IOException {
        catalogue.put(nameOfArticle, article);
        updateFiles("Catalogue.json", catalogue);
    }

    public void buyItem(String nameOfArticle, Article article) throws IOException {
        catalogue.remove(nameOfArticle, article);
        updateFiles("Catalogue.json", catalogue);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.favorites = new LinkedHashMap<>();
    }

    public User(String username, String password, LinkedHashMap<String, Article> favorites) throws InvalidPasswordException {
            isValidPassword(password);
            this.username = username;
            this.password = password;
            this.favorites = favorites;

    }

    public void addToFavourites(Article article) throws IOException {
        this.favorites.put(article.getName(), article);
        updateFiles("Users.json", users);
    }

    public void removeFromFavourites(Article article) throws IOException {
        this.favorites.remove(article.getName());
        updateFiles("Users.json", users);
    }

    public void showFavourites() {
        for (Map.Entry<String, Article> article : this.favorites.entrySet()) {
            System.out.printf("%s | %.2f lv. | %s %n",
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
        updateFiles("Users.json", users);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IOException, InvalidPasswordException {
        isValidPassword(password);
        this.password = password;
        updateFiles("Users.json", users);

    }

}
