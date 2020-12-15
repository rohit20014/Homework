package Page;

import Util.Helper;
import Util.ReadPropFile;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class ProfilePage extends Helper {
    ReadPropFile prop = new ReadPropFile();
    StoreAPI api = new StoreAPI();
    Constant constant = new Constant();
    BookStore bs = new BookStore();

    public void openProfileURL() {
        driver.get("https://demoqa.com/profile");
    }

    public void verifyProfileUI() {
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        moveFocusToElement(header);
        verifyText(header, "Profile");

        WebElement usernameLabel = findElementByXpath(constant.PROFILE_USERNAME_LABEL);
        verifyText(usernameLabel, "User Name :");

        WebElement username = findElementById(constant.USERNAME_VALUE);
        verifyText(username, prop.getPropertyValue("username"));

        WebElement logoutButton = findElementByXpath(constant.LOGOUT_BUTTON);
        verifyText(logoutButton, "Log out");

        WebElement bookLabel = findElementByXpath(constant.SEARCH_BOOK_LABEL);
        verifyText(bookLabel, "Books :");

        checkVisibilityOfElement(constant.SEARCH_BOX);
        checkVisibilityOfElement(constant.SEARCH_ICON);

        WebElement tableHeader = findElementByXpath(constant.TABLE_HEADER_LABEL);
        verifyText(tableHeader, "Image\nTitle\nAuthor\nPublisher\nAction");

        WebElement searchResultNoData = findElementByXpath(constant.NO_DATA_MESSAGE);
        verifyText(searchResultNoData, "No rows found");

        WebElement previousButton = findElementByXpath(constant.PREVIOUS_BUTTON);
        verifyText(previousButton, "Previous");

        WebElement nextButton = findElementByXpath(constant.NEXT_BUTTON);
        verifyText(nextButton, "Next");

        WebElement pageInfo = findElementByXpath(constant.PAGE_OPTIONS);
        verifyText(pageInfo, "Page of 1");

        WebElement getOptions = findElementByXpath(constant.ROW_DROPDOWN);
        verifyText(getOptions, "5 rows\n10 rows\n20 rows\n25 rows\n50 rows\n100 rows");

        WebElement goToStore = findElementById(constant.GO_TO_STORE_BUTTON);
        moveFocusToElement(goToStore);
        verifyText(goToStore, "Go To Book Store");

        WebElement deleteAccount = findElementByXpath(constant.DELETE_ACCOUNT_BUTTON);
        verifyText(deleteAccount, "Delete Account");

        WebElement deleteAllBooks = findElementByXpath(constant.DELETE_ALL_BOOKS_BUTTON);
        verifyText(deleteAllBooks, "Delete All Books");
    }

    public void verifyPageSizeOption(String pageSize) {
        int size = Integer.valueOf(pageSize);
        WebElement moveToDropdown = findElementByXpath(constant.ROW_DROPDOWN);
        moveFocusToElement(moveToDropdown);
        Select dropdownValue = selectDropdown(constant.ROW_DROPDOWN);
        dropdownValue.selectByValue(pageSize);
        List<WebElement> elements = elementsList(constant.ROW_NUMBERS);
        Assert.assertEquals(size, elements.size());
    }

    public void goToStore() {
        WebElement goToStore = findElementById(constant.GO_TO_STORE_BUTTON);
        moveFocusToElement(goToStore);
        goToStore.click();
        forceWait(2000);
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("booksURL"));
    }

    public void backToProfile() {
        WebElement bookTitle = findElementByXpath(constant.BOOK_TITLE);
        bookTitle.click();
        bs.verifyBookDetails("bookTitle");
        WebElement backToBookStore = findElementByXpath(constant.BACK_TO_BOOK_STORE_BUTTON);
        backToBookStore.click();
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("profileURL"));
    }

    public void deleteOneBook() {
        String headerMessage = "Delete Book";
        String bodyMessage = "Do you want to delete this book?";
        WebElement deleteOneBook = findElementByXpath(constant.BOOK_TITLE_DELETE);
        moveFocusToElement(deleteOneBook);
        deleteOneBook.click();
        deleteModal(headerMessage, bodyMessage);
        waitForAlertAndAccept("one");
    }

    public void deleteAllBooks(String deleteCase) {
        String headerMessage = "Delete All Books";
        String bodyMessage = "Do you want to delete all books?";
        WebElement deleteAllBook = findElementByXpath(constant.DELETE_ALL_BOOKS_BUTTON);
        moveFocusToElement(deleteAllBook);
        deleteAllBook.click();
        deleteModal(headerMessage, bodyMessage);
        waitForAlertAndAccept(deleteCase);
        WebElement searchResultNoData = findElementByXpath(constant.NO_DATA_MESSAGE);
        verifyText(searchResultNoData, "No rows found");
    }

    public void searchBook() {
        //search book
        WebElement searchBookInput = findElementById(constant.SEARCH_BOX);
        searchBookInput.clear();
        searchBookInput.sendKeys(prop.getPropertyValue("bookTitle"));

        //verify search result
        WebElement searchResultTitle = findElementByXpath(constant.SEARCH_FIRST_RESULT_TITLE);
        verifyTextViaAPI(searchResultTitle, prop.getPropertyValue("bookTitle"), "title");

        WebElement searchResultAuthor = findElementByXpath(constant.SEARCH_FIRST_RESULT_AUTHOR);
        verifyTextViaAPI(searchResultTitle, prop.getPropertyValue("bookTitle"), "title");

        WebElement searchResultPublisher = findElementByXpath(constant.SEARCH_FIRST_RESULT_PUBLISHER);
        verifyTextViaAPI(searchResultTitle, prop.getPropertyValue("bookTitle"), "title");
    }

    public void searchInvalidBook() {
        //search invalid book
        WebElement searchBookInput1 = findElementById(constant.SEARCH_BOX);
        searchBookInput1.clear();
        searchBookInput1.sendKeys("ZZYY");
        //Verify search result
        WebElement searchResultNoData = findElementByXpath(constant.NO_DATA_MESSAGE);
        verifyText(searchResultNoData, "No rows found");
    }

    public void deleteUserAccount() {
        String headerMessage = "Delete Account";
        String bodyMessage = "Do you want to delete your account?";
        WebElement deleteUserAccount = findElementByXpath(constant.DELETE_ACCOUNT_BUTTON);
        moveFocusToElement(deleteUserAccount);
        deleteUserAccount.click();
        deleteModal(headerMessage, bodyMessage);
        waitForAlertAndAccept("account");
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("loginURL"));
    }

    public void deleteModal(String headerMessage, String bodyMessage) {
        WebElement deleteHeader = findElementByXpath(constant.DELETE_MODEL_HEADER);
        moveFocusToElement(deleteHeader);
        verifyText(deleteHeader, headerMessage);

        WebElement deleteBody = findElementByXpath(constant.DELETE_MODEL_BODY);
        verifyText(deleteBody, bodyMessage);

        WebElement deleteCancel = findElementByXpath(constant.DELETE_MODEL_CANCEL);
        verifyText(deleteCancel, "Cancel");

        WebElement deleteOk = findElementByXpath(constant.DELETE_MODEL_OK);
        verifyText(deleteOk, "OK");
        deleteOk.click();
    }

    public void verifyAsNonUserProfileUI() {
        openProfileURL();
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        moveFocusToElement(header);
        verifyText(header, "Profile");
        WebElement notLoggedProfile = findElementById(constant.PROFILE_USER);
        verifyText(notLoggedProfile, "Currently you are not logged into the Book Store application, please visit the login page to enter or register page to register yourself.");
        openNonUserProfilePageLinks(constant.PROFILE_LOGIN_LINK, "loginURL");
        openNonUserProfilePageLinks(constant.PROFILE_REGISTER_LINK, "newUserURL");
    }

    public void openNonUserProfilePageLinks(String locator, String path) {
        WebElement link = findElementByXpath(locator);
        link.click();
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue(path));
        openProfileURL();
    }
}
