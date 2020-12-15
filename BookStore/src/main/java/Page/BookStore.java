package Page;

import Util.Helper;
import Util.ReadPropFile;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class BookStore extends Helper {
    ReadPropFile prop = new ReadPropFile();
    StoreAPI api = new StoreAPI();
    Constant constant = new Constant();

    public void verifyBookStoreDashboardUI() {
        verifyCommonProfileUI();

        WebElement usernameLabel = findElementByXpath(constant.PROFILE_USERNAME_LABEL);
        verifyText(usernameLabel, "User Name :");

        WebElement username = findElementById(constant.USERNAME_VALUE);
        verifyText(username, prop.getPropertyValue("username"));

        WebElement logoutButton = findElementByXpath(constant.LOGOUT_BUTTON);
        verifyText(logoutButton, "Log out");
    }

    public void verifyCommonProfileUI() {
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        moveFocusToElement(header);
        verifyText(header, "Book Store");

        checkVisibilityOfElement(constant.SEARCH_BOX);
        checkVisibilityOfElement(constant.SEARCH_ICON);

        WebElement tableHeader = findElementByXpath(constant.TABLE_HEADER_LABEL);
        verifyText(tableHeader, "Image\nTitle\nAuthor\nPublisher");

        WebElement previousButton = findElementByXpath(constant.PREVIOUS_BUTTON);
        verifyText(previousButton, "Previous");

        WebElement nextButton = findElementByXpath(constant.NEXT_BUTTON);
        verifyText(nextButton, "Next");

        WebElement pageInfo = findElementByXpath(constant.PAGE_OPTIONS);
        verifyText(pageInfo, "Page of 1");

        WebElement getOptions = findElementByXpath(constant.ROW_DROPDOWN);
        verifyText(getOptions, "5 rows\n10 rows\n20 rows\n25 rows\n50 rows\n100 rows");
    }

    public void verifyAsNonUserBookStoreUI() {
        driver.get(prop.getPropertyValue("booksURL"));
        verifyCommonProfileUI();
        WebElement login = findElementById(constant.LOGIN_BUTTON);
        verifyText(login, "Login");
    }

    public void openLoginPage() {
        WebElement login = findElementById(constant.LOGIN_BUTTON);
        verifyText(login, "Login");
        login.click();
        String actualURL = currentUrl();
        verifyText(actualURL, prop.getPropertyValue("loginURL"));
    }

    public void verifyPagination() {
        int size = api.getBooksList();
        WebElement moveToDropdown = findElementByXpath(constant.ROW_DROPDOWN);
        moveFocusToElement(moveToDropdown);
        Select dropdownValue = selectDropdown(constant.ROW_DROPDOWN);
        WebElement selectedElement = dropdownValue.getFirstSelectedOption();
        String value = selectedElement.getText();
        int rows = Integer.valueOf(value.replace("rows","").trim());
        if(rows > size) {
            checkButtonIsDisabled(constant.NEXT_BUTTON);
            checkButtonIsDisabled(constant.PREVIOUS_BUTTON);
        }
        //Select Pagination from dropdown and verify row count
        verifyPaginationDropdown(5, size);
        verifyPaginationDropdown(10,size);
        verifyPaginationDropdown(20,size);
        verifyPaginationDropdown(25,size);
        verifyPaginationDropdown(50,size);
        verifyPaginationDropdown(100,size);
    }

    public void verifyPaginationDropdown(int paginationOption, int size) {
        WebElement moveToDropdown = findElementByXpath(constant.ROW_DROPDOWN);
        moveFocusToElement(moveToDropdown);
        Select dropdownValue = selectDropdown(constant.ROW_DROPDOWN);
        dropdownValue.selectByValue(String.valueOf(paginationOption));
        List<WebElement> elements = elementsList(constant.ROW_NUMBERS);
        Assert.assertEquals(paginationOption,elements.size());
        if(size > elements.size()) {
            checkButtonIsDisabled(constant.PREVIOUS_BUTTON);
            WebElement nextButton = findElementByXpath(constant.NEXT_BUTTON);
            verifyText(nextButton,"Next");
            nextButton.click();
            WebElement previousButton = findElementByXpath(constant.PREVIOUS_BUTTON);
            verifyText(previousButton,"Previous");
            previousButton.click();
        } else {
            checkButtonIsDisabled(constant.NEXT_BUTTON);
            checkButtonIsDisabled(constant.PREVIOUS_BUTTON);
        }
    }

    public void checkButtonIsDisabled(String locator) {
        WebElement nextButton = findElementByXpath(locator);
        String outerHTML= nextButton.getAttribute("outerHTML");
        Boolean bool = outerHTML.contains("disabled");
        Assert.assertEquals(bool, Boolean.TRUE);
    }

    public void addBooksToCollection() {
        //Add Book to Collection
        verifyAndAddBookToCollection(constant.BOOK_TITLE,"bookTitle","new");
        verifyAndAddBookToCollection(constant.BOOK_TITLE,"bookTitle","Added");
        verifyAndAddBookToCollection(constant.BOOK_TITLE2,"bookTitle2", "new");
        verifyAndAddBookToCollection(constant.BOOK_TITLE3,"bookTitle3", "new");
    }

    public void verifyAndAddBookToCollection(String bookTitle, String key, String bookType) {
        WebElement getBookTitle = findElementByXpath(bookTitle);
        getBookTitle.click();

        //verify opened book header
        WebElement header = findElementByXpath(constant.MAIN_HEADER);
        moveFocusToElement(header);
        verifyText(header, "Book Store");
        //Verify username label
        WebElement usernameLabel = findElementByXpath(constant.PROFILE_USERNAME_LABEL);
        verifyText(usernameLabel, "User Name :");
        //verify username
        WebElement username = findElementById(constant.USERNAME_VALUE);
        verifyText(username, prop.getPropertyValue("username"));
        //verify Logout
        WebElement logoutButton = findElementByXpath(constant.LOGOUT_BUTTON);
        verifyText(logoutButton, "Log out");

        verifyBookDetails(key);

        WebElement addToYourCollectionButton = findElementByXpath(constant.ADD_TO_YOUR_COLLECTION_BUTTON);
        moveFocusToElement(addToYourCollectionButton);
        addToYourCollectionButton.click();
        waitForAlertAndAccept(bookType);

        WebElement backToBookStore = findElementByXpath(constant.BACK_TO_BOOK_STORE_BUTTON);
        backToBookStore.click();
        forceWait(1000);
    }

    public void verifyBookDetails(String key) {
        //Verify isbn from API
        WebElement getBookISBN = findElementByXpath(constant.BOOK_ISBN);
        String isbn = api.getBookDetails(prop.getPropertyValue(key),"isbn");
        verifyText(getBookISBN,isbn);

        //Verify Sub Title from API
        WebElement getBookSubTitle = findElementByXpath(constant.BOOK_SUBTITLE);
        String subTitle = api.getBookDetails(prop.getPropertyValue(key),"subTitle");
        verifyText(getBookSubTitle,subTitle);

        //Verify Author from API
        WebElement getBookAuthor = findElementByXpath(constant.BOOK_AUTHOR);
        String author = api.getBookDetails(prop.getPropertyValue(key),"author");
        verifyText(getBookAuthor,author);

        //Verify Author from API
        WebElement getBookPublisher = findElementByXpath(constant.BOOK_PUBLISHER);
        String publisher = api.getBookDetails(prop.getPropertyValue(key),"publisher");
        verifyText(getBookPublisher,publisher);

        //Verify page from API
        WebElement getBookPage = findElementByXpath(constant.BOOK_PAGES);
        String page = api.getBookDetails(prop.getPropertyValue(key),"pages");
        verifyText(getBookPage,page);

        //Verify description from API
        WebElement getBookDescription = findElementByXpath(constant.BOOK_DESCRIPTION);
        moveFocusToElement(getBookDescription);
        String description = api.getBookDetails(prop.getPropertyValue(key),"description");
        verifyText(getBookDescription,description);

        //Verify website link from API
        WebElement getBookWebsite = findElementByXpath(constant.BOOK_WEBSITE);
        moveFocusToElement(getBookWebsite);
        String website = api.getBookDetails(prop.getPropertyValue(key),"website");
        verifyText(getBookWebsite,website);
    }
}
