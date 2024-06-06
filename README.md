## Hibernate Stale Data Issue Reproduction

This repository demonstrates a Hibernate stale data issue. Follow the steps below to reproduce the issue.

### Prerequisites

Ensure you have Java 21 installed on your machine.

### Steps to Reproduce

1. **Run the Java Application**

   Make sure you have Java 21 installed and run the Java application.

2. **Initiate Unordered Process**

   Send a GET request to the following endpoint. This request does not require any request body or request parameters.

   ```sh
   curl -X GET http://localhost:8080/api/initiate/unordered
   ```

3. **Delegate Unordered Process**

   Send a GET request to the following endpoint:

   ```sh
   curl -X GET http://localhost:8080/api/delegate/unordered
   ```

4. **Sign Unordered Process**

   Send a GET request to the following endpoint:

   ```sh
   curl -X GET http://localhost:8080/api/sign/unordered
   ```

### Observing the Issue

When making the first request to initiate, notice that:

- The current state is correct.
- The next state is also correct.
- However, the state fetched from the database is the stale data, which was before the saving operation of the next state.

This issue is solved in the next iteration when calling the API for delegation. But when calling the sign API:

- There will be two next state operations:
  1. First, it will change the state from "unordered delegated signatory" to "unordered e-mail" state.
  2. In the next state operation, it will change the state to "unordered e-mail sent to all signatories sign" state.
  
- The state change in the first iteration is correct. However, in the second stage, the current state fetched from the database is stale.
- Because of this, the state, which was supposed to transition to "all signatory signed" state, returns back to "unordered e-mail" state.
- This discrepancy is due to the current state fetched from the database being stale, causing unexpected behavior in the state transition process.
  
This behavior demonstrates the Hibernate stale data issue.
