package edu.gatech.cs2340.gtrational.rational;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.gtrational.rational.model.Model;
import edu.gatech.cs2340.gtrational.rational.model.RationalConfig;
import edu.gatech.cs2340.gtrational.rational.model.web.WebAPI;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings({"AssignmentToStaticFieldFromInstanceMethod", "UseOfSystemOutOrSystemErr"})
public class WebAPITest {

    private static String sessionID;

    /**
     * Set up testing
     */
    @BeforeClass
    public static void setup() {
        try {
            RationalConfig.init();
        } catch (Exception ignored) {}
        RationalConfig.setSetting("host_url", "http://localhost:8081");
    }


    private static volatile WebAPI.LoginResult loginResult;

    /**
     * Test logging in as a user
     *
     * @throws InterruptedException from Thread.sleep()
     * @throws JSONException from JSON serialization
     */
    @Test
    public void testLogin() throws InterruptedException, JSONException {

        String username = "testuser";
        String password = "testpass";

        WebAPI.login(username, password, (WebAPI.LoginResult res) -> loginResult = res);

        while (loginResult == null) {
            Thread.sleep(1);
        }

        if (loginResult.success) {
            sessionID = loginResult.sessionID;
            Model.getInstance().updateUser(
                    new JSONObject().put("email", username)
                            .put("sessionID", loginResult.sessionID)
                            .put("permLevel", loginResult.permissionLevel.ordinal())
            );
            System.out.println("Login Success; SessionID: " + sessionID);
        } else {
            System.out.println("Login Error: " + loginResult.errMsg);
        }
    }

    private static volatile List<WebAPI.RatData> getRatSightingsResult;

    /**
     * Test getting rat sighting data from the server
     *
     * @throws InterruptedException from Thread.sleep()
     * @throws JSONException from JSON serialization
     */
    @Test
    public void testGetRatSightings() throws InterruptedException, JSONException {
        if (sessionID == null) {
            testLogin();
        }

        WebAPI.getRatSightings(
                0,
                5,
                (List<WebAPI.RatData> dat) -> getRatSightingsResult = new ArrayList<>(dat)
        );

        while (getRatSightingsResult == null) {
            Thread.sleep(1);
        }

        System.out.println("Got " + getRatSightingsResult.size() + " rat sightings:");
        for (WebAPI.RatData data : getRatSightingsResult) {
            JSONObject dataJSON = data.toJson();
            System.out.println(dataJSON.toString(2));
        }
    }
}