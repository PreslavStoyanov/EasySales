package App;

import Exceptions.InvalidPasswordException;
import Model.Administrator;
import Model.Article;
import Model.User;
import Utilities.DateFormatting;
import Utilities.FileHandler;
import Utilities.PasswordHashing;
import Utilities.PasswordValidator;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static Constants.BasicConstants.*;
import static Controller.AdministratorController.*;
import static Controller.CatalogueController.*;
import static Controller.CategoriesController.*;
import static Controller.UserController.*;

public class View {
    public static void main(String[] args) throws IOException, InvalidPasswordException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            ReadFiles();
            ShowMenu();
            switch (sc.nextInt()) {
                case 1 -> Register(sc);

                case 2 -> Login(sc);

                case 0 -> System.exit(0);
            }
        }
    }

    private static void Login(Scanner sc) throws IOException, InvalidPasswordException {
        System.out.print("Insert username: ");
        String username = sc.next();
        System.out.print("Insert password: ");
        String password = sc.next();
        if (users.containsKey(username) && PasswordValidator.isRightPassword(password, users.get(username).getPassword())) {
            LoginAsUser(sc, username);
        } else if (administrators.containsKey(username) && PasswordValidator.isRightPassword(password, administrators.get(username).getPassword())) {
            LoginAsAdministrator(sc, username);
        } else {
            System.out.println("Wrong username or password!");
        }
    }

    private static void LoginAsAdministrator(Scanner sc, String username) throws IOException, InvalidPasswordException {
        System.out.printf("Welcome %s! %n", username);
        Administrator administrator = administrators.get(username);

        boolean logInMenuWhile = true;
        while (logInMenuWhile) {
            LinkedHashMap<String, Article> activeCatalogue = getActiveArticles();
            LinkedHashMap<String, Article> inactiveCatalogue = getInactiveArticles();
            ShowAdministratorLoginMenu();
            switch (sc.nextInt()) {
                case 1 -> ShowActiveArticles(sc, activeCatalogue);
                case 2 -> ShowInactiveArticles(sc, inactiveCatalogue);
                case 3 -> ShowCategories(sc);
                case 4 -> username = ShowAccounts(sc, username, administrator);
                case 0 -> logInMenuWhile = false;
            }
        }
    }

    private static String ShowAccounts(Scanner sc, String username, Administrator administrator) throws IOException, InvalidPasswordException {
        boolean accountsMenuWhile = true;
        while (accountsMenuWhile) {
            ShowAccountsMenu();
            switch (sc.nextInt()) {
                case 1 -> username = ChangeAdministratorUsername(sc, username);
                case 2 -> ChangeAdministratorPassword(sc, administrator);
                case 3 -> AddUserAsAdministrator(sc);
                case 4 -> RemoveAdministrator(sc);
                case 5 -> DeleteAdministrator(sc, username);
                case 0 -> accountsMenuWhile = false;
            }
        }
        return username;
    }

    private static void ShowCategories(Scanner sc) throws IOException {
        showCategories();
        boolean categoryMenuWhile = true;
        while (categoryMenuWhile) {
            ShowCategoriesMenu();
            switch (sc.nextInt()) {
                case 1 -> AddCategory(sc);
                case 2 -> ChangeCategory(sc);
                case 3 -> DeleteCategory(sc);
                case 0 -> categoryMenuWhile = false;
            }
        }
    }

    private static void LoginAsUser(Scanner sc, String username) throws IOException, InvalidPasswordException {
        System.out.printf("Welcome %s! %n", username);
        User user = users.get(username);
        LinkedHashMap<String, Article> favorites = user.getFavorites();

        boolean logInMenuWhile = true;
        while (logInMenuWhile) {
            ShowUserLoginMenu();
            switch (sc.nextInt()) {
                case 1 -> ShowAllArticles(sc, username, user, favorites);
                case 2 -> username = Account(sc, username, user, favorites);
                case 0 -> logInMenuWhile = false;
            }
        }
    }

    private static void ShowAllArticles(Scanner sc, String username, User user, LinkedHashMap<String, Article> favorites) throws IOException {
        LinkedHashMap<String, Article> activeCatalogue = getActiveArticles();
        showArticlesFrom(activeCatalogue);
        boolean articleMenuWhile = true;
        while (articleMenuWhile) {
            showArticlesMenu();
            switch (sc.nextInt()) {
                case 1 -> FilterByDate(sc, activeCatalogue);
                case 2 -> AddToFavorites(sc, user, favorites, activeCatalogue);
                case 3 -> BuyArticle(sc, activeCatalogue);
                case 4 -> SellArticle(sc, username);
                case 0 -> articleMenuWhile = false;
            }
        }
    }

    private static String Account(Scanner sc, String username, User user, LinkedHashMap<String, Article> favorites) throws IOException, InvalidPasswordException {
        boolean accountMenuWhile = true;
        while (accountMenuWhile) {
            ShowAccountMenu();
            LinkedHashMap<String, Article> ownArticles = getArticlesByUser(username);
            switch (sc.nextInt()) {
                case 1 -> ShowYourSales(sc, username, ownArticles);
                case 2 -> SeeFavorites(sc, user, favorites);
                case 3 -> username = ChangeUserUsername(sc, username);
                case 4 -> ChangeUserPassword(sc, user);
                case 5 -> DeleteUser(sc, username);
                case 0 -> accountMenuWhile = false;
            }
        }
        return username;
    }

    private static void ShowYourSales(Scanner sc, String username, LinkedHashMap<String, Article> ownArticles) throws IOException {
        showArticlesByUser(username, ownArticles);
        boolean seeArticlesByUserMenu = true;
        while (seeArticlesByUserMenu) {
            ShowOwnArticlesMenu();
            Map<String, Object> currMap = FileHandler.readFiles(Article.class, CATALOGUE_JSON);
            for (Map.Entry<String, Object> obj : currMap.entrySet()) {
                catalogue.put(obj.getKey(), (Article) obj.getValue());
            }
            ownArticles = getArticlesByUser(username);
            switch (sc.nextInt()) {
                case 1 -> ChangeArticlesPrice(sc, ownArticles);
                case 2 -> ChangeArticlesName(sc, ownArticles);
                case 3 -> DeactivateArticle(sc, ownArticles);
                case 4 -> DeleteArticle(sc, ownArticles);
                case 0 -> seeArticlesByUserMenu = false;
            }
        }
    }

    private static void DeleteAdministrator(Scanner sc, String username) throws IOException {
        System.out.println("Are you sure you want to delete your account? Yes/No");
        if (sc.next().equalsIgnoreCase("Yes")) {
            deleteAdministrator(username);
        }
        System.exit(0);
    }

    private static void RemoveAdministrator(Scanner sc) throws IOException, InvalidPasswordException {
        System.out.print("Name of administrator to remove: ");
        String nameOfAdministrator = sc.next();
        if (!administrators.containsKey(nameOfAdministrator)) {
            System.out.println("No such administrator!");
            System.out.print("Name of administrator to remove: ");
            nameOfAdministrator = sc.next();
        }
        removeAdministrator(administrators.get(nameOfAdministrator));
        System.out.println("Administrator removed!");
    }

    private static void AddUserAsAdministrator(Scanner sc) throws IOException {
        System.out.print("Name of user to add: ");
        String nameOfUser = sc.next();
        if (!users.containsKey(nameOfUser)) {
            System.out.println("No such user!");
            System.out.print("Name of user to add: ");
            nameOfUser = sc.next();
        }
        addAdministrator(users.get(nameOfUser));
        System.out.println("Administrator added!");
    }

    private static void ChangeAdministratorPassword(Scanner sc, Administrator administrator) throws InvalidPasswordException, IOException {
        System.out.print("Type your old password: ");
        String oldPassword = sc.next();
        if (PasswordValidator.isRightPassword(oldPassword, administrator.getPassword())) {
            System.out.println("Requirements: 8-15 characters, at least 1 number, 1 upper case, 1 lower case and 1 special symbol!");
            System.out.print("Type your new password: ");
            String newPassword = getValidPassword(sc);
            changeAdministratorPassword(administrator, PasswordHashing.getHashPassword(newPassword));
            System.out.println("Your password was changed!");
        } else {
            System.out.println("Wrong password!");
        }
    }

    private static String ChangeAdministratorUsername(Scanner sc, String username) throws IOException {
        System.out.print("Type your new username: ");
        String newUsername = sc.next();
        if (!containsUser(newUsername) && !containsAdministrator(newUsername)) {
            changeAdministratorName(username, newUsername);
            System.out.println("Your name was changed!");
            username = newUsername;
        } else {
            System.out.println("Username taken!");
        }
        return username;
    }

    private static void ShowAccountsMenu() {
        System.out.println("To change your name press '1'");
        System.out.println("To change your password press '2'");
        System.out.println("To add User as Administrator press '3'");
        System.out.println("To remove Administrator press '4'");
        System.out.println("To delete Account press '5'");
        System.out.println("To go back press '0'");
    }

    private static void DeleteCategory(Scanner sc) throws IOException {
        System.out.print("Name of category: ");
        String nameOfCategory = sc.next();
        if (containsCategory(nameOfCategory)) {
            removeCategory(nameOfCategory);
            System.out.println("Category deleted!");
        } else {
            System.out.println("No such category");
        }
    }

    private static void ChangeCategory(Scanner sc) throws IOException {
        System.out.print("Name of category: ");
        String nameOfCategory = sc.next();
        if (containsCategory(nameOfCategory)) {
            System.out.print("New name of category: ");
            String newNameOfCategory = sc.next();
            if (!containsCategory(newNameOfCategory)) {
                setCategoryName(nameOfCategory, newNameOfCategory);
                System.out.println("Category changed!");
            } else {
                System.out.println("This name is taken");
            }
        } else {
            System.out.println("No such category");
        }
    }

    private static void AddCategory(Scanner sc) throws IOException {
        System.out.print("Name of category: ");
        String nameOfCategory = sc.next();
        if (!containsCategory(nameOfCategory)) {
            addCategory(nameOfCategory);
            System.out.println("Category added!");
        } else {
            System.out.println("There is such category already");
        }
    }

    private static void ShowCategoriesMenu() {
        System.out.println("To add category press '1'");
        System.out.println("To change category press '2'");
        System.out.println("To delete category press '3'");
        System.out.println("To go back press '0'");
    }

    private static void ShowInactiveArticles(Scanner sc, LinkedHashMap<String, Article> inactiveCatalogue) {
        System.out.println("To filter them by deactivation date press '1'");
        System.out.println("To go back press '0'");
        int option = sc.nextInt();
        while (option != 1) {
            showArticlesFrom(inactiveCatalogue);
            FilterByDeactivationDate(sc, inactiveCatalogue);
            System.out.println("To filter them by deactivation date press '1'");
            System.out.println("To go back press '0'");
            option = sc.nextInt();
        }
    }

    private static void ShowActiveArticles(Scanner sc, LinkedHashMap<String, Article> activeCatalogue) {
        System.out.println("To filter them by date press '1'");
        System.out.println("To go back press '0'");
        int option = sc.nextInt();
        while (option != 1) {
            showArticlesFrom(activeCatalogue);
            FilterByDate(sc, activeCatalogue);
            System.out.println("To filter them by date press '1'");
            System.out.println("To go back press '0'");
            option = sc.nextInt();
        }
    }

    private static void ShowAdministratorLoginMenu() {
        System.out.println("To see all active Articles press '1'");
        System.out.println("To see all inactive Articles press '2'");
        System.out.println("For categories press '3'");
        System.out.println("For accounts press '4'");
        System.out.println("To log out press '0'.");
    }

    private static void DeleteUser(Scanner sc, String username) throws IOException {
        System.out.println("Are you sure you want to delete your account? Yes/No");
        if (sc.next().equalsIgnoreCase("Yes")) {
            deleteUser(username);
        }
        System.exit(0);
    }

    private static void ChangeUserPassword(Scanner sc, User user) throws InvalidPasswordException, IOException {
        System.out.print("Type your old password: ");
        String oldPassword = sc.next();
        if (PasswordValidator.isRightPassword(oldPassword, user.getPassword())) {
            System.out.println("Requirements: 8-15 characters, at least 1 number, 1 upper case, 1 lower case and 1 special symbol!");
            System.out.print("Type your new password: ");
            String newPassword = getValidPassword(sc);
            changeUserPassword(user, PasswordHashing.getHashPassword(newPassword));
            System.out.println("Your password was changed!");
        } else {
            System.out.println("Wrong password!");
        }
    }

    private static String ChangeUserUsername(Scanner sc, String username) throws IOException {
        System.out.print("Type your new username: ");
        String newUsername = sc.next();
        if (!containsUser(newUsername) && !containsAdministrator(newUsername)) {
            changeUserName(username, newUsername);
            System.out.println("Your name was changed!");
            username = newUsername;
        } else {
            System.out.println("Username taken!");
        }
        return username;
    }

    private static void SeeFavorites(Scanner sc, User user, LinkedHashMap<String, Article> favorites) throws IOException {
        if (favorites.isEmpty()) {
            System.out.println("Your favourite list is empty!");
            System.out.println();
            return;
        }
        System.out.println("To remove article from favourite press '1'");
        System.out.println("To go back press '0'");
        int option = sc.nextInt();
        while (option != 1) {
            user.showFavourites();

            System.out.print("Name of article: ");
            String articleName = sc.next();
            if (containsArticle(articleName, favorites)) {
                user.removeFromFavourites(favorites.get(articleName));
                System.out.println("Item removed!");
            } else {
                System.out.println("No such article!");
            }
            System.out.println("To remove article from favourite press '1'");
            System.out.println("To go back press '0'");
            option = sc.nextInt();
        }
    }

    private static void DeleteArticle(Scanner sc, LinkedHashMap<String, Article> ownArticles) throws IOException {
        System.out.print("Name of article: ");
        String articleName = sc.next();
        if (containsArticle(articleName, ownArticles)) {
            deleteArticle(articleName);
            System.out.println("Article deleted!");
        } else {
            System.out.println("No such article!");
        }
    }

    private static void DeactivateArticle(Scanner sc, LinkedHashMap<String, Article> ownArticles) throws IOException {
        System.out.print("Name of article: ");
        String articleName = sc.next();
        if (containsArticle(articleName, ownArticles)) {
            deactivate(articleName);
            System.out.println("Article deactivated!");
        } else {
            System.out.println("No such article!");
        }
    }

    private static void ChangeArticlesName(Scanner sc, LinkedHashMap<String, Article> ownArticles) throws IOException {
        System.out.print("Name of article: ");
        String articleName = sc.next();
        if (containsArticle(articleName, ownArticles)) {
            System.out.print("New name: ");
            String newName = sc.next();
            if (!catalogue.containsKey(articleName)) {
                changeArticlesName(articleName, newName);
                System.out.println("Name changed!");
            } else {
                System.out.println("This name is taken");
            }
        }
    }

    private static void ChangeArticlesPrice(Scanner sc, LinkedHashMap<String, Article> ownArticles) throws IOException {
        System.out.print("Name of article: ");
        String articleName = sc.next();
        if (containsArticle(articleName, ownArticles)) {
            System.out.print("New price: ");
            double newPrice = sc.nextDouble();
            changeArticlesPrice(articleName, newPrice);
            System.out.println("Price changed!");
        } else {
            System.out.println("No such article!");
        }
    }

    private static void ShowOwnArticlesMenu() {
        System.out.println("To change the price of an article press '1'");
        System.out.println("To change the name of an article press '2'");
        System.out.println("To deactivate an article press '3'");
        System.out.println("To delete an article press '4'");
        System.out.println("To go back press '0'");
    }

    private static void ShowAccountMenu() {
        System.out.println("To see your sales press '1'.");
        System.out.println("To see your favourites press '2'.");
        System.out.println("To change your name press '3'.");
        System.out.println("To change your password press '4'");
        System.out.println("To delete your account press '5'");
        System.out.println("To go back press '0'");
    }

    private static void SellArticle(Scanner sc, String username) throws IOException {
        System.out.print("Name of the article to sell: ");
        String articleName = sc.next();
        if (!catalogue.containsKey(articleName)) {
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
            addArticle(articleName, new Article(articleName, price, category, username));
            System.out.println("Your article has been released for sell successfully!");
        } else {
            System.out.println("This name is taken");
        }
    }

    private static void BuyArticle(Scanner sc, LinkedHashMap<String, Article> activeCatalogue) throws IOException {
        showArticlesFrom(activeCatalogue);
        System.out.print("Name of article: ");
        String articleName = sc.next();
        if (containsArticle(articleName, activeCatalogue)) {
            deleteArticle(articleName);
            System.out.println("Purchase successful!");
        } else {
            System.out.println("No such article!");
        }
    }

    private static void AddToFavorites(Scanner sc, User user, LinkedHashMap<String, Article> favorites, LinkedHashMap<String, Article> activeCatalogue) throws IOException {
        showArticlesFrom(activeCatalogue);
        System.out.print("Name of article: ");
        String articleName = sc.next();
        if (containsArticle(articleName, activeCatalogue)) {
            addIfNotInFavourites(user, favorites, articleName);
            System.out.println("Added successful!");
        } else {
            System.out.println("No such article!");
        }
    }

    private static void FilterByDate(Scanner sc, LinkedHashMap<String, Article> catalogue) {
        System.out.print("After (DD:MM:YYYY): ");
        String afterDate = sc.next();
        System.out.print("Before (DD:MM:YYYY): ");
        String beforeDate = sc.next();
        if (DateFormatting.isValidDateFormat(afterDate) && DateFormatting.isValidDateFormat(beforeDate)) {
            Date after = DateFormatting.getDate(afterDate);
            Date before = DateFormatting.getDate(beforeDate);
            showArticlesFromDate(after, before, catalogue);
        } else {
            System.out.println("This is invalid format! Try again!");
        }
    }

    private static void FilterByDeactivationDate(Scanner sc, LinkedHashMap<String, Article> catalogue) {
        System.out.print("After (DD:MM:YYYY): ");
        String afterDate = sc.next();
        System.out.print("Before (DD:MM:YYYY): ");
        String beforeDate = sc.next();
        if (DateFormatting.isValidDateFormat(afterDate) && DateFormatting.isValidDateFormat(beforeDate)) {
            Date after = DateFormatting.getDate(afterDate);
            Date before = DateFormatting.getDate(beforeDate);
            showArticlesFromDeactivationDate(after, before, catalogue);
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

    private static void ShowUserLoginMenu() {
        System.out.println("To see all Articles press '1'.");
        System.out.println("To see your Account press '2'.");
        System.out.println("To log out press '0'.");
    }

    private static void ShowMenu() {
        System.out.println("For 'Register' press '1'.");
        System.out.println("For 'Log In' press '2'.");
        System.out.println("To 'Exit' press '0'.");
    }

    private static void Register(Scanner sc) throws IOException, InvalidPasswordException {
        System.out.print("Insert username: ");
        String username = sc.next();
        while (containsUser(username) || containsAdministrator(username)) {
            System.out.println("Name taken!");
            System.out.print("Insert username: ");
            username = sc.next();
        }
        System.out.println("Requirements: 8-15 characters, at least 1 number, 1 upper case, 1 lower case and 1 special symbol!");
        System.out.print("Insert password: ");
        String password = getValidPassword(sc);
        registerUser(username, PasswordHashing.getHashPassword(password));
        System.out.println("Registration successful!");
    }

    private static String getValidPassword(Scanner sc) {
        String password = sc.next();
        boolean whileInvalid = true;
        while (whileInvalid) {
            try {
                if (PasswordValidator.isValidPassword(password)) {
                    whileInvalid = false;
                }
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
                System.out.println("Requirements: 8-15 characters, at least 1 number, 1 upper case, 1 lower case and 1 special symbol!");
                System.out.print("Try again: ");
                password = sc.next();
            }
        }
        return password;
    }

    private static void ReadFiles() throws IOException {
        Map<String, Object> currMap = FileHandler.readFiles(User.class, USERS_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            users.put(obj.getKey(), (User) obj.getValue());
        }
        currMap = FileHandler.readFiles(Administrator.class, ADMINISTRATORS_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            administrators.put(obj.getKey(), (Administrator) obj.getValue());
        }
        currMap = FileHandler.readFiles(Article.class, CATALOGUE_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            catalogue.put(obj.getKey(), (Article) obj.getValue());
        }
        currMap = FileHandler.readFiles(String.class, CATEGORIES_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            categories.put(obj.getKey(), (String) obj.getValue());
        }

    }
}