package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.OSNotificationPage;

import static org.testng.Assert.*;

public class OSNotificationTest extends BaseTest {
    String notifyPage = "https://bonigarcia.dev/selenium-webdriver-java/notifications.html";

    @Test (groups={"chrome-only"})
    public void OSNotify(){
        OSNotificationPage osNotify = new OSNotificationPage(driver);
        goToURL(notifyPage);

        // This JS returns a String from the notification
        String script = String.join("\n",
                "const callback = arguments[arguments.length - 1];",  // callback function to end the script
                "const OldNotify = window.Notification;",               // store a copy of the original constructor
                "function newNotification(title, options) {",          // create new constructor for notifications
                "     callback(title);" ,                              // pass msg title as arg in callback
                "     return new OldNotify(title, options);",          // use old constructor to create original notificaton object
                "}",
                "newNotification.requestPermission = " +
                        "OldNotify.requestPermission.bind(OldNotify);" ,
                "Object.defineProperty(newNotification, 'permission', {" ,
                "     get: function() {" ,
                "         return OldNotify.permission;" ,
                "     }" ,
                "});" ,
                "window.Notification = newNotification;" ,
                "document.getElementById('notify-me').click();") ;        // btn click triggers notification on web page

        Object notifyTitle = osNotify.executeJS(script);

        // verify notification title
        assertEquals(notifyTitle, "This is a notification");

    }
}
