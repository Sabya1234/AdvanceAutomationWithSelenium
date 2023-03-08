package Security;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ZAPTest {

    static final String ZAP_PROXY_ADDRESS = "localhost";
    static final int ZAP_PROXY_PORT = 8080;

    //Generated from owap zap server
    static final String ZAP_API_KEY = "sfrr6iq6egbtg41mjl936qb0r6";
    private WebDriver driver;
    private ClientApi api;

    @BeforeMethod
    public void setup() {
        String httpProxyUrl = ZAP_PROXY_ADDRESS + ":" + ZAP_PROXY_PORT;
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(httpProxyUrl);
        proxy.setSslProxy(httpProxyUrl);

        ChromeOptions op = new ChromeOptions();
        op.setAcceptInsecureCerts(true);
        op.setProxy(proxy);
        driver = new ChromeDriver(op);
        api = new ClientApi(ZAP_PROXY_ADDRESS, ZAP_PROXY_PORT, ZAP_API_KEY);

    }

    @Test(description = "DAST scan on amazon.com using ZAP client api with selenium")
    public void amazonDASTTest() {
        driver.get("https://www.amazon.com/");
        Assert.assertTrue(driver.getTitle().contains("Amazon.com"));
    }

    @AfterMethod
    public void tearDown() {
        if (api != null) {
            String title="Amazon ZAP Security Report";
            String template="traditional-html";
            String description="This is Amazon ZAP security test Report";
            String reportfilename="amazon-zapSecurityReport.html";
            String reportdir=System.getProperty("user.dir");
            System.out.println(reportdir);

            try {
                ApiResponse response=api.reports.generate(title,template,null,description,null,null,null,
                        null,null,reportfilename,null,reportdir,null);

                System.out.println("The ZAP result file genereted at  "+response.toString());
            } catch (ClientApiException exception) {
                exception.printStackTrace();
            }

        }
        driver.quit();
    }

}
