package CDP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v109.network.Network;
import org.openqa.selenium.devtools.v109.network.model.Request;
import org.openqa.selenium.devtools.v109.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Optional;

public class NetworkLogsTest {

    private static WebDriver driver;
    private String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @Test
    public void captureNetworkRequestAndResponseLog() {
        driver = new ChromeDriver();
        DevTools tools = ((ChromeDriver) driver).getDevTools();
        tools.createSession();
        tools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty()));

        tools.addListener(Network.requestWillBeSent(), request -> {
            Request rq = request.getRequest();
            System.out.println("Request URL:" + rq.getUrl());
            System.out.println("Request Method:" + rq.getMethod());
            System.out.println("Request Headers:" + rq.getHeaders());

        });
        tools.addListener(Network.responseReceived(), response -> {
            Response rs = response.getResponse();
            System.out.println("Status Code " + rs.getStatus());
        });
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));


    }


}
