Fun7 Game Backend

This is the backend application for the Fun7 game. It provides various services and APIs to manage users, game features, and advertisements.

Prerequisites
Before running the application, make sure you have the following installed on your system:

Java 11 or higher
Maven
MySQL Database (or any other supported database)
Postman (for API testing) - I will attach the Postman collection

- Getting Started:
    Clone the repository to your local machine: git clone https://github.com/your-username/fun7-game-backend.git

- Navigate to the project directory:
      cd fun7-game-backend

- Set up the database:

Create a new MySQL database for the project.
Open the application.properties file located in src/main/resources.
Update the database configuration with your database URL, username, and password.
But I will send you the script of my database with the data that I use for the testing API's.

- Build the project using Maven:
    mvn clean install

- Run the application:
    mvn spring-boot:run
    or you can set it in the configuration tab to run the main method in Fun7GameBackendApplication.class, and then an application should run at 'http://localhost:8080'

- API testing with Postman: 
    When you import collection 'fun7_backend_game_collection.json' it will be a couple of requests that you can use for testing a backend endpoint

- Additional information:
    You will see some of the comments in the code that I put, It is just to be easier to clear some steps of what each method does or what we perform on that case.
    Some of the assumptions that I made were to enable multiplayer service in that way where I use the data from the MySQL database.
    Otherwise, for customer support, I understand that we are talking in real-time and when I test services on weekends and hours it was just as we wanted to be disabled.
    Also, there are some test classes that you can take a look at and run and they have test coverage.
  
