# ToDoApp
## Description
This is simple web application which enables all users to create an account and logged users to represent their projects with _dashboards_.
 Projects are separated on smaller _tasks_.
Each task belongs to specific column (named _task state_ in this application) depending on its current state (to do, in progress, done,...).  

Logged user can:  
- create, update, delete his/her dashboard;
- create, update, delete columns (task states) on one of his/her dashboards;
- create, update (including changing task state), delete tasks inside one of task states.

Backend of the application is developed in **Spring Boot** framework. Application uses SQL database **SQLite** to store data.
## Installation
1. Clone or download the project and unpack the folder. Navigate to project where pom.xml file is. Start the application using Maven command:
`mvn spring-boot:run`
2. Open Postman or some other advanced REST client and connect to some of the bellow specified REST APIs. 
Application server is running on local machine on port 8080.
3. If you want to log in already existing account, you can type in one of the following credentials for username and password, respectively:  
`username1`  `password1`  
`username2`  `password2`
## REST api
| Method | URL |Request Body (JSON)| Description |
|--------|-----|-------------------|-------------|
|POST  |`/users/login`|userLogin*|Login to application.|
|GET  |`/users/logout`||Logout from application.|
|POST  |`/users/register`|userRegister*|Creating an account.|
|GET  |`/users/dashboards`||Retrieving dashboards of logged user.|
|GET  |`/users/dashboards/{dashboardId}`<br> _id_-type Numeric (integer)||Retrieving dashboard (of logged user) with specified ID. Retrieved data contain all task states of dashboard and tasks inside them.|
|GET  |`/dashboards/{dashboardId}/task-states`<br> _id_-type Numeric (integer)||Retrieving task states of dashboard (of logged user) with specified ID.|
|GET  |`/dashboards/{dashboardId}`<br> _id_-type Numeric (integer)||Retrieving dashboard with specified ID.|
|POST  |`/users/dashboards`|dashboard*|Creating new dashboard.|
|PUT  |`/dashboards/{dashboardId}`<br> _id_-type Numeric (integer)|dashboard**|Updating dashboard (of logged user) with specified ID.|
|DELETE  |`/dashboards/{dashboardId}`<br> _id_-type Numeric (integer)||Deleting dashboard (of logged user) with specified ID.|
|GET  |`/task-states/{taskStateId}/tasks`<br> _id_-type Numeric (integer)||Retrieving tasks of task state (of logged user) with specified ID.|
|GET  |`/task-states/{taskStateId}`<br> _id_-type Numeric (integer)||Retrieving task state (of logged user) with specified ID.|
|POST  |`/task-states`|task-state*|Creating new task state on dashboard with ID specified in JSON object.|
|PUT  |`/task-states/{taskStateId}`<br> _id_-type Numeric (integer)|task-state**|Updating task state (of logged user) with specified ID.|
|DELETE  |`/task-states/{taskStateId}`<br> _id_-type Numeric (integer)||Deleting task state (of logged user) with specified ID.|
|GET  |`/tasks/{taskId}`<br> _id_-type Numeric (integer)||Retrieving task (of logged user) with specified ID.|
|POST  |`/tasks`|*task|Creating new task with task state whose ID is specified in JSON object.|
|PUT  |`/tasks/{taskId}`<br> _id_-type Numeric (integer)|**task|Upading task (of logged user) with specified ID.|
|DELETE  |`/tasks/{taskId}`<br> _id_-type Numeric (integer)||Deleting task (of logged user) with specified ID.|

#### Formats of JSON objects required in request body
*userLogin

| Field | Type |
|-------|------|
|username|String|
|password|String|  

*userRegister

| Field | Type |
|-------|------|
|username|String|
|password|String|
|repeatedPassword|String|
|name|String|
|lastName|String|  

*dashboard

| Field | Type |
|-------|------|
|title|String|

**dashboard

| Field | Type |
|-------|------|
|id|Numeric (integer)|
|title|String|  

*task-state

| Field | Type |
|-------|------|
|title|String|
|dashboardId|Numeric (integer)|

**task-state

| Field | Type |
|-------|------|
|id|Numeric (integer)|
|title|String| 

*task

| Field | Type |
|-------|------|
|title|String|
|description|String|
|taskStateId|Numeric (integer)|

**task

| Field | Type |
|-------|------|
|id|Numeric (integer)|
|title|String|
|description|String|
|taskStateId|Numeric (integer)|

