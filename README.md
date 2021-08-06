# Simple_Banking_System

This is a Simple banking system with SQLite database.
The number of card contains 16 digits and starts from "400000" (bank code).
Every card number must satisfy the Luhn's algorithm (the last digit is a checksum).

User can create new cards and log into an account. 
Logged in, there're these opportunities:
1) Get balance of the card
2) Add some income (user must enter the amount of income)
3) To transfer to another existing account
4) Close the account (in other words, delete)
5) Log out (after logging out user can create or log into another account)

After exiting the program all card data will be saved.

Technologies:
1) Java
2) SQL, SQLite, JDBC
3) Gradle

The .jar file:
Banking_System.jar

<img width="387" alt="Снимок экрана 2021-08-07 в 0 12 34" src="https://user-images.githubusercontent.com/54900460/128571905-3849f49f-11dd-4c6b-b3a3-af2d78173a88.png">
<img width="306" alt="Снимок экрана 2021-08-07 в 0 13 08" src="https://user-images.githubusercontent.com/54900460/128571911-4410dd8e-01d0-4cfc-9753-329de1da49c7.png">
<img width="437" alt="Снимок экрана 2021-08-07 в 0 15 48" src="https://user-images.githubusercontent.com/54900460/128571915-90b77354-68f0-47db-ab36-09bfbd76daa3.png">
