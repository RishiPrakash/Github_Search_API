# Github_Search_API
An Java+SpringBoot based REST API to fetch users details based on their first name, last name and location. This API honours the GitHub search API rate limits applied.

# Objectives
Identify a Developer’s profile on GitHub, when following attributes are provided:
1. First Name
2. Last Name
3. Location
### Once the profile has been identified, extract following attributes from the profile:
1. Public profile details
2. Name of repositories where they are contributing
3. No. of commits made by that developer on those repositories

#### As GitHub applies rate limits to the use of their API, gathering of the data is done in such a way that application operates in the given rate limits as specified by the host.

#### Q. What is Rate Limiting?
Any API which is being used by consumers needs to be equally available for all the users, hence excessive use of APIs by any/some consumers can bring the server productivity down and can hamper the availability of API by other consumers.
To ensure this does not happen, a throttling is applied at the server side which is known as rate_limiting, this defines the number of hits allowed by any consumer to the API in a given period of time. By restricting the access, the server availability is ensured.
To honour this rate_limiting, at client side also we put throttling at the request rate,if client surpasses the API rate limits, it might result in 403 error response and then on successive abuses client might be blocked as Server may take these requests as DDoS attacks.
To ensure this does not happen, we apply client side throttling.
There could be a number of ways to implement the same definition in code. 
#### Q. How I have implemented Rate_limiting
GitHub APIs gives two ways to access it,
1. With authentication
2. Without authentication
For both the ways it defines a rate limit per minute, for example with authentication it permits 30 requests per minute.
I have used guava rateLimiter APIs which provides token, which are needed to be acquired by any task before they can be submitted to executor. We provide a rate at which these permits can be given to tasks.
Example
final RateLimiter rateLimiter = RateLimiter.create(2.0); // rate is "2 permits per second"
This rateLimiter will be used to distribute tokens at a defined rate.
void submitTasks(List<Runnable> tasks, Executor executor) { for (Runnable task : tasks) {
rateLimiter.acquire(); // may make the task wait before it can be given to executor
executor.execute(task); }}
#### Q. How I have defined this rate in the API submitted?
API first makes a call to github rate limit API to get what is the remaining rate limit quota, that rateLimit quota is then broken down per second and then divided by two( because each time to calculate information for each user, we are making two API calls). Using this number, we are defining the permissible rate of token generation.
  
## How to Install and Execute  
### Install 
#### Requirements
1. Eclipse/IDE
2. Java 8+
3. Maven tool


1. Import Project into eclipse
2. Run Maven>Update Project
3. Run Maven clean install
a. Test cases are set up to run at build time
b. 2 out of 3 test cases (One positive and one negative test case will run)
c. 3rd test case which takes “sample input provided with the BlueOptima sample
task” as input is commented as it may take a good amount of time to execute all
the inputs ,can be uncommented if required
4. Right click on RatelimiterApplication.java and do Run as > Java Application
a. Default server port is set as 9080, this can be changed accordingly
b. Project works with/without authentication.token to connect with GitHub API, if
required value of it can be updated in application.properties
### Execution 
#### Requirements
1. Postman(like tool) Http method : POST
URI : ​http://localhost:9080/api/users
1. Set content-type as application/json
2. Put following in Body

```
[{
"firstName"​:​"Chunky"​,
"lastName"​:​"Garg"​, 
"location"​:​"Gurgaon"
}​]
``` 
3. Hit send and you will get result like
```
{
​"public_information"​: {
​"id"​: ​7038167​,
​"login"​: ​"chunky-garg"​,
​"url"​: ​"https://api.github.com/users/chunky-garg"​,
}, ​"avatar_url"​: ​"https://avatars1.githubusercontent.com/u/7038167?v=4"
​"contributed_to"​: [ {
​"repoName"​: ​"GIT-demo"​,
}, ​"totalCommit"​: ​14
{
​"repoName"​: ​"test"​,
}, ​"totalCommit"​: ​2 {
​"repoName"​: ​"webkiosk"​,
}, ​"totalCommit"​: ​5
{
​"repoName"​: ​"Repo1in1"​,
}, ​"totalCommit"​: ​1 {
​"repoName"​: ​"clientapp"​,
} ​"totalCommit"​: ​8
] }
```
#### Eclipse Console can be looked upon to see a summary of execution, which describes how many records were submitted and how many of them are now successful and which one of them were not.An exmple of that is attached in the repo root folder.
