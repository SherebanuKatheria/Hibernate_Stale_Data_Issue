## Hibernate Stale Data Issue Reproduction

This repository demonstrates a Hibernate stale data issue. Follow the steps below to reproduce the issue.

### Prerequisites

Ensure you have Java 21 installed on your machine.

### Steps to Reproduce

1. **Run the Java Application**

   Make sure you have Java 21 installed and run the Java application.

2. **Send API request**

   Send a GET request to the following endpoint. This request does not require any request body or request parameters.

   ```sh
   curl -X GET http://localhost:8080/api
   ```

### Observing the Issue

After making the request, notice the console logs. There you can see that the hibernate is updating the entity in database but when fetched it's returning the old data only.
  
This behavior demonstrates the Hibernate stale data issue.
