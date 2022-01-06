package Controller;

import Model.Article;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.CATALOGUE_JSON;
import static Constants.BasicConstants.USERS_JSON;
import static Utilities.FileHandler.updateFiles;
import static Controller.UserController.users;

public class CatalogueController {
    public static LinkedHashMap<String, Article> catalogue = new LinkedHashMap<>();

    public static void showArticlesFromDeactivationDate(Date dateAfter, Date dateBefore, LinkedHashMap<String, Article> articles) {
        for (Map.Entry<String, Article> article : articles.entrySet()) {
            if (article.getValue().getDateOfDeactivation().after(dateAfter) && article.getValue().getDateOfDeactivation().before(dateBefore)) {
                System.out.printf("%s | %.2f BGN | %s | %s %n", article.getValue().getName(), article.getValue().getPrice(),
                        article.getValue().getCategory(), article.getValue().getOwnerName());
            }
        }
    }

    public static void showArticlesFromDate(Date dateAfter, Date dateBefore, LinkedHashMap<String, Article> articles) {
        for (Map.Entry<String, Article> article : articles.entrySet()) {
            if (article.getValue().getDate().after(dateAfter) && article.getValue().getDate().before(dateBefore)) {
                System.out.printf("%s | %.2f BGN | %s | %s | %s %n", article.getValue().getName(), article.getValue().getPrice(),
                        article.getValue().getCategory(), article.getValue().getActivateMessage(), article.getValue().getOwnerName());
            }
        }
    }

    public static LinkedHashMap<String, Article> getActiveArticles() {
        LinkedHashMap<String, Article> activeArticles = new LinkedHashMap<>();
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (article.getValue().isActive()) {
                activeArticles.put(article.getKey(), article.getValue());
            }
        }
        return activeArticles;
    }

    public static LinkedHashMap<String, Article> getInactiveArticles() {
        LinkedHashMap<String, Article> inactiveArticles = new LinkedHashMap<>();
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (!article.getValue().isActive()) {
                inactiveArticles.put(article.getKey(), article.getValue());
            }
        }
        return inactiveArticles;
    }

    public static boolean containsArticle(String articleName, Map<String, Article> articles) {
        return articles.containsKey(articleName);
    }

    public static void setArticleKey(String newKey, String oldKey) throws IOException {
        Article article = catalogue.get(oldKey);
        catalogue.remove(oldKey);
        catalogue.put(newKey, new Article(newKey, article.getPrice(),
                article.getCategory(), article.getOwnerName(), article.getDate(), article.isActive()));
        updateFiles(CATALOGUE_JSON, catalogue);
        updateFiles(USERS_JSON, users);
    }

    public static void deleteArticle(String articleName) throws IOException {
        catalogue.remove(articleName);
        updateFiles(CATALOGUE_JSON, catalogue);
    }

    public static void addArticle(String articleName, Article article) throws IOException {
        catalogue.put(articleName, article);
        updateFiles(CATALOGUE_JSON, catalogue);
    }

    public static void showArticlesFrom(Map<String, Article> articles) {
        if (articles.isEmpty()) {
            System.out.println("No articles!");
            return;
        }
        for (Map.Entry<String, Article> article : articles.entrySet()) {
            System.out.printf("%s | %.2f BGN | %s | %s | %s %n", article.getValue().getName(), article.getValue().getPrice(),
                    article.getValue().getCategory(), article.getValue().getActivateMessage(), article.getValue().getOwnerName());
        }
        System.out.println();
    }

    public static void showArticlesByUser(String userName, LinkedHashMap<String, Article> ownArticles) {
        if (ownArticles.isEmpty()) {
            System.out.println("No articles!");
        }
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (article.getValue().getOwnerName().equals(userName)) {
                System.out.printf("%s | %.2f BGN | %s | %s %n", article.getValue().getName(), article.getValue().getPrice(),
                        article.getValue().getCategory(), article.getValue().getActivateMessage());
            }
        }
    }

    public static LinkedHashMap<String, Article> getArticlesByUser(String userName) {
        LinkedHashMap<String, Article> userArticles = new LinkedHashMap<>();
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (article.getValue().getOwnerName().equals(userName)) {
                userArticles.put(article.getKey(), article.getValue());
            }
        }
        return userArticles;
    }
}
