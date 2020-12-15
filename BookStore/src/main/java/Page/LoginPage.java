package Page;

import Util.Helper;
import Util.ReadPropFile;
import org.openqa.selenium.WebElement;

public class LoginPage extends Helper {
    ReadPropFile prop = new ReadPropFile();
    Constant constant = new Constant();

    public void openLoginPage() {
        driver.get(prop.getPropertyValue("loginURL"));
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        verifyText(header, "Login");
        WebElement welcomeMessage = findElementByXpath(constant.WELCOME_MESSAGE);
        verifyText(welcomeMessage, "Welcome,\nLogin in Book Store");
        //Username field verification
        WebElement usernameLabel = findElementById(constant.USERNAME_LABEL);
        verifyText(usernameLabel, "UserName :");
        WebElement usernameInput = findElementById(constant.USERNAME_INPUT);
        usernameInput.clear();
        usernameInput.sendKeys(prop.getPropertyValue("username"));
        //password field verification
        WebElement passwordLabel = findElementById(constant.PASSWORD_LABEL);
        verifyText(passwordLabel, "Password :");
        WebElement passwordInput = findElementById(constant.PASSWORD_INPUT);
        passwordInput.clear();
        passwordInput.sendKeys(prop.getPropertyValue("password"));
        WebElement loginButton = findElementById(constant.LOGIN_BUTTON);
        verifyText(loginButton, "Login");
        WebElement newUserButton = findElementById(constant.NEW_USER_BUTTON);
        verifyText(newUserButton, "New User");
        newUserButton.click();
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("newUserURL"));
    }

    public void createUser() {
        driver.get(prop.getPropertyValue("newUserURL"));
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        verifyText(header, "Register");
        WebElement welcomeMessage = findElementByXpath(constant.WELCOME_MESSAGE);
        verifyText(welcomeMessage, "Register to Book Store");
        //First Name Verification
        WebElement firstNameLabel = findElementById(constant.FIRST_NAME_LABEL);
        verifyText(firstNameLabel, "First Name :");
        WebElement firstNameInput = findElementById(constant.FIRST_NAME_INPUT);
        firstNameInput.clear();
        firstNameInput.sendKeys(prop.getPropertyValue("firstName"));
        //Last Name Verification
        WebElement lastNameLabel = findElementById(constant.LAST_NAME_LABEL);
        verifyText(lastNameLabel, "Last Name :");
        WebElement lastNameInput = findElementById(constant.LAST_NAME_INPUT);
        lastNameInput.clear();
        lastNameInput.sendKeys(prop.getPropertyValue("lastName"));
        //Username verification
        WebElement usernameLabel = findElementById(constant.USERNAME_LABEL);
        verifyText(usernameLabel, "UserName :");
        WebElement usernameInput = findElementById(constant.USERNAME_INPUT);
        usernameInput.clear();
        usernameInput.sendKeys(prop.getPropertyValue("username"));
        //password field verification
        WebElement passwordLabel = findElementById(constant.PASSWORD_LABEL);
        verifyText(passwordLabel, "Password :");
        WebElement passwordInput = findElementById(constant.PASSWORD_INPUT);
        passwordInput.clear();
        passwordInput.sendKeys(prop.getPropertyValue("password"));
        //Switch to iframe
        switchFrame(constant.CAPTCHA_IFRAME);
        //Captcha Verification
        WebElement captchaCheckbox = findElementByXpath(constant.RECAPTCHA_INPUT);
        //Switch back to main window
        switchToMainWindow();
        //Register
        WebElement register = findElementById(constant.REGISTER_BUTTON);
        verifyText(register, "Register");
        //BackTOLogin Button
        WebElement backToLogin = findElementById(constant.BACK_TO_LOGIN);
        verifyText(backToLogin, "Back to Login");
    }

    public void verifyRegisterButton() {
        WebElement register = findElementById(constant.REGISTER_BUTTON);
        verifyText(register, "Register");
        register.click();
        WebElement registerErrorMessage = findElementById(constant.REGISTER_ERROR_MESSAGE);
        verifyText(registerErrorMessage,  prop.getPropertyValue("registerAlertMessage"));
    }

    public void backToLoginPage() {
        WebElement backToLogin = findElementById(constant.BACK_TO_LOGIN);
        verifyText(backToLogin, "Back to Login");
        backToLogin.click();
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("loginURL"));
    }

    public void loginToApp() {
        driver.get(prop.getPropertyValue("loginURL"));
        WebElement usernameInput = findElementById(constant.USERNAME_INPUT);
        usernameInput.clear();
        usernameInput.sendKeys(prop.getPropertyValue("username"));
        WebElement passwordInput = findElementById(constant.PASSWORD_INPUT);
        passwordInput.clear();
        passwordInput.sendKeys(prop.getPropertyValue("password"));
        WebElement loginButton = findElementById(constant.LOGIN_BUTTON);
        verifyText(loginButton, "Login");
        loginButton.click();
        forceWait(2000);
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        verifyText(header, "Profile");
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("profileURL"));
    }

    public void invalidUsernameLogin() {
        driver.get(prop.getPropertyValue("loginURL"));
        WebElement usernameInput = findElementById(constant.USERNAME_INPUT);
        usernameInput.clear();
        usernameInput.sendKeys("ZZZ");
        WebElement passwordInput = findElementById(constant.PASSWORD_INPUT);
        passwordInput.clear();
        passwordInput.sendKeys(prop.getPropertyValue("password"));
        WebElement loginButton = findElementById(constant.LOGIN_BUTTON);
        verifyText(loginButton, "Login");
        loginButton.click();
        WebElement loginErrorMessage = findElementById(constant.LOGIN_ERROR_MESSAGE);
        verifyText(loginErrorMessage,"Invalid username or password!");
    }

    public void invalidPasswordLogin() {
        driver.get(prop.getPropertyValue("loginURL"));
        WebElement usernameInput = findElementById(constant.USERNAME_INPUT);
        usernameInput.clear();
        usernameInput.sendKeys("ZZZ");
        WebElement passwordInput = findElementById(constant.PASSWORD_INPUT);
        passwordInput.clear();
        passwordInput.sendKeys("zzz");
        WebElement loginButton = findElementById(constant.LOGIN_BUTTON);
        verifyText(loginButton, "Login");
        loginButton.click();
        WebElement loginErrorMessage = findElementById(constant.LOGIN_ERROR_MESSAGE);
        verifyText(loginErrorMessage,"Invalid username or password!");
    }

    public void logout() {
        WebElement logout = findElementByXpath(constant.LOGOUT_BUTTON);
        verifyText(logout,"Log out");
        logout.click();
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("loginURL"));
    }

}
