package App;

import java.io.*;
import java.util.*;

import Model.*;

import static Controller.AdministratorController.*;
import static Controller.UserController.*;
import static Controller.FileController.*;
import static Controller.CatalogueController.*;
import static Model.Administrator.showCategories;

public class View {
    public static void main(String[] args) throws IOException, InvalidPasswordException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            ReadFiles();
            showMenu();
            switch (sc.nextInt()) {
                case 1 -> Register(sc);

                case 2 -> { //'Log In'
                    System.out.print("Insert username: ");
                    String username = sc.next();
                    System.out.print("Insert password: ");
                    String password = sc.next();
                    if (users.containsKey(username) && checkUserPassword(password, users.get(username))) {
                        System.out.printf("Welcome %s! %n", username);
                        User user = users.get(username);
                        LinkedHashMap<String, Article> favorites = user.getFavorites();

                        boolean logInMenuWhile = true;
                        while (logInMenuWhile) {
                            LinkedHashMap<String, Article> activeCatalogue = getActiveArticles();
                            showLoginMenu();
                            switch (sc.nextInt()) {
                                case 1 -> {
                                    showArticlesFrom(activeCatalogue);
                                    boolean articleMenuWhile = true;
                                    while (articleMenuWhile) {
                                        showArticlesMenu();
                                        switch (sc.nextInt()) {
                                            case 1 -> filterByDate(sc, activeCatalogue);
                                             //to filter them by date
                                            case 2 -> {
                                                showArticlesFrom(activeCatalogue);
                                                System.out.print("Name of article: ");
                                                String nameOfArticle = sc.next();
                                                if (containsArticle(nameOfArticle, activeCatalogue)) {
                                                    addIfNotInFavourites(user, favorites, nameOfArticle);
                                                    System.out.println("Added successful!");
                                                } else {
                                                    System.out.println("No such article!");
                                                }
                                            } //add article in favourite
                                            case 3 -> {
                                                showArticlesFrom(activeCatalogue);
                                                System.out.print("Name of article: ");
                                                String nameOfArticle = sc.next();
                                                if (containsArticle(nameOfArticle, activeCatalogue)) {
                                                    user.buyItem(nameOfArticle, activeCatalogue.get(nameOfArticle));
                                                    System.out.println("Purchase successful!");
                                                } else {
                                                    System.out.println("No such article!");
                                                }
                                            } //buy an Article
                                            case 4 -> {
                                                System.out.print("Name of the article to sell: ");
                                                String nameOfArticle = sc.next();
                                                if (!catalogue.containsKey(nameOfArticle)) {
                                                    System.out.print("Price: ");
                                                    double price = sc.nextDouble();
                                                    showCategories();
                                                    System.out.print("Category: ");
                                                    String category = sc.next();
                                                    while (!categories.containsValue(category)) {
                                                        System.out.println("No such category! Chose on from below!");
                                                        showCategories();
                                                        System.out.print("Category: ");
                                                        category = sc.next();
                                                    }
                                                    user.listItem(nameOfArticle, new Article(nameOfArticle, price, category, username));
                                                    System.out.println("Your article has been released for sell successfully!");
                                                } else {
                                                    System.out.println("This name is taken");
                                                }
                                            } //sell an Article
                                            case 0 -> articleMenuWhile = false;
                                        }
                                    }
                                } //all Articles
                                case 2 -> {
                                    boolean accountMenuWhile = true;
                                    while (accountMenuWhile) {
                                        System.out.println("To see your sales press '1'.");
                                        System.out.println("To see your favourites press '2'.");
                                        System.out.println("To change your name press '3'.");
                                        System.out.println("To change your password press '4'");
                                        System.out.println("To delete your account press '5'");
                                        System.out.println("To go back press '0'");
                                        LinkedHashMap<String, Article> ownArticles = getArticlesByUser(username);
                                        switch (sc.nextInt()) {
                                            case 1 -> {
                                                if (ownArticles.isEmpty()) {
                                                    System.out.println("No articles!");
                                                    break;
                                                }
                                                showArticlesByUser(username);
                                                boolean seeArticlesByUserMenu = true;
                                                while (seeArticlesByUserMenu) {
                                                    System.out.println("To change the price of an article press '1'");
                                                    System.out.println("To change the name of an article press '2'");
                                                    System.out.println("To deactivate an article press '3'");
                                                    System.out.println("To delete an article press '4'");
                                                    System.out.println("To go back press '0'");
                                                    readArticles();
                                                    activeCatalogue = getActiveArticles();
                                                    ownArticles = getArticlesByUser(username);
                                                    switch (sc.nextInt()) {
                                                        case 1 -> {
                                                            System.out.print("Name of article: ");
                                                            String nameOfArticle = sc.next();
                                                            if (containsArticle(nameOfArticle, ownArticles)) {
                                                                System.out.print("New price: ");
                                                                double newPrice = sc.nextDouble();
                                                                catalogue.get(nameOfArticle).setPrice(newPrice);
                                                                System.out.println("Price changed!");
                                                            } else {
                                                                System.out.println("No such article!");
                                                            }
                                                        } //change article's price
                                                        case 2 -> {
                                                            System.out.print("Name of article: ");
                                                            String nameOfArticle = sc.next();
                                                            if (containsArticle(nameOfArticle, ownArticles)) {
                                                                System.out.print("New name: ");
                                                                String newName = sc.next();
                                                                if (!catalogue.containsKey(nameOfArticle)) {
                                                                    catalogue.get(nameOfArticle).setName(newName);
                                                                    setArticleKey(newName, nameOfArticle);
                                                                    System.out.println("Name changed!");
                                                                } else {
                                                                    System.out.println("This name is taken");
                                                                }
                                                            }
                                                        } //change article's name
                                                        case 3 -> {
                                                            System.out.print("Name of article: ");
                                                            String nameOfArticle = sc.next();
                                                            if (containsArticle(nameOfArticle, ownArticles)) {
                                                                catalogue.get(nameOfArticle).deactivate();
                                                                System.out.println("Article deactivated!");
                                                            } else {
                                                                System.out.println("No such article!");
                                                            }
                                                        } //deactivate an article
                                                        case 4 -> {
                                                            System.out.print("Name of article: ");
                                                            String nameOfArticle = sc.next();
                                                            if (containsArticle(nameOfArticle, ownArticles)) {
                                                                deleteArticle(nameOfArticle);
                                                                System.out.println("Article deleted!");
                                                            } else {
                                                                System.out.println("No such article!");
                                                            }
                                                        } //delete an article
                                                        case 0 -> seeArticlesByUserMenu = false;
                                                    }
                                                }
                                            } //see your sales
                                            case 2 -> {
                                                user.showFavourites();
                                                boolean showFavouritesMenuWhile = true;
                                                while (showFavouritesMenuWhile) {
                                                    System.out.println("To remove article from favourite press '1'");
                                                    System.out.println("To go back press '0'");
                                                    switch (sc.nextInt()) {
                                                        case 1 -> {
                                                            if (favorites.isEmpty()) {
                                                                System.out.println("Your favourite list is empty!");
                                                                break;
                                                            }
                                                            System.out.print("Name of article: ");
                                                            String nameOfArticle = sc.next();
                                                            if (containsArticle(nameOfArticle, favorites)) {
                                                                user.removeFromFavourites(favorites.get(nameOfArticle));
                                                                System.out.println("Item removed!");
                                                            } else {
                                                                System.out.println("No such article!");
                                                            }
                                                        } //remove article from favourite
                                                        case 0 -> showFavouritesMenuWhile = false;
                                                    }
                                                }
                                            } //see your favourites
                                            case 3 -> {
                                                System.out.print("Type your new username: ");
                                                String newUsername = sc.next();
                                                if (!containsUsername(newUsername)) {
                                                    user.setUsername(username);
                                                    setUserKey(newUsername, username);
                                                    System.out.println("Your name was changed!");
                                                    username = newUsername;
                                                } else {
                                                    System.out.println("Username taken!");
                                                }
                                            } //change your name
                                            case 4 -> {
                                                System.out.print("Type your old password: ");
                                                String oldPassword = sc.next();
                                                if (checkUserPassword(oldPassword, user)) {
                                                    System.out.print("Type your new password: ");
                                                    password = sc.next();
                                                    user.setPassword(password);
                                                    System.out.println("Your password was changed!");
                                                } else {
                                                    System.out.println("Wrong password!");
                                                }
                                            } //change your password
                                            case 5 -> {
                                                System.out.println("Are you sure you want to delete your account? Yes/No");
                                                if (sc.next().equalsIgnoreCase("Yes")) {
                                                    deleteUser(username);
                                                }
                                                System.exit(0);
                                            } //delete your account
                                            case 0 -> accountMenuWhile = false;
                                        }
                                    }
                                } //see your Account
                                case 0 -> logInMenuWhile = false;
                            }
                        }
                    } else if (administrators.containsKey(username) && checkAdministratorPassword(password, administrators.get(username))) {
                        System.out.printf("Welcome %s! %n", username);
                        Administrator administrator = administrators.get(username);

                        boolean logInMenuWhile = true;
                        while (logInMenuWhile) {
                            LinkedHashMap<String, Article> activeCatalogue = getActiveArticles();
                            LinkedHashMap<String, Article> inactiveCatalogue = getInactiveArticles();
                            System.out.println("To see all active Articles press '1'");
                            System.out.println("To see all inactive Articles press '2'");
                            System.out.println("For categories press '3'");
                            System.out.println("For accounts press '4'");
                            System.out.println("To log out press '0'.");
                            switch (sc.nextInt()) {
                                case 1 -> {
                                    showArticlesFrom(activeCatalogue);
                                    boolean activeCatalogueMenu = true;
                                    while (activeCatalogueMenu) {
                                        System.out.println("To filter them by date press '1'");
                                        System.out.println("To go back press '0'");
                                        switch (sc.nextInt()) {
                                            case 1 -> filterByDate(sc, activeCatalogue);
                                            case 0 -> activeCatalogueMenu = false;
                                        }
                                    }
                                } // see all active Articles
                                case 2 -> {
                                    showArticlesFrom(inactiveCatalogue);
                                    boolean inactiveCatalogueMenu = true;
                                    while (inactiveCatalogueMenu) {
                                        System.out.println("To filter them by deactivation date press '1'");
                                        System.out.println("To go back press '0'");
                                        switch (sc.nextInt()) {
                                            case 1 -> filterByDeactivationDate(sc, inactiveCatalogue);
                                            case 0 -> inactiveCatalogueMenu = false;
                                        }
                                    }
                                } // see all inactive Articles
                                case 3 -> {
                                    showCategories();
                                    boolean categoryMenuWhile = true;
                                    while (categoryMenuWhile) {
                                        System.out.println("To add category press '1'");
                                        System.out.println("To change category press '2'");
                                        System.out.println("To delete category press '3'");
                                        System.out.println("To go back press '0'");
                                        switch (sc.nextInt()) {
                                            case 1 -> {
                                                System.out.print("Name of category: ");
                                                String nameOfCategory = sc.next();
                                                if (!administrator.containsCategory(nameOfCategory)) {
                                                    administrator.addCategory(nameOfCategory);
                                                    System.out.println("Category added!");
                                                } else {
                                                    System.out.println("There is such category already");
                                                }
                                            } // add category
                                            case 2 -> {
                                                System.out.print("Name of category: ");
                                                String nameOfCategory = sc.next();
                                                if (administrator.containsCategory(nameOfCategory)) {
                                                    System.out.print("New name of category: ");
                                                    String newNameOfCategory = sc.next();
                                                    if (!administrator.containsCategory(newNameOfCategory)) {
                                                        administrator.setCategoryName(nameOfCategory, newNameOfCategory);
                                                        System.out.println("Category changed!");
                                                    } else {
                                                        System.out.println("This name is taken");
                                                    }
                                                } else {
                                                    System.out.println("No such category");
                                                }
                                            } // change category
                                            case 3 -> {
                                                System.out.print("Name of category: ");
                                                String nameOfCategory = sc.next();
                                                if (administrator.containsCategory(nameOfCategory)) {
                                                    administrator.removeCategory(nameOfCategory);
                                                    System.out.println("Category deleted!");
                                                } else {
                                                    System.out.println("No such category");
                                                }
                                            } // delete category
                                            case 0 -> categoryMenuWhile = false;
                                        }
                                    }
                                } // categories
                                case 4 -> {
                                    boolean accountsMenuWhile = true;
                                    while (accountsMenuWhile) {
                                        System.out.println("To change your name press '1'");
                                        System.out.println("To change your password press '2'");
                                        System.out.println("To add User as Administrator press '3'");
                                        System.out.println("To remove Administrator press '4'");
                                        System.out.println("To delete Account press '5'");
                                        switch (sc.nextInt()) {
                                            case 1 -> {
                                                System.out.print("Type your new username: ");
                                                String newUsername = sc.next();
                                                if (!containsUsername(newUsername)) {
                                                    administrator.setUsername(username);
                                                    setAdministratorKey(newUsername, username);
                                                    System.out.println("Your name was changed!");
                                                    username = newUsername;
                                                } else {
                                                    System.out.println("Username taken!");
                                                }
                                            } // change your name
                                            case 2 -> {
                                                System.out.print("Type your old password: ");
                                                String oldPassword = sc.next();
                                                if (checkAdministratorPassword(oldPassword, administrator)) {
                                                    System.out.print("Type your new password: ");
                                                    password = sc.next();
                                                    administrator.setPassword(password);
                                                    System.out.println("Your password was changed!");
                                                } else {
                                                    System.out.println("Wron password!");
                                                }
                                            } // change your password
                                            case 3 -> {
                                                System.out.println("Name of user to add: ");
                                                String nameOfUser = sc.next();
                                                if (!users.containsKey(nameOfUser)) {
                                                    System.out.println("No such user!");
                                                    nameOfUser = sc.next();
                                                }
                                                addAdministrator(users.get(nameOfUser));
                                                System.out.println("Administrator added!");
                                            } // add User as Administrator
                                            case 4 -> {
                                                System.out.println("Name of administrator to remove: ");
                                                String nameOfAdministrator = sc.next();
                                                if (!administrators.containsKey(nameOfAdministrator)) {
                                                    System.out.println("No such administrator!");
                                                    nameOfAdministrator = sc.next();
                                                }
                                                removeAdministrator(administrators.get(nameOfAdministrator));
                                                System.out.println("Administrator removed!");
                                            } // remove Administrator
                                            case 5 -> {
                                                System.out.println("Are you sure you want to delete your account? Yes/No");
                                                if (sc.next().equalsIgnoreCase("Yes")) {
                                                    deleteAdministrator(username);
                                                }
                                                System.exit(0);
                                            } // delete Account
                                            case 0 -> accountsMenuWhile = false;
                                        }
                                    }
                                } // accounts
                                case 0 -> logInMenuWhile = false;
                            }
                        }
                    } else {
                        System.out.println("Wrong username or password!");
                    }
                }
                case 0 -> System.exit(0);
            }
        }
    }

    private static void filterByDate(Scanner sc, LinkedHashMap<String, Article> catalogue) {
        System.out.print("After (DD:MM:YYYY): ");
        String afterDate = sc.next();
        System.out.print("Before (DD:MM:YYYY): ");
        String beforeDate = sc.next();
        if (isValidDateFormat(afterDate) && isValidDateFormat(beforeDate)) {
            Date after = getDate(afterDate);
            Date before = getDate(beforeDate);
            showArticlesByDateFrom(after, before, catalogue);
        } else {
            System.out.println("This is invalid format! Try again!");
        }
    }

    private static void filterByDeactivationDate(Scanner sc, LinkedHashMap<String, Article> catalogue) {
        System.out.print("After (DD:MM:YYYY): ");
        String afterDate = sc.next();
        System.out.print("Before (DD:MM:YYYY): ");
        String beforeDate = sc.next();
        if (isValidDateFormat(afterDate) && isValidDateFormat(beforeDate)) {
            Date after = getDate(afterDate);
            Date before = getDate(beforeDate);
            showArticlesByDeactivationDateFrom(after, before, catalogue);
        } else {
            System.out.println("This is invalid format! Try again!");
        }
    }

    private static void showArticlesMenu() {
        System.out.println("To filter them by date press '1'");
        System.out.println("To add article in favourites press '2'");
        System.out.println("To buy an Article press '3'.");
        System.out.println("To sell an Article press '4'.");
        System.out.println("To go back press '0'");
    }

    private static void showLoginMenu() {
        System.out.println("To see all Articles press '1'.");
        System.out.println("To see your Account press '2'.");
        System.out.println("To log out press '0'.");
    }

    private static void showMenu() {
        System.out.println("For 'Register' press '1'.");
        System.out.println("For 'Log In' press '2'.");
        System.out.println("To 'Exit' press '0'.");
    }

    private static void Register(Scanner sc) throws IOException, InvalidPasswordException {
        System.out.print("Insert username: ");
        String username = sc.next();
        while (containsUsername(username)) {
            System.out.println("Name taken!");
            System.out.print("Insert username: ");
            username = sc.next();
        }
        System.out.print("Insert password: ");
        String password = sc.next();
        registerUser(username, password);
        System.out.println("Registration successful!");
    }

    private static void ReadFiles() throws IOException {
        readUsers();
        readAdministrators();
        readArticles();
        readCategories();
    }
}