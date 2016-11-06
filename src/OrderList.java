import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderList {

    public static List<Book> getOrderLists() {

        List<Book> books = new LinkedList<>();
        List<Book> tempBooks;
        int nowPageNo = 1;
        while ((tempBooks = getOrderList(nowPageNo)).size() != 0) {
            books.addAll(tempBooks);
            nowPageNo++;
        }
        return books;

    }

    public static List<Book> getOrderList(int nowPageNo) {

        List<Book> books = new LinkedList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect("http://order.kyobobook.co.kr/myroom/order/orderList")
                    .userAgent("Mozilla/5.0")
                    .cookies(Login.cookies)
                    .data(
                            "nowPageNo", Integer.toString(nowPageNo)
                            , "endDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // Now
                            , "strDate", LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // A year ago
                            , "month", "12"
                            , "filter_gb", "A"
                            , "period_type", "YMD"
                    )
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String _orderId = ""; // 한 권 이상의 책들이 동일한 주문번호를 가지고 있을 수 있음

        for(Element tr : doc.select("tr")) {

            String _title = "";
            String _status = "";
            String _barcode = "";
            String _ejkGb = "";

            // Title, Barcode and ejkGb

            Elements book_name = tr.select(".book_name a[href]");

            _title = book_name.text();

            Pattern pattern = Pattern.compile("toView\\('.*?', '(.*?)', '(.*?)'\\)"); // toView(type, cmdt_code, ejkGb) // Example: toView('', '9788901092973', 'KOR');
            Matcher matcher = pattern.matcher(book_name.outerHtml());
            if (matcher.find()) {
                _barcode = matcher.group(1);
                _ejkGb = matcher.group(2);
            }

            // Order ID
            Elements order_number = tr.select(".order_number");
            if(!order_number.isEmpty()) {
                _orderId = order_number.text();
            }

            // Status
            String html = tr.outerHtml();
            if (html.contains("주문취소")) {
                _status = "주문취소";
            } else if (html.contains("반품완료")) {
                _status = "반품완료";
            } else if (html.contains("수령완료")) {
                _status = "수령완료";
            } else if (html.contains("배송완료")) {
                _status = "배송완료";
            } else if (html.contains("배송중")) {
                _status = "배송중";
            } else if (html.contains("출고작업중")) {
                _status = "출고작업";
            } else if (html.contains("상품준비중")) {
                _status = "상품준비중";
            } else if (html.contains("결재완료")) {
                _status = "결재완료";
            } else if (html.contains("주문접수")) {
                _status = "주문접수";
            }

            // Books
            if (!_title.equals("")) { // if _title is not ""
                books.add(new Book(_title, _orderId, _status, _barcode, _ejkGb));
            }

        }

        return books;

    }

}