# Selenium Test Automation Framework (Java)

End-to-end test automation framework for a PrestaShop e-commerce application, built with Selenium WebDriver, JUnit 5, and a three-layer Page Object + Steps architecture.

## Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 17 | Language |
| Selenium WebDriver | 4.21 | Browser automation |
| JUnit 5 | 5.11.3 | Test runner & parallel execution |
| AssertJ | 3.26.3 | Fluent assertions with recursive comparison |
| Lombok | 1.18.34 | Boilerplate reduction |
| Logback | 1.5.12 | Structured logging |
| SnakeYAML / Jackson | 2.3 / 2.18 | YAML config parsing |
| Java Faker | 1.0.2 | Test data generation |
| Maven Surefire | 3.5.1 | Build & parallel test execution |

> Driver management is handled automatically by Selenium Manager (built into Selenium 4.6+). No manual chromedriver download needed.

---

## Architecture

```
src/main/java
├── configuration/
│   ├── handler/         # BrowserHandler, DriverManager (ThreadLocal), EnvironmentHandler, YamlReader
│   ├── model/           # YamlModel + per-suite config POJOs (CheckoutConfig, BasketConfig, …)
│   └── assertJConfig/   # Custom AssertJ RecursiveComparisonConfiguration (BigDecimal precision)
│
├── model/
│   ├── basket/          # Basket, BasketLine, BasketPopUp + Queryable interfaces
│   ├── order/           # OrderDetails + Queryable interface
│   ├── testdata/        # Test input models (Credentials, Address, CheckoutTestData, …)
│   └── user/            # User, SocialTitle
│
├── pages/
│   ├── base/            # BasePage — explicit waits, sendKeys, BigDecimal parsing, select helpers
│   ├── basket/          # BasketPage, BasketPopUpPage, BasketLineComponent
│   ├── checkout/        # CheckoutAddressPage, CheckoutShippingPage, CheckoutPaymentPage,
│   │                    # OrderConfirmationPage, DeliveryOptionComponent
│   ├── home/            # HeaderPage
│   ├── login/           # LogInPage
│   ├── product/         # CategoryPage, ProductGridPage, ProductPage,
│   │                    # ProductFilterPage, ProductMiniatureComponent
│   └── user/            # AccountPage, AddressPage, OrderHistoryPage,
│                        # OrderDetailsPage, OrderLineComponent, OrderStatusLineComponent
│
├── steps/
│   ├── base/            # BaseSteps — page factory helper (at()), random helpers
│   ├── basket/          # BasketSteps
│   ├── checkout/        # CheckoutSteps
│   ├── product/         # ProductSteps, FilterSteps, SearchSteps
│   └── user/            # LoginSteps, AccountSteps
│
├── providers/           # UrlProvider, TestDataProvider
└── utils/
    ├── ScreenshotUtil   # Auto-capture screenshot + page source on failure
    └── dataGeneration/  # UserFactory (Java Faker)

src/test/java
├── base/                # TestBase — @BeforeEach driver init, @AfterEach quit, JUnit5 TestWatcher
├── basketTests/         # BasketBase + BasketTest
├── categoryTests/       # CategoryBase + CategoryTest
├── checkoutTests/       # CheckoutBase + CheckoutTest
├── filterTests/         # FilterBase + FilterTest
└── searchTests/         # SearchBase + SearchTest
```

### Design patterns

**Three-layer architecture**

| Layer | Responsibility |
|-------|---------------|
| **Pages** | WebElement locators and raw browser interactions. No assertions, no business logic. |
| **Steps** | Business-level user journeys. Return `this` for fluent chaining. Contain AssertJ assertions. |
| **Tests** | Readable, scenario-style methods composing steps. No direct WebDriver usage. |

**Domain model assertions** — page state is mapped into domain objects (`Basket`, `BasketLine`, `OrderDetails`) which are then compared with AssertJ `usingRecursiveComparison()`. Tests verify business data rather than raw element text.

**Thread-safe driver** — `DriverManager` wraps `WebDriver` in a `ThreadLocal`, enabling safe parallel test execution with Maven Surefire `forkCount`.

**YAML-first configuration** — all environment URLs, test data, and browser settings live in `configFile.yaml`. CLI `-D` flags override YAML values, so the same config works locally and in CI without code changes.

---

## Test Suites

| Suite | Tests | What is verified |
|-------|-------|-----------------|
| `BasketTest` | 3 | Add a single product (popup content + cart counter); add 10 random products and verify full basket state; remove items one by one and verify running total |
| `CheckoutTest` | 1 | Full purchase flow: login → navigate category → add product → checkout with invoice address → verify order in account history |
| `CategoryTest` | 2 | Title, product count and filter panel for every top-level category; same checks for all subcategories |
| `FilterTest` | 1 | Apply price range filter, assert all results are within bounds, clear filter, assert full count restored |
| `SearchTest` | 2 | Search by a randomly selected product name, assert all results match; type a keyword and assert autocomplete suggestions |

---

## Running Tests

### Prerequisites

- Java 17+
- Maven 3.6+
- Chrome, Firefox, or Edge installed locally

### Run all tests

```bash
mvn test
```

### Run a single suite

```bash
mvn test -Dtest=BasketTest
```

### Override browser settings via CLI

Any browser property defined in `configFile.yaml` can be overridden with a `-D` flag:

```bash
# Run headless on Firefox
mvn test -DbrowserName=firefox -DbrowserHeadless=true

# Run with a longer explicit wait (e.g., slow environment)
mvn test -DexplicitWaitTimeout=20
```

### Parallel execution

Fork count is set to `2` in `pom.xml` (Maven Surefire). To increase parallelism:

```bash
mvn test -DforkCount=4
```

---

## Configuration

All test parameters are defined in `src/main/resources/configFile.yaml`.

```yaml
environment: test         # selects which block under `environments:` is active

browserSettings:
  browserName: chrome     # chrome | firefox | edge
  browserHeadless: false
  maximizeWindow: true

environments:
  test:
    urls:
      home: http://...
    explicitWaitTimeout: 10
    shippingPrice: 7
    market: US
    currency: $
    searchTests:
      keyword: HUMMINGBIRD
    filterTests:
      minPrice: 13
      maxPrice: 15
      category: ACCESSORIES
    basketTests:
      category: ART
      productName: THE BEST IS YET POSTER
      productQuantity: 3
    checkoutTests:
      ...
```

### Credentials

Test account credentials are **not stored in the repository**. Supply them as environment variables before running checkout tests:

```bash
export TEST_USER_EMAIL=your.test.user@example.com
export TEST_USER_PASSWORD=your_password
```

See `.env.example` for the full template. If the variables are missing, `CheckoutBase` fails fast with a descriptive error before the browser even opens.

---

## Failure Artifacts

On test failure, `TestBase` automatically captures:

- **Screenshot** → `target/screenshots/<testName>_FAILED_<timestamp>.png`
- **Page source** → `target/page-sources/<testName>_FAILED_<timestamp>.html`

Both paths are uploaded as CI artifacts (see `.github/workflows/ci.yml`) and retained for 7 days.

---

## CI/CD (GitHub Actions)

The pipeline runs on every push and pull request to `master`.

```
Checkout → Java 17 setup → Install Chrome → Run tests (headless) → Upload artifacts
```

Secrets required in the repository:

| Secret | Description |
|--------|-------------|
| `TEST_USER_EMAIL` | Test account e-mail |
| `TEST_USER_PASSWORD` | Test account password |

Surefire XML reports, screenshots, and page sources are uploaded as separate artifacts on every run.
