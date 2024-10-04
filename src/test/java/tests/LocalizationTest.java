package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LocalizationPage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.Assert.*;

public class LocalizationTest extends BaseTest {

    String mLangPage = "https://bonigarcia.dev/selenium-webdriver-java/multilanguage.html";
    String webFormPage = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    // **** LOCAL TEST ONLY ****
    @Test
    public void acceptLangSetting() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        goToURL(mLangPage);

        // get resource strings from lang setting resource property file strings_es.properties
        ResourceBundle strings = ResourceBundle.getBundle("strings", Locale.forLanguageTag(lang));

        String title = strings.getString("title");
        String home = strings.getString("home");
        String content = strings.getString("content");
        String about = strings.getString("about");
        String contact = strings.getString("contact");

        // get the body text
        String bodyText = localizationPage.getBodyTxt();

        assertTrue(bodyText.contains(title));
        assertTrue(bodyText.contains(home));
        assertTrue(bodyText.contains(content));
        assertTrue(bodyText.contains(about));
        assertTrue(bodyText.contains(contact));

    }


}
