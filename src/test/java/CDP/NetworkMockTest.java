package CDP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.Test;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class NetworkMockTest {

    private static WebDriver driver;
    private String url = "https://rahulshettyacademy.com/angularAppdemo/";

    @Test
    public void mockResponseTest() throws InterruptedException {
        driver= new ChromeDriver();
        //DevTools tools=((ChromeDriver)driver).getDevTools();

        //tools.send(Fetch.enable(Optional.empty(),Optional.empty()));

        NetworkInterceptor interceptor= new NetworkInterceptor(driver,
                Route.matching(req->req.getUri().contains("=shetty")).to(
                        ()-> req->new HttpResponse().setStatus(200)
                                .addHeader("Content-Type","application/json;charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate,br")
                                .setContent(utf8String("{\"book_name\":\"Something\",\"isbn\":\"ad\",\"aisle\":\"123\""))


                )
        );

        driver.get(url);
        WebElement elem= driver.findElement(By.cssSelector("button.btn"));
        elem.click();
        Thread.sleep(3000);

    }
}
