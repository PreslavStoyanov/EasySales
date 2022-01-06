package Model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.USERS_JSON;
import static Controller.UserController.users;
import static Utilities.FileHandler.updateFiles;

public class User {
    private String username;
    private String password;
    private final LinkedHashMap<String, Article> favorites;

    public User(String username, String password) {
        this(username, password, new LinkedHashMap<>());
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
