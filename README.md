# Homework

## Steps
1. Fork this repository
2. Commit & Push your code that completes the objective below to your repo
3. Send us the link to your repo once you have completed the objective

## Important Links
2. Use the [Bookstore Application](https://demoqa.com/books)
3. Use the [Bookstore API Documentation](https://demoqa.com/swagger/)
    
### Explanation
Using the website and api documentation given above, we want you to build a project in Python or Javascript that goes through the following flow:

We want you thinking about **quality** while you are writing the code. Feel free to add extra verification checks or extra steps to ensure a high standard of quality on the application. 

All interactions with the website must be **Automated** or via the **API**.

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
