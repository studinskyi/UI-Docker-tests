package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.RemoteDriverManager;

import java.util.concurrent.TimeUnit;

public class UpdateIssuePage {

    String issueKey = "";
    private WebDriver driver;


    public UpdateIssuePage() {
        this.driver = RemoteDriverManager.getDriver();;
    }

    public void updateReporter() {
        driver.get("http://soft.it-hillel.com.ua:8080/browse/" + issueKey);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//*[@id='issue_summary_reporter_katherinebilous']")).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//*[@id='reporter-field']")).sendKeys(Keys.DELETE);

        driver.findElement(By.xpath("//*[@id='reporter-field']")).sendKeys("a.a.piluck2 ", Keys.ENTER);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void updatePriority() {
        driver.get("http://soft.it-hillel.com.ua:8080/browse/" + issueKey);
        WebElement priority = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='priority-val']")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        priority.click();
        WebElement priorityChange = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='priority-field']")));

        priorityChange.clear();

        priorityChange.sendKeys("Low", Keys.ENTER, Keys.ENTER);

        // TODO assert priority low

    }

    public void getIssueKey() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        issueKey = driver
                .findElement(By.xpath("//*[@id='aui-flag-container']/div/div/a"))
                .getAttribute("data-issue-key");
        System.out.println(issueKey);
    }

    public void addComment() {
        driver.get("http://soft.it-hillel.com.ua:8080/browse/" + issueKey);
        WebElement comment = driver.findElement(By.xpath("//*[@id='comment-issue']/span[1]"));
        comment.click();
        WebElement addComment = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='comment']")));

        addComment.sendKeys("This comment was added via WebDriver", Keys.CONTROL, Keys.ENTER);

        // TODO assert button

    }

    public void updateIssueTitle() {
        driver.get("http://soft.it-hillel.com.ua:8080/browse/" + issueKey);
        WebElement changeIssueTitle = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='summary-val']")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        changeIssueTitle.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO add assert text bar
        WebElement changeIssueSummary = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='summary']")));
        changeIssueSummary.clear();

        changeIssueSummary.sendKeys(" This title was changed via WebDriver", Keys.ENTER);

    }

    public void deleteIssue() {
        driver.get("http://soft.it-hillel.com.ua:8080/browse/" + issueKey);
        driver.findElement(By.xpath("//*[@id='opsbar-operations_more']/span[1]")).click();
        driver.findElement(By.xpath("//*[@id='delete-issue']/span")).click();
        driver.findElement(By.xpath("//*[@id='delete-issue-submit']")).click();
    }


}
