import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class Login {

    public static final Map<String, String> cookies;

    static {
        cookies = login();
    }

    public static Map<String, String> login() { // 교보문고 로그인 후 쿠키 반환

        String memid = "guswoqkrtk";
        String pw = "4c8cc5f13f";

        Connection.Response res = null;
        try {
            res = Jsoup.connect("http://www.kyobobook.co.kr/login/login.laf")
                    .userAgent("Mozilla/5.0")
                    .data("memid", memid, "pw", pw)
                    .method(Connection.Method.POST)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.cookies();

    }

}
