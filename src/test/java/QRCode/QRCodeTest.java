package QRCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.netty.handler.codec.base64.Base64Decoder;
import org.apache.commons.codec.binary.Base64;
import org.assertj.core.api.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import static org.assertj.core.api.Assertions.assertThat;

/***
 * @author sabyasachinag
 */
public class QRCodeTest {

    private static WebDriver driver;

    @BeforeTest
    public void setup() throws InterruptedException{
        ChromeOptions opt=new ChromeOptions();
        opt.addArguments("--headless=new");
        String qrurl="https://sabya1234.github.io/AdvanceAutomationWithSelenium/";
        driver=new ChromeDriver(opt);
        driver.get(qrurl);
        Thread.sleep(3000);

    }
    @Test
    public void verifyQRCodeTextFromURL()
    {
        String qrCodeFilePath=driver.findElement(By.id("qr")).getAttribute("src");
        String actualqrCodeText=decodeQRCode(qrCodeFilePath);
        System.out.println(actualqrCodeText);
        assertThat(actualqrCodeText).isEqualTo("ORDERID- OD-1234");
    }
    @Test
    public void verifyQRCodeTextFrombase64()
    {
        String qrCodeFilePath=driver.findElement(By.id("qr-base64")).getAttribute("src");
        String base64path=qrCodeFilePath.split(",")[1];
        String actualqrCodeText=decodeQRCode(base64path);
        System.out.println(actualqrCodeText);
        assertThat(actualqrCodeText).isEqualTo("https://www.thoughtworks.com/");

    }

    /***
     * This function takes input the QR code image either from URL or embedded as base64 format
     * and decode and return the text
     * @param qrFilePath
     * @return
     */
    public String decodeQRCode(String qrFilePath) {
        Result result = null;
        try {
            //Get the image path and transform it in a BufferedImage
            BufferedImage bufferedImage;
            if (qrFilePath.contains("http")) {
                bufferedImage= ImageIO.read(new URL(qrFilePath));
            }
            else {
                //decoding using apache commons codec base64 library
                byte[] decode= Base64.decodeBase64(qrFilePath);
                bufferedImage= ImageIO.read(new ByteArrayInputStream(decode));
            }

              //Processed and decode using google zxing library

            LuminanceSource luminanceSource= new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(luminanceSource));
            result=new MultiFormatReader().decode(binaryBitmap);

        }
        catch(IOException|NotFoundException e)
        {
            e.printStackTrace();

        }
        return result.getText();
    }

    @AfterTest
    public void tearDown()
    {
        if(driver!=null)
        {
            driver.quit();
        }
    }

}
