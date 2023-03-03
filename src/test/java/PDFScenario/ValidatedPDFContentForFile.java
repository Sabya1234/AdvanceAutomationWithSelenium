package PDFScenario;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/***
 In this class we are going to Test pdf validation scenarios considering file has been Downloaded in codebase
 * then we can use the filepath to validate content.
 */

public class ValidatedPDFContentForFile {

    @Test
    public void pdfReaderTestForFile() throws IOException {

        File file = new File("src/test/resources/pdf/c4611_sample_explain.pdf");
        URL pdfUrl= file.toURI().toURL();
        System.out.println("file url---> "+pdfUrl);

        //Define Inputstream class object to point the inputstream that returned by URL class
        InputStream ip= pdfUrl.openStream();

        //Store into buffered array of input stream data
        BufferedInputStream bf = new BufferedInputStream(ip);
        //load and parse PDF file
        PDDocument pdfDocument= PDDocument.load(bf);

        //validate total page count in PDF
        int numberOfPages= pdfDocument.getNumberOfPages();
        System.out.println(numberOfPages);
        Assert.assertEquals(numberOfPages,4);

        /*** validate particular texts in pdf */

        //This PDFTExtStripper will take a pdf document and strip out all the text and ignore the formatting and such
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText=pdfTextStripper.getText(pdfDocument);
        Assert.assertTrue(pdfText.contains("PDF BOOKMARK SAMPLE"));
        Assert.assertTrue(pdfText.contains("The example bookmark file includes three distinct sections"));
        Assert.assertTrue(pdfText.contains("To run this sample, place ap_bookmark.dat in the collector directory scanned by Central"));

        /*** validate particular text from particular page in pdf, for example page 3 */
        pdfTextStripper.setStartPage(3);
        pdfTextStripper.setEndPage(3);
        String textOfpageThree= pdfTextStripper.getText(pdfDocument);
        System.out.println(textOfpageThree);
        Assert.assertTrue(textOfpageThree.contains("Deploying the Sample"));
        pdfDocument.close();


    }


}
