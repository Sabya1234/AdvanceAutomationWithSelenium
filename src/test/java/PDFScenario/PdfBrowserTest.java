package PDFScenario;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
import java.net.URLConnection;
import java.time.Duration;


/***
 * In this class we are going to Test pdf validation scenarios for redirecting to the PDF file as url  also we will try to validate content in incognito
 * as well as in headless mode
 */

public class PdfBrowserTest {

    WebDriver driver;
    WebDriverWait wait;
    String url="https://www.inkit.com/blog/pdf-the-best-digital-document-management";
    @BeforeTest
    public void setup()  {
       // ChromeOptions opt= new ChromeOptions();
        //opt.setHeadless(true);
        //opt.addArguments("--incognito");
        driver= new ChromeDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement accept=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='ecSubmitAgreeButton']")));
        accept.click();
    }

    @Test
    public void pdfReaderTestInSameTab() throws IOException, InterruptedException {


        String urlOfPdf=driver.findElement(By.linkText("trillions of PDFs")).getAttribute("href");

       // Boolean val=wait.until(ExpectedConditions.urlContains(".pdf"));
        //convert the string "url" to a uniform resource locator via URL class
//        String pdfUrl=driver.getCurrentUrl();
        System.out.println(urlOfPdf);
//        System.out.println(pdfUrl);
        URL url = new URL(urlOfPdf);
        //Define Inputstream class object to point the inputstream that returned by URL class
        //URLConnection urlConnection= url.openConnection();
        //urlConnection.setRequestProperty("User-Agent","Chrome");
        InputStream ip= url.openStream();

        //Store into buffered array of input stream data
        BufferedInputStream bf = new BufferedInputStream(ip);
        //load and parse PDF file
        PDDocument pdfDocument= PDDocument.load(bf);

        //validate total page count in PDF
        int numberOfPages= pdfDocument.getNumberOfPages();
        System.out.println(numberOfPages);
        Assert.assertEquals(numberOfPages,43);

        /*** validate particular texts in pdf */

        //This PDFTExtStripper will take a pdf document and strip out all the text and ignore the formatting and such
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText=pdfTextStripper.getText(pdfDocument);
        Assert.assertTrue(pdfText.contains("PDF statistics"));
        Assert.assertTrue(pdfText.contains("Context: web traffic"));
        Assert.assertTrue(pdfText.contains("Core W3C technologies (HTML / CSS / JavaScript)"));

        /*** validate particular text from particular page in pdf, for example page 3 */
        pdfTextStripper.setStartPage(43);
        String textOfpageThree= pdfTextStripper.getText(pdfDocument);
        System.out.println(textOfpageThree);
        Assert.assertTrue(textOfpageThree.contains("Thank you!"));

        /*** Extract Metadata of PDF ***/

        pdfDocument.close();

    }


//    @AfterTest
//    public void tearDown()
//    {
//        if (driver!=null)
//        {
//            driver.quit();
//        }
//    }
}
