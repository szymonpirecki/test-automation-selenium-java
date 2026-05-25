# Selenium Test Automation Framework (Java)

End-to-end test automation framework for a PrestaShop e-commerce application, built with Selenium WebDriver and JUnit 5.

## Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 17 | Language |
| Selenium WebDriver | 4.21 | Browser automation |
| JUnit 5 | 5.11.3 | Test runner |
| AssertJ | 3.26.3 | Fluent assertions |
| Lombok | 1.18.34 | Boilerplate reduction |
| Logback | 1.5.12 | Logging |
| SnakeYAML / Jackson | 2.3 / 2.18 | Config parsing |
| Java Faker | 1.0.2 | Test data generation |
| Maven | 3.x | Build tool |

> Driver management is handled automatically by Selenium Manager (built into Selenium 4.6+).

## Architecture

```
src/main/java
├── configuration/       # YAML config loading, browser & environment setup
├── model/               # Domain model (Basket, BasketLine, Product, Order, User)
├── pages/               # Page Object Model — one class per page/component
│   ├── base/            # BasePage with shared waits and helpers
│   ├── basket/
│   ├── checkout/
│   ├── product/
│   └── user/
├── flows/               # User flow layer — fluent API wrapping page interactions + assertions
│   ├── base/            # BaseFlows with page factory helper
│   ├── basket/
│   ├── checkout/
│   ├── product/
│   └── user/
├── providers/           # URL constants
└── utils/               # Data generation (UserFactory)

src/test/java
├── base/                # TestBase — driver lifecycle (@BeforeEach / @AfterEach)
├── basketTests/
├── categoryTests/
├── checkoutTests/
├── filterTests/
└── searchTests/
```

The framework uses a three-layer design:
- **Pages** — WebElement locators and low-level browser interactions
- **Flows** — business-level user flows and assertions, returning `this` for fluent chaining
- **Tests** — readable, scenario-style test methods composing flows

Domain models (e.g. `Basket`, `BasketLine`) are built from page state and compared with AssertJ's `usingRecursiveComparison`, so tests verify business data rather than raw HTML.

## Running Tests

### Prerequisites

- Java 17+
- Maven 3.6+
- Chrome / Firefox / Edge installed

### Run all tests

```bash
mvn test
```

### Run a specific test class

```bash
mvn test -Dtest=BasketTest
```

### Change browser

Edit `src/main/resources/configFile.yaml`:

```yaml
browserSettings:
  browserName: firefox   # chrome | firefox | edge
  browserHeadless: true
```

### Change environment

The `configFile.yaml` `environment` key selects which block under `environments:` is loaded. Currently `test` points to a hosted PrestaShop instance.

## Test Suites

| Suite | Description |
|-------|-------------|
| `BasketTest` | Add products, verify basket contents and counter, remove items |
| `CheckoutTest` | Full purchase flow: login → product → basket → checkout → order verification |
| `CategoryTest` | Category headers, product counts, filter panel presence, subcategory navigation |
| `FilterTest` | Price range filter applied and cleared, results within bounds |
| `SearchTest` | Search by keyword, autocomplete suggestions |

## Configuration

All test parameters are defined in `src/main/resources/configFile.yaml` and loaded as `System.properties` at runtime. Nested YAML keys are flattened with `-` as separator (e.g. `checkoutTests-user-email`).

### Credentials

Test account credentials are **not stored in the repository**. Supply them via environment variables before running:

```bash
export TEST_USER_EMAIL=your.test.user@example.com
export TEST_USER_PASSWORD=your_password
```

See `.env.example` for the full list. In CI these are injected as repository secrets (see `.github/workflows/ci.yml`).
