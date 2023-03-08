package CDP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v109.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mocking the geolocation and test the expected behaviour of website for particular location for example search for
 * store locators features where current location should show storeas near user, but if u need to test from other countries
 * Then we have to mock locations coordinates, VIa WebDriver Bidirectional Protocol(through CDP)
 */
public class GeoLocationTest {

    private static WebDriver driver;
    WebDriverWait wait;

    @Test
    public void mockGeolocation() {
        driver =new ChromeDriver();
        DevTools tools= ((ChromeDriver)driver).getDevTools();

        //creating web Socket handshake
        tools.createSession();

        //setting the NY cordinates via setGeolocationOverride command under Emulation Domain, its internally buidling an Immutable Map
            tools.send(Emulation.setGeolocationOverride(
                    Optional.of(40.730610),
                    Optional.of(-73.935242),
                    Optional.of(1)));

        driver.get("https://locations.kfc.com/search");
        wait= new WebDriverWait(driver, Duration.ofSeconds(5));

        //clicking on My location now mocking NewYork Coordinates as my location
        WebElement locate=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Locator-button.js-locator-geolocateTrigger")));
        locate.click();
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement elem=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ol[@class='ResultList']/li[1]//abbr[text()='NY']")));

        //validating that stores address has 'NY' word
        assertThat(elem.isDisplayed()).isEqualTo(true);
        driver.quit();
    }


}
