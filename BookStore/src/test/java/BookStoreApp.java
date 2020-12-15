import Page.BookStore;
import Page.LoginPage;
import Page.ProfilePage;
import Util.Helper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BookStoreApp extends Helper {

        LoginPage lg = new LoginPage();
        BookStore bs = new BookStore();
        ProfilePage pp = new ProfilePage();

    @BeforeMethod(alwaysRun = true)
    public void login() {
        lg.loginToApp();
        pp.goToStore();
    }

    @Test(priority = 10, description = "This test case to check Book Store UI")
    public void verifyBookStorePage() {
        bs.verifyBookStoreDashboardUI();
    }

    @Test(priority = 11, description = "This test case to check book store pagination")
    public void verifyBookStorePagination() {
        bs.verifyPagination();
    }

    @Test(priority = 12, description = "This test case to add books to collection")
    public void verifyAddingBookToCollection() {
        bs.addBooksToCollection();
    }

    @Test(priority = 13, description = "This test case to add books to collection")
    public void verifySearchBook() {
        pp.searchBook();
    }

    @Test(priority = 14, description = "This test case to add books to collection")
    public void verifySearchInvalidBook() {
        pp.searchInvalidBook();
    }

    @AfterMethod(alwaysRun = true)
    public void logout() {
        lg.logout();
    }
}
