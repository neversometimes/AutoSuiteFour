package pages;

import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class LocalPrintPage {

    WebDriver driver;

    public LocalPrintPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  ***********  PAGE FACTORY DECLARATIONS  *************


    //  ***********   PAGE METHODS   **************
    public Pdf printPageToPDF() {
        PrintsPage pg = (PrintsPage) driver;                // cast driver object to PrintsPage
        PrintOptions printOptions = new PrintOptions();
        return pg.print(printOptions);               // print current web page to PDF
    }
    public byte[] convertBase64PDFtoByteArray(Pdf pdf) {
        return Base64.getDecoder()
                .decode(pdf.getContent().getBytes(StandardCharsets.UTF_8));
    }
    public void writeByteArrayAsPDF (byte[] decodedImg, String pdfFile) throws IOException {
        Path destinationFile = Paths.get(pdfFile);
        Files.write(destinationFile, decodedImg);
    }
}
