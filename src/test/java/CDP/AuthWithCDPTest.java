package CDP;


import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v109.network.Network;
import org.openqa.selenium.devtools.v109.network.model.Headers;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/***
 * Problem Statement:
 * This class will show how to handle Authentication via Chrome dev tool protocols which is reliable for
 * Edge browser also, previously we used to do like -"https://admin:admin@abc.com" by this we used to handle
 * chrome Auth popup for basic auht, but that will not work all time, what if its uplock at server side or
 * the "username" like "admin@1" then url formation is invalid by the traditional Approach,Hence approach not
 * reliable for all time
 */
public class AuthWithCDPTest {

    WebDriver driver;
    private static final String username="admin";
    private static final String password="admin";
    private String url="https://the-internet.herokuapp.com/basic_auth";

    @BeforeTest
    public void setup()
    {
        driver=new ChromeDriver();

    }

    @Test
    public void validateAuth() {

        DevTools tools= ((ChromeDriver)driver).getDevTools();
        tools.createSession();
        tools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        //Send Auth Headers

        Map<String,Object> headers = new HashMap<>();

        String baseAuth="Basic "+ new String(new Base64().encode(String.format("%s:%s",username,password).getBytes()));
        //set Authorization header
        headers.put("Authorization",baseAuth);

        tools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
        driver.get(url);
        WebElement ele=driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]"));
        assertThat(ele.getText()).contains("Congratulations! You must have the proper credentials");

    }

    /***
     * using Bidirectional Protocol(internally using CDP), this test will work with Mozilla firefox/safari also cause
     * here we didnot used CDP
     */

    @Test
    public void validateAuthWithBidi(){

        ((HasAuthentication)driver).register(()-> new UsernameAndPassword(username,password));
        driver.get(url);
        WebElement ele=driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]"));
        assertThat(ele.getText()).contains("Congratulations! You must have the proper credentials");

    }

    @AfterTest
    public void tearDown()
    {
        driver.quit();
    }
}
