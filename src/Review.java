import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Review {

    public static void postReview(String barcode) {

        try {
            Jsoup.connect("http://www.kyobobook.co.kr/product/productSimpleReviewReg.laf")
                    .userAgent("Mozilla/5.0")
                    .cookies(Login.cookies)
                    .data(
                            "barcode", barcode
                            //, "ejkGb", "KOR"
                            //, "bookNm", "신세계에서. 2(미도리의 책장 6)"
                            , "regType", "kloverReviewMyroom"
                            , "sortType", "date"
                            , "orderType", "all"
                            , "gb", "klover"
                            , "rating", "4" // 0 ~ 4
                            , "feelTag", "1" // 1: 좋아요 2: 잘읽혀요 3: 정독해요 4: 기발해요 5: 유용해요 6: 기타
                            , "content", "좋아요"
                            , "imageFile", ""
                    )
                    .method(Connection.Method.POST)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean hasMyReview(String barcode, String ejkGb) {

        Elements review;
        int pageNumber = 1;
        while((review = getReview(pageNumber, barcode, ejkGb)).size() != 0) {
            if(review.outerHtml().contains("cmt_del")) { // 삭제가 가능할 경우 자신의 리뷰이다
                return true;
            }
            pageNumber++;
        }

        return false;

    }

    public static Elements getReview(int pageNumber, String barcode, String ejkGb) {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.kyobobook.co.kr/product/productSimpleReviewSort.laf")
                    .userAgent("Mozilla/5.0")
                    .cookies(Login.cookies)
                    .data(
                            "gb", "klover"
                            , "barcode", barcode
                            , "ejkGb", ejkGb
                            , "mallGb", ""
                            , "sortType", "date"
                            , "pageNumber", Integer.toString(pageNumber)
                            , "orderType", "all"
                    )
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc.select(".comment_wrap");

    }

}
