import Page.LoginPage;
import Page.ProfilePage;
import Util.Helper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LandingPage extends Helper {
    LoginPage lg = new LoginPage();
    ProfilePage pp = new ProfilePage();

    @BeforeMethod(alwaysRun = true)
    public void login() {
        lg.loginToApp();
    }

    @Test(priority = 7, description = "This test case to check Profile UI")
    public void verifyProfilePage() {
        pp.verifyProfileUI();
    }

    @Test(priority = 8, description = "This test case to check pagination")
    public void verifyProfilePageOption() {
        pp.verifyPageSizeOption("5");
        pp.verifyPageSizeOption("10");
        pp.verifyPageSizeOption("20");
        pp.verifyPageSizeOption("25");
        pp.verifyPageSizeOption("50");
        pp.verifyPageSizeOption("100");
    }

    @Test(priority = 9, description = "This test case to check Go to store Button")
    public void verifyGoBackToStore() {
        pp.goToStore();
    }

    @AfterMethod(alwaysRun = true)
    public void logout() {
        lg.logout();
    }
}
