import Page.BookStore;
import Page.LoginPage;
import Page.ProfilePage;
import org.testng.annotations.Test;

public class NonAuthorizedUser {
    LoginPage lg = new LoginPage();
    ProfilePage pp = new ProfilePage();
    BookStore bs = new BookStore();

    @Test(priority = 20, description = "This test case to delete user account")
    public void verifyDeleteUserAccount() {
        lg.loginToApp();
        pp.deleteUserAccount();
    }

    @Test(priority = 21, description = "This test case to check book store page without Login")
    public void verifyNonUserBookStorePage() {
        bs.verifyAsNonUserBookStoreUI();
        bs.openLoginPage();
    }

    @Test(priority = 22, description = "This test case to check profile page without Login")
    public void verifyNonUserProfilePage() {
        pp.verifyAsNonUserProfileUI();
    }
}
