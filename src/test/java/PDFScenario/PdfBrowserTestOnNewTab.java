package PDFScenario;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;


/***
 * In this class we are going to Test pdf validation scenarios where clickin on link text rediredted to pdf in another browser
 * window also we will try to validate content in incognito
 * as well as in headless mode
 */

public class PdfBrowserTestOnNewTab {

    WebDriver driver;
    WebDriverWait wait;
    String url = "https://www.hdfcbank.com/personal/resources/rates";

    @BeforeTest
    public void setup() {
        ChromeOptions opt = new ChromeOptions();
        //opt.setHeadless(true);
        //opt.addArguments("--incognito");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);

    }

    @Test
    public void pdfReaderTestInNewTab() throws IOException, InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[contains(text(),'Historic Rates')]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,450)", "");
        ele.click();
        WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'for Historic Savings Account Interest Rate')]/a")));
        elem.click();
        String parent = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        Iterator<String> it = windows.iterator();

        while (it.hasNext()) {
            String childWindow = it.next();
            if (!parent.equals(childWindow)) {
                driver.switchTo().window(childWindow);
                String pdfUrl = driver.getCurrentUrl();
                validatePDF(pdfUrl);
                driver.close();

            }
        }
        driver.switchTo().window(parent);

    }

    public void validatePDF(String url) throws IOException {
        System.out.println(url);
        URL pdfurl = new URL(url);
        //Define Inputstream class object to point the inputstream that returned by URL class
        InputStream ip = pdfurl.openStream();

        //Store into buffered array of input stream data
        BufferedInputStream bf = new BufferedInputStream(ip);
        //load and parse PDF file
        PDDocument pdfDocument = PDDocument.load(bf);

        //validate total page count in PDF
        int numberOfPages = pdfDocument.getNumberOfPages();
        System.out.println(numberOfPages);
        Assert.assertEquals(numberOfPages, 1);

        /*** validate particular texts in pdf */

        //This PDFTExtStripper will take a pdf document and strip out all the text and ignore the formatting and such
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        /*** validate particular text from particular page in pdf, for example page 3 */
        pdfTextStripper.setStartPage(1);
        String textOfpageOne = pdfTextStripper.getText(pdfDocument);
        System.out.println(textOfpageOne);
        Assert.assertTrue(textOfpageOne.contains("Savings Account Interest Rate"));

        /*** extract metadata in pdf */
        System.out.println(pdfDocument.getDocumentId());
        System.out.println(pdfDocument.getVersion());
        System.out.println(pdfDocument.getDocumentInformation().getAuthor());
        System.out.println(pdfDocument.isEncrypted());
        pdfDocument.close();
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
