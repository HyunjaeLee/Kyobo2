import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delivery {

    public static String searchDelivery(String orderId) { // 교보문고 주문번호를 받고 운송장 번호 조회 주소를 반환함

        Document doc = null;
        try {
            doc = Jsoup.connect("http://order.kyobobook.co.kr/myroom/order/searchDelivLayer")
                    .userAgent("Mozilla/5.0")
                    .data("ordr_id", orderId)
                    .cookies(Login.cookies)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("popDelivery\\('(.*?)', '(.*?)', '(.*?)'\\)"); //popDelivery(dlvrShpCode, dscm_code, invc_num)
        Matcher matcher = pattern.matcher(doc.outerHtml());
        if (matcher.find()) {
            return popDelivery(matcher.group(1), matcher.group(2), matcher.group(3)); //popDelivery(dlvrShpCode, dscm_code, invc_num)
        } else {
            return null;
        }

    }

    public static String popDelivery(String dlvrShpCode, String dscm_code, String invc_num) { // 데이터를 받고 택배사 주소 반환 (교보문고의 자바스크립트를 컨버팅함)

        if (dlvrShpCode.equals("110")){ // 일반 택배
            if(dscm_code.equals("001")){ // 한진택배
                return "http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=" + invc_num + "&Bgrn=1eeefff";
            } else if(dscm_code.equals("002")){	// CJ대한통운택배
                return "http://www.cjgls.co.kr/kor/service/service02_01.asp?slipno=" + invc_num;
            } else if(dscm_code.equals("003")){	// 우체국택배
                return "http://service.epost.go.kr/trace.RetrieveRegiPrclDeliv.postal?sid1=" + invc_num;
            } else if(dscm_code.equals("004")){	// 현대택배
                return "https://www.hlc.co.kr/home/personal/inquiry/track?InvNo="+ invc_num;
            } else if(dscm_code.equals("005")){	// KG옐로우캡택배
                return "http://www.kglogis.co.kr/delivery/delivery_result.jsp?item_no=" + invc_num;
            } else if(dscm_code.equals("006")){	// 로젠택배
                return "http://d2d.ilogen.com/d2d/delivery/invoice_tracesearch_quick.jsp?slipno=" + invc_num;
            } else if(dscm_code.equals("007")){	// KGB택배
                return "http://www.kgbls.co.kr/sub5/trace.asp?f_slipno=" + invc_num;
            } else if(dscm_code.equals("008")){	//	KG로지스(동부)
                return "http://www.kglogis.co.kr/delivery/delivery_result.jsp?item_no=" + invc_num;
            } else if(dscm_code.equals("009")){	// 대신택배
            } else if(dscm_code.equals("010")){	// 천일택배
            } else if(dscm_code.equals("011")){	// 합동택배
            } else if(dscm_code.equals("012")){	// 일양로지스
            } else if(dscm_code.equals("013")){	// 경동택배
                return "http://www.kdexp.com/sub3_shipping.asp?stype=1&p_item=" + invc_num;
            } else if(dscm_code.equals("014")){	// 건영택배
            } else if(dscm_code.equals("015")){	// 고려택배
            } else if(dscm_code.equals("016")){	// 범한판토스
            } else if(dscm_code.equals("017")){	// 에어보이익스프레스
            } else if(dscm_code.equals("018")){	// 한덱스
            } else if(dscm_code.equals("022")){	// GTX로지스
            } else if(dscm_code.equals("023")){	// OCS
            } else if(dscm_code.equals("024")){	// TNT Express
            } else if(dscm_code.equals("026")){	// 한의사랑택배
            }
        } else if (dlvrShpCode.equals("111")){ // 편의점 택배
            return "http://was.cvsnet.co.kr/trans2_check_pickup.jsp?invoice_no=" + invc_num;
        } else if (dlvrShpCode.equals("120")){ // 해외배송
            if(dscm_code.equals("019")){ // DHL
                return "http://www.dhl.co.kr/ec/kyobo/kyobotrack.asp?ref_no=" + invc_num;
            } else if(dscm_code.equals("020")){	// EMS
            } else if(dscm_code.equals("021")){	// Fedex
                return "http://www.fedex.com/Tracking?cntry_code=kr&language=korean&tracknumbers=" + invc_num;
            } else if(dscm_code.equals("025")){	// UPS
                return "http://wwwapps.ups.com/WebTracking/processRequest?HTMLVersion=5.0&Requester=NES&AgreeToTermsAndConditions=yes&loc=ko_KR&tracknum=" + invc_num;
            }
        }

        return null;

    }

}
