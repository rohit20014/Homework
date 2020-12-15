import Page.LoginPage;
import Util.Helper;
import org.testng.annotations.Test;

public class BookStoreLogin extends Helper {
    LoginPage lg = new LoginPage();

    @Test(priority = 1, description = "This test case to check New user UI")
    public void checkNewUserPage() {
        lg.createUser();
        lg.verifyRegisterButton();
        lg.backToLoginPage();
    }

    @Test(priority = 2, description = "This test case to check Login UI")
    public void checkLoginPage() {
        lg.openLoginPage();
    }

    @Test(priority = 3, description = "This test case to check Invalid username login")
    public void checkInvalidUsernameLogin() {
        lg.invalidUsernameLogin();
    }

    @Test(priority = 4, description = "This test case to check Invalid password login")
    public void checkInvalidPasswordLogin() {
        lg.invalidPasswordLogin();
    }

    @Test(priority = 5, description = "This test case to check valid login")
    public void checkValidLogin() {
        lg.loginToApp();
    }

    @Test(priority = 6, description = "This test case to check logout")
    public void checkLogout() {
        lg.logout();
    }
}
