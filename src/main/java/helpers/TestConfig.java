package helpers;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public interface TestConfig {
    public static  final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");
    public static final Gson gson = new Gson();
    public static  final OkHttpClient client = new OkHttpClient();
    public static final String authHeader = "Authorization";
    public static final String token="token";
    public static final String username ="username";
    public static final String password ="password";
    public static  final  String REGISTRATION_PATH = "https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword";
    public static  final  String ADD_CONTACT_PATH = "https://contactapp-telran-backend.herokuapp.com/v1/contacts";

}
