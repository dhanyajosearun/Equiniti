# Technical Test #

## Welcome to the Equiniti Credit Services Tech Test ##
This technical test is for candidates wishing to apply for Software Engineering roles with Equiniti Credit Services. It is designed as a starting point for conversation, we do not expect a full solution to be delivered at the end of it (45-75mins). What we do want is for you to have some fun showing us what you can do and how you do it and hopefully spark some conversation about why you made some of your decisions. If you feel there is insufficient time, please just tell us what you would do if you had more time.

While this is a test we would prefer you to think of it as a collaborative exercise / pair programming, with you as the driving force.

You may use internet searches during the test. We all do it.

Your solution can contain dependencies to publicly available packages. 


## What do we want you to do? ##
You will be provided with a skeleton Java Servlet solution (implementation class is ApiServlet) which you can extend as you wish.  You will be required to build (maven) and deploy (java web server) the code.  The landing page in a web browser is http(s)://server:port/EqPancreditDeveloperTest/ and to see the initial API GET result you can visit http(s)://server:port/EqPancreditDeveloperTest/ApiServlet.

We want you to create a REST API for a simple data set that we will provide (**resources/data.json**).

The REST API should allow for the following actions on the resource:
 
- create
- read
- update 
- delete 

We would like to see data access to the json file, no need for databases.


## Other Considerations ##

You should follow industry best practices and consider: 

- separation of concerns
- testability
- security (the API will be publicly accessible)
- scalability 
- performance

Ideally at the end of this process your solution will be structured, readable and maintainable. 


## Finally ##
Please ask for help if you get stuck.

Good luck and try to have fun.
