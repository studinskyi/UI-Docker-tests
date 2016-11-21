package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestListener implements ITestListener {

    static final Logger logger = Logger.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext iTestContext) {
        //Configure logger
        PropertyConfigurator.configure("log4j.properties");
        logger.debug("debug_AAAAAAAAAAA");
        logger.info("info_AAAAAAAAAAA");
        logger.warn("warn_AAAAAAAAAAA");
        logger.error("error_AAAAAAAAAAA");
        String browserName = iTestContext.getCurrentXmlTest().getParameter("browserName");
        WebDriver driver = RemoteWebDriverFactory.createInstance(browserName);
        RemoteDriverManager.setWebDriver(driver);

        // ожидание перед использованием
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println("created webDriver for browser: " + browserName + " *" + getCurrenDateTimeString());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        RemoteDriverManager.closeDriver();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
        File screen = captureScreenshot((WebDriver) RemoteDriverManager.getDriver());

        String pathToFile = "d:\\QA_Hillel\\DockerTest\\" + "chrome_Success_" + tr.getMethod().getMethodName() + "_" + getCurrenDateTimeToNameFile() + ".png";
        //String pathToFile2 = "/home/ds/tmp/" + "chromeScreenshot" + randInt + "_" + getCurrenDateTimeToNameFile() + ".png";
        try {
            FileUtils.copyFile(screen, new File(pathToFile));
            //FileUtils.copyFile(screen, new File("/tmp/test/" + tr.getMethod().getMethodName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // сохранение скриншота при успешном тесте
        System.out.println("saved screenshot on Test Success: " + pathToFile + " *" + getCurrenDateTimeString());
    }


    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
        File screen = captureScreenshot((WebDriver) RemoteDriverManager.getDriver());

        String pathToFile = "d:\\QA_Hillel\\DockerTest\\" + "chrome_Failure_" + tr.getMethod().getMethodName() + "_" + getCurrenDateTimeToNameFile() + ".png";
        //String pathToFile2 = "/home/ds/tmp/" + "chromeScreenshot" + randInt + "_" + getCurrenDateTimeToNameFile() + ".png";
        try {
            FileUtils.copyFile(screen, new File(pathToFile));
            //FileUtils.copyFile(screen, new File("/tmp/test/" + tr.getMethod().getMethodName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // сохранение скриншота при упавшем при выполнении тесте
        System.out.println("saved screenshot on Test Failure: " + pathToFile + " *" + getCurrenDateTimeString());

        //        System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
        //        File screen = captureScreenshot((WebDriver) RemoteDriverManager.getDriver());
        //        try {
        //            FileUtils.copyFile(screen, new File("/tmp/test/" + tr.getMethod().getMethodName() + ".png"));
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
    }

    @Attachment
    public File captureScreenshot(WebDriver d) {
        File file = null;
        try {
            file = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);

        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }


    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public String getCurrenDateTimeToNameFile() {
        Date d = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        return formatDate.format(d);
    }

    public String getCurrenDateTimeString() {
        Date d = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return formatDate.format(d);
    }
}