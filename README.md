# selenium-prestashop-java

![Tests](https://github.com/szymonpirecki/test-automation-playwright-ts/actions/workflows/ci.yml/badge.svg)

End-to-end test automation framework for a PrestaShop demo e-commerce application, built as a QA Engineer portfolio project with Selenium WebDriver, JUnit 5, and a three-layer Page Object + Steps architecture.

**Architecture**
- Three-layer design: Pages (locators and raw interactions), Steps (business journeys, fluent chaining, assertions), Tests (readable scenario methods)
- Domain model assertions — page state is mapped into domain objects (`Basket`, `BasketLine`, `OrderDetails`) and compared with AssertJ `usingRecursiveComparison()`; tests verify business data rather than raw element text
- Custom AssertJ `RecursiveComparisonConfiguration` handles `BigDecimal` precision
- Driver management handled automatically by Selenium Manager (built into Selenium 4.6+) — no manual chromedriver download

**Test execution**
- Runs on Chrome or Firefox, selected via YAML config or CLI `-D` flags
- Thread-safe driver: `DriverManager` wraps `WebDriver` in a `ThreadLocal`, enabling safe parallel execution with Maven Surefire `forkCount`
- CLI `-D` flags override any YAML value, so the same config works locally and in CI without code changes

**Reporting**
- Surefire XML reports collected after every run
- Screenshots and page source captured automatically on failure

**Configuration**
- YAML-first: all environment URLs, test data, and browser settings live in `configFile.yaml`
- The `environment` key selects which environment block is active
- Test credentials are kept outside version control and supplied as environment variables

**Deployment**
- GitHub Actions pipeline (push/PR to `master`) runs a single job:
    - Checkout → Java 17 setup → install Chrome → run tests headless → upload artifacts
- `TEST_USER_EMAIL` and `TEST_USER_PASSWORD` injected from repository secrets
- Surefire XML reports, screenshots, and page sources uploaded as separate artifacts on every run

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java | 17 or later |
| Maven | 3.6 or later |
| Browser | Chrome or Firefox installed locally |

---

## Setup

```bash
git clone https://github.com/szymonpirecki/selenium-prestashop-java.git
cd selenium-prestashop-java
mvn clean install -DskipTests
cp .env.example .env
```

Test account credentials are **not stored in the repository**. Supply them as environment variables before running checkout tests — fill in real values:

```bash
export TEST_USER_EMAIL=your.test.user@example.com
export TEST_USER_PASSWORD=your_password
```

See `.env.example` for the full template. If the variables are missing, `CheckoutBase` fails fast with a descriptive error before the browser even opens.

---

## Running tests

### Run all tests

```bash
mvn test
```

### Run a single suite

```bash
mvn test -Dtest=BasketTest
```

### Other options

**Override browser settings via CLI** — any browser property defined in `configFile.yaml` can be overridden with a `-D` flag:

```bash
# Run headless on Firefox
mvn test -DbrowserName=firefox -DbrowserHeadless=true

# Run with a longer explicit wait (e.g. slow environment)
mvn test -DexplicitWaitTimeout=20
```

**Parallel execution** — fork count is set to `2` in `pom.xml` (Maven Surefire). To increase parallelism:

```bash
mvn test -DforkCount=4
```

---

## Test suites

| Suite | Tests | What is verified |
|---|---|---|
| `BasketTest` | 3 | Add a single product (popup content + cart counter); add 10 random products and verify full basket state; remove items one by one and verify running total |
| `CheckoutTest` | 1 | Full purchase flow: login → navigate category → add product → checkout with invoice address → verify order in account history |
| `CategoryTest` | 2 | Title, product count and filter panel for every top-level category; same checks for all subcategories |
| `FilterTest` | 1 | Apply price range filter, assert all results are within bounds, clear filter, assert full count restored |
| `SearchTest` | 2 | Search by a randomly selected product name, assert all results match; type a keyword and assert autocomplete suggestions |

---

## Tech stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Selenium WebDriver | 4.21 | Browser automation |
| JUnit 5 | 5.11.3 | Test runner & parallel execution |
| AssertJ | 3.26.3 | Fluent assertions with recursive comparison |
| Lombok | 1.18.34 | Boilerplate reduction |
| Logback | 1.5.12 | Structured logging |
| SnakeYAML / Jackson | 2.3 / 2.18 | YAML config parsing |
| Java Faker | 1.0.2 | Test data generation |
| Maven Surefire | 3.5.1 | Build & parallel test execution |

---

## Configuration

All test parameters are defined in `src/main/resources/configFile.yaml`.

```yaml
environment: test         # selects which block under `environments:` is active

browserSettings:
  browserName: chrome     # chrome | firefox
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

**Priority (highest → lowest):**
1. CLI `-D` flag: `mvn test -DbrowserName=firefox`
2. Value defined in `configFile.yaml`

---

## Failure artifacts

On test failure, `TestBase` automatically captures:

| Artifact | Path |
|---|---|
| Screenshot | `target/screenshots/<testName>_FAILED_<timestamp>.png` |
| Page source | `target/page-sources/<testName>_FAILED_<timestamp>.html` |

Both paths are uploaded as CI artifacts (see `.github/workflows/ci.yml`) and retained for 7 days.

---

## Project structure

```
.
├── .github/
│   └── workflows/
│       └── ci.yml                       # GitHub Actions: Java setup, headless test run, artifact upload
├── src/
│   ├── main/java/
│   │   ├── configuration/
│   │   │   ├── handler/                 # BrowserHandler, DriverManager (ThreadLocal), EnvironmentHandler, YamlReader
│   │   │   ├── model/                   # YamlModel + per-suite config POJOs (CheckoutConfig, BasketConfig, …)
│   │   │   └── assertJConfig/           # Custom AssertJ RecursiveComparisonConfiguration (BigDecimal precision)
│   │   ├── model/
│   │   │   ├── basket/                  # Basket, BasketLine, BasketPopUp + Queryable interfaces
│   │   │   ├── order/                   # OrderDetails + Queryable interface
│   │   │   ├── testdata/                # Test input models (Credentials, Address, CheckoutTestData, …)
│   │   │   └── user/                    # User, SocialTitle
│   │   ├── pages/
│   │   │   ├── base/                    # BasePage — explicit waits, sendKeys, BigDecimal parsing, select helpers
│   │   │   ├── basket/                  # BasketPage, BasketPopUpPage, BasketLineComponent
│   │   │   ├── checkout/                # Checkout address/shipping/payment pages, OrderConfirmationPage
│   │   │   ├── home/                    # HeaderPage
│   │   │   ├── login/                   # LogInPage
│   │   │   ├── product/                 # CategoryPage, ProductGridPage, ProductPage, ProductFilterPage
│   │   │   └── user/                    # AccountPage, AddressPage, OrderHistoryPage, OrderDetailsPage
│   │   ├── steps/
│   │   │   ├── base/                    # BaseSteps — page factory helper (at()), random helpers
│   │   │   ├── basket/                  # BasketSteps
│   │   │   ├── checkout/                # CheckoutSteps
│   │   │   ├── product/                 # ProductSteps, FilterSteps, SearchSteps
│   │   │   └── user/                    # LoginSteps, AccountSteps
│   │   ├── providers/                   # UrlProvider, TestDataProvider
│   │   └── utils/
│   │       ├── ScreenshotUtil           # Auto-capture screenshot + page source on failure
│   │       └── dataGeneration/          # UserFactory (Java Faker)
│   ├── test/java/
│   │   ├── base/                        # TestBase — @BeforeEach driver init, @AfterEach quit, JUnit5 TestWatcher
│   │   ├── basketTests/                 # BasketBase + BasketTest
│   │   ├── categoryTests/               # CategoryBase + CategoryTest
│   │   ├── checkoutTests/               # CheckoutBase + CheckoutTest
│   │   ├── filterTests/                 # FilterBase + FilterTest
│   │   └── searchTests/                 # SearchBase + SearchTest
│   └── main/resources/
│       └── configFile.yaml              # Environments, test data, browser settings
├── .env.example                         # Committed env template — copy to .env and fill in
├── .gitignore
├── pom.xml                              # Maven build, dependencies, Surefire parallel config
└── README.md
```