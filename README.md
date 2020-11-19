# Homework


## What you need to know
1. Fork this repository
2. Use the [Bookstore Application](https://demoqa.com/books)
3. Use the [Bookstore API Documentation](https://demoqa.com/swagger/)
    
### Homework Project
Using the website and api documentation given above, we want you to build a project in Python or Javascript that goes through the following flow:

All interactions with the website must be **Automated** or via the **API**.

We want you thinking about quality while you are writing the objective. On each step feel free to add extra verifcation steps to ensure that the quality of the application is being tested throughly. You can also add extra steps and verifications as you would on any application that you are testing. 

#### Objective
1. Create an new user via the API
  * Verify the user is created
2. Login to that account via the GUI
  * Verify successful login
3. Add 3 books to the users collection via the GUI
  * Verify they were added via the GUI
4. Remove one of the books from the users collection via the GUI
  * Verify it was removed
5. Search for one of the remaining books via the GUI
  * Verify the search results
6. Clear the search
  * Verify list is correct
7. Using the api try to delete the user you created in Step 1
  * Verify that you don't have permissions to do that
8. Generate a Authorization Token for your account
  * Verify that you are now successfully authorized
9. Using the api and the Authorization Token delete your account
  * Verify that the account is deleted
10. Try to login with the user
  * Verify the user fails to login because account doesn't exist

P.S - On each step you can choose to do extra verification if you feel like it would help 

### The bookstore application has three GUI interfaces
    - Login Page
    - User Profile Page
    - Bookstore Page

### The bookstore has API documentation available at [Bookstore API](https://demoqa.com/swagger/)
    
    Example API Call that will get all books currently in the bookstore <https://demoqa.com/BookStore/v1/Books>
