import Page.LoginPage;
import Page.ProfilePage;
import Util.Helper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BookStoreProfile extends Helper {
    LoginPage lg = new LoginPage();
    ProfilePage pp = new ProfilePage();

    @BeforeMethod(alwaysRun = true)
    public void login() {
        lg.loginToApp();
    }

    @Test(priority = 15, description = "This test case to check valid book")
    public void verifyBackToProfile() {
        pp.backToProfile();
    }

    @Test(priority = 15, description = "This test case to check valid book")
    public void verifySearchBook() {
        pp.searchBook();
    }

    @Test(priority = 16, description = "This test case to check search invalid book")
    public void verifySearchInvalidBook() {
        pp.searchInvalidBook();
    }

    @Test(priority = 17, description = "This test case to delete one book")
    public void verifyDeleteOneBook() {
        pp.deleteOneBook();
    }

    @Test(priority = 18, description = "This test case to delete user all books")
    public void verifyDeleteAllBook() {
        pp.deleteAllBooks("All");
    }

    @Test(priority = 19, description = "This test case to check delete all if no books available")
    public void verifyDeleteWhenNoBooks() {
        pp.deleteAllBooks("No Book");
    }

    @AfterMethod(alwaysRun = true)
    public void logout() {
        lg.logout();
    }
}
