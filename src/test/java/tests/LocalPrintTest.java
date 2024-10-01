package tests;

import base.BaseTest;
import pages.LocalPrintPage;

import org.openqa.selenium.Pdf;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.IOException;

public class LocalPrintTest extends BaseTest {

    @Test
    void printPage() throws IOException {
        LocalPrintPage localPrintPage = new LocalPrintPage(driver);
        String pdfFilename = "byteArrayPDF.pdf";
        goToURL(initPage);

        // print current page to PDF
        Pdf pdf = localPrintPage.printPageToPDF();

        //String pdfBase64 = pdf.getContent();                    // put PDF in Base64 format
        assertTrue(pdf.getContent().contains("JVBER"));        // verify Base64 PDF content

        // convert Base64 PDF to byte array
        byte[] decodeImg = localPrintPage.convertBase64PDFtoByteArray(pdf);

        // write out byte array as PDF file
        localPrintPage.writeByteArrayAsPDF(decodeImg, pdfFilename);

    }

}
