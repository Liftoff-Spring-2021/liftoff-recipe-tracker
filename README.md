# **Liftoff Recipe Tracker**
### Overview
Liftoff Recipe Tracker is a recipe app that allows the user to search, save, and modify recipes.
### Features
The Liftoff Recipe Tracker is an app that allows users to:
* Log in/log out
* Search by keyword, pantry items, or category
* Create, read, update, and delete recipes  
* Create, read, update, and delete pantry items 
* View recipes by category
* Generate a shopping lists
* Generate a recipe list
* Scale number of servings for a recipe
* Share recipes
* Plan meals with calendaring
* Generate beverage pairings

### Technologies
* Java
* Spring Boot
* Spring Security
* MySQL
* Hibernate
* Thymeleaf templates
* ?API

### Installing The LRT Locally
1. Clone the project to your local machine from the [LRT Github](https://github.com/Liftoff-Spring-2021/liftoff-recipe-tracker)
2. Create a MySQL schema and administrative user for the project
   - Schema name, username, and password can be unique to your person
3. Start the project in your IDE of choice and Gradle will build the project structure from the dependencies
4. Use the Gradle bootrun command either via the CLI or via Gradle's integrated UI to initialize the project
5. Once the initial bootrun command fails, edit the project configuration and add necessary environment variables. The variables for this project are as follows:
   - DB_HOST (If you are running the project locally, your host is likely localhost)
   - DB_PORT (The standard port for MySQL is 3306)
   - DB_APP (This will be your schema name)
   - DB_USERNAME (The username for your schema admin)
   - DB_PASSWORD (The password for your schema admin)
6. Now that you have your environment variables configured, run the project again
7. To ensure the project starts correctly, open your preferred browser to localhost:8080