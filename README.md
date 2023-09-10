# How TOTP works Demo
Demonstration of how TOTP authentication works.

# Prerequisites

- Java 17
- Gradle 7.6 or higher

# Running the demo

## Server
Run the server first. Change working directory to the cloned repository.
```shell
./gradlew :server:bootRun
```

## Client
Once the Server is running successfully, run the client in another terminal from the same directory as the server.
```shell
./gradlew :client:run
```
The client will make 5 authentication attempts every 60 seconds generating a new TOTP every time.
