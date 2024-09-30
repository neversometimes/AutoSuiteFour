package tests;

import base.BaseTest;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import pages.GeolocPage;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.Optional;


public class GeolocTest extends BaseTest {

    String geoPage = "https://bonigarcia.dev/selenium-webdriver-java/geolocation.html";


    // ****  NOTE:  this test is CHROME-ONLY based on its dependency of chrome options  ****
    @Test (groups = {"chrome-only"})  //??
    public void overrideGeoLoc() {
        GeolocPage geolocationPage = new GeolocPage(driver);

        goToURL(geoPage);

        // click Get coordinates button on page
        geolocationPage.clickGetCoordBtn();

        // get the current latitude and longitude coordinates
        String currLat = geolocationPage.getLatitude();
        String currLon = geolocationPage.getLongitude();

        // override current geolocation to be Paris, France
        devTools.send(Emulation.setGeolocationOverride
                (Optional.of(48.8584), Optional.of(2.2945), Optional.of(100)));

        // click Get coordinates button on page
        geolocationPage.clickGetCoordBtn();

        // verify page shows new geolocation coordinates of Paris, France
        assertNotEquals(geolocationPage.getLatitude(), currLat);
        assertNotEquals(geolocationPage.getLongitude(), currLon);
        assertEquals(geolocationPage.getLatitude(), "Latitude: 48.8584°");
        assertEquals(geolocationPage.getLongitude(), "Longitude: 2.2945°");

        devTools.close();
    }

}
