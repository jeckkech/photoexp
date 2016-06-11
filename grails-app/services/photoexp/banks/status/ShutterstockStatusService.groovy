package photoexp.banks.status

/**
 * Created by Max on 09.06.2016.
 */
class ShutterstockStatusService {
    private static final String API_URL = "https://api.shutterstock.com/v2/"
    private static final String CLIENT_ID = "48f5310288ea98e5c333";
    private static final String CLIENT_SECRET = "a57bae7add2c5273a36045ac006491aaebcad82e";

    // https://api.shutterstock.com/v2/images/licenses?sort=newest

    private String encodeAuthorization(){
        return "Basic " + ("${CLIENT_ID}:${CLIENT_SECRET}".bytes.encodeBase64().toString());
    }

    public String searchImages(){
       return new URL("${API_URL}images/search").getText(
                requestProperties: [Authorization: this.encodeAuthorization()]
        )
    }

}
