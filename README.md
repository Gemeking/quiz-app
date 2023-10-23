# quiz-app


## Student Quiz Application
This Java Swing application allows students to select a quiz and interact with web-based quiz questions.

##Getting Started
To run the application, follow these steps:

Make sure you have Java installed on your system.

Open the project in your preferred Java IDE (e.g., NetBeans, Eclipse).

Locate the main class StudentQuizApp.java and run it.

The application will open. Select a quiz from the dropdown menu and click "Start Quiz". This will open a web page with the corresponding quiz questions.

Note: Please ensure that you have a working internet connection to access the web-based quiz questions.

##Troubleshooting
If you encounter issues with opening the web page, make sure that you have a default web browser set up on your system.

If you're running into platform-specific issues, please let the developer know. Some environments may not support opening web pages directly from a Java application.

##Adding Custom Quiz (jj.html)
If you have a custom quiz (e.g., jj.html) that you'd like to use, follow these steps:

Place the jj.html file in the web directory of your project.

Note: If the web directory doesn't exist, create it inside your project directory.

In the StudentQuizApp.java file, make sure to update the openQuizWebPage method to reflect the correct file name (jj.html) and quiz name.
