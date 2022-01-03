package Controller;

import Model.Article;
import Model.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Constants.BasicConstants.CATALOGUE_JSON;
import static Constants.BasicConstants.USERS_JSON;
import static Controller.FileController.updateFiles;
import static Controller.UserController.users;

public class CatalogueController {
    public static LinkedHashMap<String, Article> catalogue = new LinkedHashMap<>();

    public static boolean isValidDateFormat (String date) {
        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{4}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();

    }

    public static Date getDate(String stringDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd:MM:yyyy").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void showArticlesByDeactivationDateFrom(Date dateAfter, Date dateBefore, LinkedHashMap<String, Article> map) {
        for (Map.Entry<String, Article> article : map.entrySet()) {
            if (article.getValue().getDateOfDeactivation().after(dateAfter) && article.getValue().getDateOfDeactivation().before(dateBefore)) {
                System.out.printf("%s | %.2f BGN | %s | %s %n", article.getValue().getName(), article.getValue().getPrice(),
                        article.getValue().getCategory(), article.getValue().getOwnerName());
            }
        }
    }

    public static void showArticlesByDateFrom(Date dateAfter, Date dateBefore, LinkedHashMap<String, Article> map) {
        for (Map.Entry<String, Article> article : map.entrySet()) {
            if (article.getValue().getDate().after(dateAfter) && article.getValue().getDate().before(dateBefore)) {
                System.out.printf("%s | %.2f BGN | %s | %s | %s %n", article.getValue().getName(), article.getValue().getPrice(),
                        article.getValue().getCategory(), article.getValue().getActivateMessage(), article.getValue().getOwnerName());
            }
        }
    }

    public static LinkedHashMap<String, Article> getActiveArticles() {
        LinkedHashMap<String, Article> mapToReturn = new LinkedHashMap<>();
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (article.getValue().isActive()) {
                mapToReturn.put(article.getKey(), article.getValue());
            }
        }
        return mapToReturn;
    }

    public static LinkedHashMap<String, Article> getInactiveArticles() {
        LinkedHashMap<String, Article> mapToReturn = new LinkedHashMap<>();
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (!article.getValue().isActive()) {
                mapToReturn.put(article.getKey(), article.getValue());
            }
        }
        return mapToReturn;
    }

    public static boolean containsArticle(String nameOfArticle, Map<String, Article> map) {
        return map.containsKey(nameOfArticle);
    }

    public static void addIfNotInFavourites(User user, LinkedHashMap<String, Article> favorites, String nameOfArticle) throws IOException {
        if (favorites.containsKey(nameOfArticle)) {
            System.out.println("This article is already in favourites!");
        } else {
            user.addToFavourites(catalogue.get(nameOfArticle));
            System.out.println("Item added!");
        }
    }

    public static void setArticleKey(String newKey, String oldKey) throws IOException {
        Article article = catalogue.get(oldKey);
        catalogue.remove(oldKey);
        catalogue.put(newKey, new Article(newKey, article.getPrice(),
                article.getCategory(), article.getOwnerName(), article.getDate(), article.isActive()));
        updateFiles(CATALOGUE_JSON, catalogue);
        updateFiles(USERS_JSON, users);
    }

    public static void deleteArticle(String nameOfArticle) throws IOException {
        catalogue.remove(nameOfArticle);
        updateFiles(CATALOGUE_JSON, catalogue);
    }

    public static void showArticlesFrom(Map<String, Article> map) {
        if (map.isEmpty()) {
            System.out.println("No articles!");
            return;
        }
        for (Map.Entry<String, Article> article : map.entrySet()) {
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
        LinkedHashMap<String, Article> mapToReturn = new LinkedHashMap<>();
        for (Map.Entry<String, Article> article : catalogue.entrySet()) {
            if (article.getValue().getOwnerName().equals(userName)) {
                mapToReturn.put(article.getKey(), article.getValue());
            }
        }
        return mapToReturn;
    }
}
