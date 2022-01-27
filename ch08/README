= Taco Cloud - Chapter 8

Note that this is a WIP README. For now, it will tersely describe how to build,
test, and run the code in this folder.

There are three projects in this folder:

 - tacocloud : This is the same Taco Cloud project as in the previous chapter,
   altered to act as an OAuth2 resource server.
 - auth-server : An OAuth2 authorization server based on Spring Authorization
   Server (https://github.com/spring-projects-experimental/spring-authorization-server).
 - tacocloud-admin : An admin client to consume the API exposed by tacocloud,
   using tokens obtained from the auth-server.

== Running the Main Taco Cloud Application

Changed into tacocloud directory:

----
$ cd tacocloud
----

=== Testing

----
$ ./mvnw test
----

=== Building

----
$ ./mvnw package
----

=== Running

After building:

----
$ java -jar tacocloud/target/taco-cloud-0.0.8-SNAPSHOT.jar
----

Once the application is running, you can poke at the API using `curl` like this:

For the most part, this application will behave the same as it did in the previous
chapter's code. But certain API endpoints and HTTP methods will require a valid
authorization token in requests. A POST request to /api/ingredients, for example,
is secured by the resource server and will require an OAuth token.

== Running the Authorization Server

Changed into auth-server directory:

----
$ cd auth-server
----

=== Testing

----
$ ./mvnw test
----

=== Building

----
$ ./mvnw package
----

=== Running

After building:

----
$ java -jar target/auth-server-0.0.1-SNAPSHOT.jar
----

Or, optionally use the Spring Boot Maven plugin to run the app:

----
$ ./mvnw spring-boot:run
----

Then, you can manually interact with the authorization server by TODO:FINISH THIS

http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients
