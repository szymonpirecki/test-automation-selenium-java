package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ScreenshotUtil {

    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtil() {}

    public static void captureScreenshot(WebDriver driver, String testName, String suffix) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = String.format("%s_%s_%s.png", testName, suffix, LocalDateTime.now().format(TIMESTAMP));
            Path destPath = Paths.get("target/screenshots", fileName);
            Files.createDirectories(destPath.getParent());
            Files.copy(srcFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Screenshot saved: {}", destPath);
        } catch (Exception e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    public static void capturePageSource(WebDriver driver, String testName, String suffix) {
        try {
            String fileName = String.format("%s_%s_%s.html", testName, suffix, LocalDateTime.now().format(TIMESTAMP));
            Path destPath = Paths.get("target/page-sources", fileName);
            Files.createDirectories(destPath.getParent());
            Files.writeString(destPath, driver.getPageSource());
            log.info("Page source saved: {} | URL: {}", destPath, driver.getCurrentUrl());
        } catch (Exception e) {
            log.error("Failed to capture page source: {}", e.getMessage());
        }
    }
}
