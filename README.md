API Automation Framework – RestAssured, TestNG, Allure

Overview
This project is a production-style API automation framework built in Java using RestAssured, TestNG, and Allure reporting. It is designed to validate the Restful Booker API (public CRUD and authentication playground) and to demonstrate how a mature, reusable automation framework can be built and integrated into enterprise CI/CD pipelines.​

Business Value

Reduces regression testing effort by automating end-to-end booking flows (create, read, update, delete).

Provides fast feedback on API health with clear reporting, enabling earlier defect discovery and lower cost of fixes.

Offers a reusable core framework that can be plugged into multiple services, minimizing duplicated effort across projects.

Demonstrates engineering capability to design automation solutions suitable for large teams and long-term maintenance.

Key Capabilities

Multi-module Maven architecture (core framework + API-specific test module).

Environment-based execution (staging, QA, etc.) controlled via simple parameters.

Dynamic authentication token generation against Restful Booker’s /auth endpoint, avoiding brittle hard-coded tokens.​

Rich Allure reporting with request/response logging for transparent defect analysis.

Clear separation between framework code, service layer, and tests for easier onboarding and maintenance.

High-Level Architecture

Parent Maven project

core-framework

Base classes for API interaction and request specification building.

Centralized configuration management and utilities.

restfulbooker-tests

Models, service layer, and TestNG test suites specifically for the Restful Booker API.

Core Framework Module (core-framework)

Base Layer

BaseAPI

Common setup for RestAssured.

Shared behavior used by all service classes.

SpecBuilder

getBaseSpec():

Sets base URI from configuration.

Sets JSON content type.

Registers Allure RestAssured filter so every request and response is logged into reports.

getSpecWithCookieAuth(String token):

Accepts a token and builds a spec with a Cookie header, allowing secure update/delete operations without exposing credentials.

getSpecWithBearerAuth():

Uses bearer token from configuration for services that require Authorization headers.

Configuration

ConfigManager

Thread-safe singleton responsible for loading environment-specific properties.

Supports selecting environment via command-line parameter (for example, -Denv=staging).

Builds the properties path like restfulbooker/{env}.properties and loads it from the classpath.

Exposes methods such as getBaseURI(), getUsername(), getPassword(), getCookieToken(), and getBearerToken().

Utilities

JsonUtils

Handles serialization and deserialization of HTTP responses to strongly typed models.

AssertionUtils

Centralized, reusable assertions for status codes and payload validations, improving consistency across tests.

TestDataGenerator

Uses randomized data (for example with JavaFaker) to generate realistic booking payloads, increasing coverage and reducing data collisions.

Test Module (restfulbooker-tests)

Models

Auth

Represents username/password payload for authentication.

Booking / BookingDates / BookingResponse

Models for create/update booking requests and responses.

TokenResponse

Represents the token returned from the /auth endpoint, enabling typed token handling.

Service Layer

AuthService

Wraps calls to the /auth endpoint.

Provides getToken(username, password) to request new tokens as needed.

BookingService

Extends BaseAPI and uses SpecBuilder for consistent specification setup.

createBooking(Booking booking): POST /booking.

getBookingById(int bookingId): GET /booking/{id}.

updateBooking(int bookingId, Booking booking): PUT /booking/{id} using dynamic cookie token.

deleteBooking(int bookingId): DELETE /booking/{id} using dynamic cookie token.

Generates a token once per test run (or on demand) via AuthService and reuses it, simulating a realistic authenticated client and avoiding expired test tokens.​

Test Layer

BaseTest

Uses @BeforeClass to initialize ConfigManager, AuthService, and BookingService.

Logs environment and base URI at the start of each test suite, helping traceability in reports.

BookingTests

Validates entire CRUD lifecycle:

Create booking with randomized data.

Retrieve booking by ID and verify fields.

Update booking using dynamic token-based authentication.

Delete booking and optionally verify deletion.

Uses AssertionUtils and JsonUtils to keep test code concise and readable.

Technology Stack

Java 11+

Maven 3.6+

RestAssured (API client)

TestNG (test framework)

Allure 2 (reporting)

Jackson (JSON parsing)

JavaFaker (test data generation)

Environments and Configuration

Environment Properties

Location: restfulbooker-tests/src/main/resources/restfulbooker/

Example: staging.properties

base.uri=https://restful-booker.herokuapp.com

username=admin

password=password123

cookie.token=fallback-token

bearer.token=fallback-token

Environment Selection

Default environment is staging.

Executions can be switched via Maven parameter:

mvn clean test -Denv=staging

mvn clean test -Denv=qa

Production-Readiness Considerations

Multi-module design allows extracting core-framework as a shared library and reusing it across multiple services.

Configuration is externalized and driven by environment, aligning with 12-factor app principles.

Allure integration ensures every API interaction is captured and visible, supporting rapid root-cause analysis.

Dynamic token generation reflects how real systems handle authentication and reduces the operational overhead of maintaining test credentials.​

The framework is easily hooked into continuous integration tools (Jenkins, GitHub Actions, Azure DevOps) for automated nightly or pipeline runs, with Allure reports archived as artifacts.

How to Run

From project root:

Clean and build all modules:

mvn clean install

Run full test suite in staging environment:

mvn clean test -Denv=staging

Run tests only for the restfulbooker-tests module:

cd restfulbooker-tests

mvn clean test -Denv=staging

Allure Reporting

After running tests, allure-results are generated under restfulbooker-tests:

Generate and view a live report:

allure serve restfulbooker-tests/allure-results

This command compiles the results into an HTML report and opens it in a browser, showing:

Overview of passed/failed/broken tests.

Suites grouped by feature and class.

Timeline of executions.

Detailed request and response for each API call (method, URL, headers, payload, and body).

Usage Scenarios for HR and Hiring Managers

Demonstrates ability to design frameworks, not just scripts, which is essential for senior QA and SDET roles.

Shows comfort with modern Java-based test stacks and industry-standard tooling (RestAssured, TestNG, Allure).

Reflects understanding of real-world concerns like environment management, authentication, and reporting.

Indicates readiness to lead or contribute to building organization-wide automation standards.

How This Project Can Be Extended

Add new API domains by creating additional test modules reusing core-framework.

Integrate with CI/CD for fully automated regression runs with Allure reports published for stakeholders.

Add performance checks (for example, basic response time thresholds) into AssertionUtils.

Integrate database or message queue validations for end-to-end coverage.

Contact
If you would like a walkthrough of the framework design, test strategy, or how this setup could be adapted to your organization’s APIs and pipelines, feel free to connect.
