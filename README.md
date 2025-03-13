# Mpesa B2C Transaction Service

## Overview
This project is a Spring Boot-based application that integrates with Safaricom's Mpesa B2C API to process transactions and send SMS notifications upon completion.

## Features
- Process Mpesa B2C transactions
- Handle transaction callbacks
- Store transactions in an in-memory database
- Send SMS notifications upon successful or failed transactions
- Secure API keys using environment variables
- Expose REST endpoints for initiating transactions and handling callbacks

## Prerequisites
Before running the application, ensure you have the following installed:
- Java 21
- Maven
- Docker (optional, for containerization)

## Setup Instructions

### 1. Clone the Repository
```sh
    git clone https://github.com/Atieno-Ouma/mpesa-b2c-microservice-test.git
    cd mpesa-b2c-microservice-test
```

### 2. Configure Environment Variables
Create a `.env` file in the project's root directory and add the following variables:
```sh
    SAFARICOM_CONSUMER_KEY=your_consumer_key
    SAFARICOM_CONSUMER_SECRET=your_consumer_secret
    SAFARICOM_SMS_URL=https://api.safaricom.co.ke/safaricom/messaging/v1/text/single
    SAFARICOM_TOKEN_URL=https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials
```

### 3. Build and Run the Application
```sh
    mvn clean install
    mvn spring-boot:run
```

The application should start on `http://localhost:8080`.

### 4. Running with Docker (Optional)
```sh
    docker build -t mpesa-b2c-microservice-test .
    docker run -p 8080:8080 --env-file .env mpesa-b2c-microservice-test
```

## API Endpoints
### 1. Initiate B2C Transaction
**Endpoint:** `POST /api/mpesa/b2c/pay`
- **Request Body:**
```json
{
  "phoneNumber": "2547********",
  "amount": 500
}
```
- **Response:**
```json
{
  "success": true,
  "transactionReference": "TXN123********"
}
```

### 2. Handle Mpesa Transaction Callback
**Endpoint:** `POST /api/mpesa/b2c/transaction-result`
- **Request Body:** JSON response from Mpesa API
- **Response:**
```json
{"message": "Callback received successfully"}
```

## Assumptions Made
- Transactions are assumed to be **successful** unless indicated otherwise in the callback response.
- SMS notifications are sent **only** after processing transactions.
- The application uses an **in-memory database** (H2) for testing.
- API credentials are **stored in `.env`** and loaded using `@Value` in Spring Boot.

## Testing
Run unit tests using:
```sh
    mvn test
```

## Future Improvements
- Implement database persistence (e.g., PostgreSQL/MySQL).
- Improve error handling and logging.
- Add authentication (e.g., JWT) for securing API endpoints.

