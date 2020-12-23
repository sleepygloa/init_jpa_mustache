package com.nambi.book.service.api;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class NaverMapService {


    //변수정의
    private  String API_KEY = "of9ytjj6w6";
    private  String API_SECRET_KEY = "m3keXCg4BpiLgrkRHZicAdy9QimcQn0SeQuhpo6o";
    private Logger logger = Logger.getLogger("NaverMap");

    //기본변수 set,get
    public void setAPIKey(String key) {
        API_KEY = key;
    }

    public  String  getAPIKEY() {
        return API_KEY;
    }
    public  String  getAPISECRETKEY() {
        return API_SECRET_KEY;
    }

    //TEST용 코드
    public  void main(String[] args) {
//		System.out.println("========TEST==========");
//
//		//geocoding test 소스

        float distance = 1505f * 100 / 1000;
        //distance = (distance > 0 ? Long.valueOf(String.format("%.1f", distance / 1000)) : 0);
        System.out.println(distance); // %f:실수, 1:한자리수

        //JSONObject ss = getNaverApiGeocoding("경상남도 김해시 대성동 434"); //128.8737496, 35.2374127
        //JSONObject ss = getNaverApiGeocoding("부산광역시 금정구 부곡동 225-28"); //129.0914332, 35.2354259
//		JSONObject ss = getNaverApiGeocoding("서울 동작구 신대방로 370-1"); //126.9174418, 37.4853661
        //JSONObject ss = getNaverApiGeocoding("서울 관악구 조원로 136"); //126.9174418, 37.4853661
//		//{"status":"OK","meta":{"totalCount":1,"page":1,"count":1},"addresses":[{"roadAddress":"서울특별시 관악구 조원로 136","jibunAddress":"서울특별시 관악구 신림동 492-2","englishAddress":"136, Jowon-ro, Gwanak-gu, Seoul, Republic of Korea","addressElements":[{"types":["SIDO"],"longName":"서울특별시","shortName":"서울특별시","code":""},{"types":["SIGUGUN"],"longName":"관악구","shortName":"관악구","code":""},{"types":["DONGMYUN"],"longName":"신림동","shortName":"신림동","code":""},{"types":["RI"],"longName":"","shortName":"","code":""},{"types":["ROAD_NAME"],"longName":"조원로","shortName":"조원로","code":""},{"types":["BUILDING_NUMBER"],"longName":"136","shortName":"136","code":""},{"types":["BUILDING_NAME"],"longName":"","shortName":"","code":""},{"types":["LAND_NUMBER"],"longName":"492-2","shortName":"492-2","code":""},{"types":["POSTAL_CODE"],"longName":"08762","shortName":"08762","code":""}],"x":"126.9174418","y":"37.4853661","distance":0.0}],"errorMessage":""}
//		//JSONObject ss = getNaverApiGeocoding("서울 영등포구 시흥대로 657 대림성모병원"); //126.9072208, 37.4906979
//		//{"status":"OK","meta":{"totalCount":1,"page":1,"count":1},"addresses":[{"roadAddress":"서울특별시 영등포구 시흥대로 657 대림성모병원","jibunAddress":"서울특별시 영등포구 대림동 978-13 대림성모병원","englishAddress":"657, Siheung-daero, Yeongdeungpo-gu, Seoul, Republic of Korea","addressElements":[{"types":["SIDO"],"longName":"서울특별시","shortName":"서울특별시","code":""},{"types":["SIGUGUN"],"longName":"영등포구","shortName":"영등포구","code":""},{"types":["DONGMYUN"],"longName":"대림동","shortName":"대림동","code":""},{"types":["RI"],"longName":"","shortName":"","code":""},{"types":["ROAD_NAME"],"longName":"시흥대로","shortName":"시흥대로","code":""},{"types":["BUILDING_NUMBER"],"longName":"657","shortName":"657","code":""},{"types":["BUILDING_NAME"],"longName":"대림성모병원","shortName":"대림성모병원","code":""},{"types":["LAND_NUMBER"],"longName":"978-13","shortName":"978-13","code":""},{"types":["POSTAL_CODE"],"longName":"07442","shortName":"07442","code":""}],"x":"126.9072208","y":"37.4906979","distance":0.0}],"errorMessage":""}
//		//JSONObject ss = getNaverApiGeocoding("서울 영등포구 대방천로 259"); //126.9198317, 37.4991347
//		//{"status":"OK","meta":{"totalCount":1,"page":1,"count":1},"addresses":[{"roadAddress":"서울특별시 영등포구 대방천로 259 동작세무서","jibunAddress":"서울특별시 영등포구 신길동 476 동작세무서","englishAddress":"259, Daebangcheon-ro, Yeongdeungpo-gu, Seoul, Republic of Korea","addressElements":[{"types":["SIDO"],"longName":"서울특별시","shortName":"서울특별시","code":""},{"types":["SIGUGUN"],"longName":"영등포구","shortName":"영등포구","code":""},{"types":["DONGMYUN"],"longName":"신길동","shortName":"신길동","code":""},{"types":["RI"],"longName":"","shortName":"","code":""},{"types":["ROAD_NAME"],"longName":"대방천로","shortName":"대방천로","code":""},{"types":["BUILDING_NUMBER"],"longName":"259","shortName":"259","code":""},{"types":["BUILDING_NAME"],"longName":"동작세무서","shortName":"동작세무서","code":""},{"types":["LAND_NUMBER"],"longName":"476","shortName":"476","code":""},{"types":["POSTAL_CODE"],"longName":"07432","shortName":"07432","code":""}],"x":"126.9198317","y":"37.4991347","distance":0.0}],"errorMessage":""}
//		//JSONObject ss = getNaverApiGeocoding("서울 동작구 대방동길 74 성남고등학교"); //126.9263282, 37.5051676
//		//{"status":"OK","meta":{"totalCount":1,"page":1,"count":1},"addresses":[{"roadAddress":"서울특별시 동작구 대방동길 74 성남고등학교, 성남중학교","jibunAddress":"서울특별시 동작구 대방동 375-1 성남고등학교, 성남중학교","englishAddress":"74, Daebangdong-gil, Dongjak-gu, Seoul, Republic of Korea","addressElements":[{"types":["SIDO"],"longName":"서울특별시","shortName":"서울특별시","code":""},{"types":["SIGUGUN"],"longName":"동작구","shortName":"동작구","code":""},{"types":["DONGMYUN"],"longName":"대방동","shortName":"대방동","code":""},{"types":["RI"],"longName":"","shortName":"","code":""},{"types":["ROAD_NAME"],"longName":"대방동길","shortName":"대방동길","code":""},{"types":["BUILDING_NUMBER"],"longName":"74","shortName":"74","code":""},{"types":["BUILDING_NAME"],"longName":"성남고등학교, 성남중학교","shortName":"성남고등학교, 성남중학교","code":""},{"types":["LAND_NUMBER"],"longName":"375-1","shortName":"375-1","code":""},{"types":["POSTAL_CODE"],"longName":"06946","shortName":"06946","code":""}],"x":"126.9263282","y":"37.5051676","distance":0.0}],"errorMessage":""}
//		//JSONObject ss = getNaverApiGeocoding("서울특별시 동작구 노량진동 47-2"); //126.9394728, 37.5122924
//		//{"status":"OK","meta":{"totalCount":1,"page":1,"count":1},"addresses":[{"roadAddress":"서울특별시 동작구 장승배기로 161 동작구청","jibunAddress":"서울특별시 동작구 노량진동 47-2 동작구청","englishAddress":"161, Jangseungbaegi-ro, Dongjak-gu, Seoul, Republic of Korea","addressElements":[{"types":["SIDO"],"longName":"서울특별시","shortName":"서울특별시","code":""},{"types":["SIGUGUN"],"longName":"동작구","shortName":"동작구","code":""},{"types":["DONGMYUN"],"longName":"노량진동","shortName":"노량진동","code":""},{"types":["RI"],"longName":"","shortName":"","code":""},{"types":["ROAD_NAME"],"longName":"장승배기로","shortName":"장승배기로","code":""},{"types":["BUILDING_NUMBER"],"longName":"161","shortName":"161","code":""},{"types":["BUILDING_NAME"],"longName":"동작구청","shortName":"동작구청","code":""},{"types":["LAND_NUMBER"],"longName":"47-2","shortName":"47-2","code":""},{"types":["POSTAL_CODE"],"longName":"06928","shortName":"06928","code":""}],"x":"126.9394728","y":"37.5122924","distance":0.0}],"errorMessage":""}
//
//		//reverse geocoding test 소스
////		JSONObject ss = getNaverApiReverseGeocoding("126.9198317", "37.4991347"); //126.9198317,37.4991347
//		//{"status":{"code":0,"name":"ok","message":"done"},"results":[{"name":"legalcode","code":{"id":"1156013200","type":"L","mappingId":"09560132"},"region":{"area0":{"name":"kr","coords":{"center":{"crs":"","x":0.0,"y":0.0}}},"area1":{"name":"서울특별시","coords":{"center":{"crs":"EPSG:4326","x":126.978388,"y":37.5666102}},"alias":"서울"},"area2":{"name":"영등포구","coords":{"center":{"crs":"EPSG:4326","x":126.896004,"y":37.5264359}}},"area3":{"name":"신길동","coords":{"center":{"crs":"EPSG:4326","x":126.911537,"y":37.505933}}},"area4":{"name":"","coords":{"center":{"crs":"","x":0.0,"y":0.0}}}}},{"name":"admcode","code":{"id":"1156068000","type":"A","mappingId":"09560680"},"region":{"area0":{"name":"kr","coords":{"center":{"crs":"","x":0.0,"y":0.0}}},"area1":{"name":"서울특별시","coords":{"center":{"crs":"EPSG:4326","x":126.978388,"y":37.5666102}},"alias":"서울"},"area2":{"name":"영등포구","coords":{"center":{"crs":"EPSG:4326","x":126.896004,"y":37.5264359}}},"area3":{"name":"신길6동","coords":{"center":{"crs":"EPSG:4326","x":126.909837,"y":37.499418}}},"area4":{"name":"","coords":{"center":{"crs":"","x":0.0,"y":0.0}}}}}]}
        //JSONObject ss = getNaverApiReverseGeocoding("126.9174418", "37.4853661"); //본사
        //JSONObject ss = getNaverApiReverseGeocoding("126.9211042", "37.5024155"); //본사

//
//		//direction15 test 소스
//		//JSONObject ss = callApi("DIRECTION15", "start=126.9174418,37.4853661&goal=126.9072208,37.4906979:126.9198317,37.4991347:126.9263282,37.5051676:126.9394728,37.5122924");
//		//{"code":0,"message":"길찾기를 성공하였습니다.","currentDateTime":"2020-10-20T16:22:58","route":{"traoptimal":[{"summary":{"start":{"location":[126.9174411,37.4853657]},"goal":{"location":[126.9198310,37.4991340],"dir":2},"distance":4011,"duration":948103,"departureTime":"2020-10-20T16:22:58","bbox":[[126.9028500,37.4844358],[126.9202491,37.4991399]],"tollFare":0,"taxiFare":6500,"fuelPrice":381},"path":[[126.9174086,37.4854782],[126.9171816,37.4854384],[126.9171160,37.4854263],[126.9168630,37.4853809],[126.9164609,37.4853077],[126.9160091,37.4852235],[126.9157233,37.4851752],[126.9153653,37.4851148],[126.9152444,37.4850926],[126.9150795,37.4850602],[126.9149316,37.4850315],[126.9146051,37.4849722],[126.9143567,37.4849241],[126.9141511,37.4848880],[126.9139805,37.4848538],[126.9139647,37.4848510],[126.9139388,37.4849915],[126.9138715,37.4853427],[126.9138305,37.4855272],[126.9137695,37.4858027],[126.9137530,37.4858901],[126.9137390,37.4859522],[126.9136733,37.4862502],[126.9136007,37.4865609],[126.9135901,37.4866140],[126.9135701,37.4867148],[126.9135336,37.4869030],[126.9135278,37.4869192],[126.9135187,37.4869291],[126.9134754,37.4869658],[126.9134038,37.4870051],[126.9133993,37.4870078],[126.9132124,37.4868915],[126.9131055,37.4868216],[126.9126373,37.4865245],[126.9125810,37.4864909],[126.9122243,37.4862575],[126.9121848,37.4862419],[126.9120124,37.4861582],[126.9118918,37.4861044],[126.9117869,37.4860579],[126.9114747,37.4859247],[126.9114431,37.4859156],[126.9110582,37.4858064],[126.9109691,37.4857807],[126.9107242,37.4857074],[126.9106553,37.4856872],[126.9105786,37.4856661],[126.9105075,37.4856468],[126.9103438,37.4856000],[126.9098055,37.4854477],[126.9095493,37.4853743],[126.9095110,37.4853615],[126.9093789,37.4853248],[126.9090979,37.4852450],[126.9089263,37.4851990],[126.9086881,37.4851411],[126.9081384,37.4850022],[126.9078742,37.4849333],[126.9074003,37.4847912],[126.9070494,37.4846831],[126.9067244,37.4845832],[126.9064612,37.4845332],[126.9061574,37.4844740],[126.9059993,37.4844416],[126.9058252,37.4844390],[126.9057245,37.4844358],[126.9056250,37.4844371],[126.9054485,37.4844380],[126.9047867,37.4844509],[126.9041748,37.4844577],[126.9038331,37.4844641],[126.9037867,37.4844666],[126.9031180,37.4844938],[126.9028715,37.4844917],[126.9028500,37.4844916],[126.9030247,37.4847079],[126.9030661,37.4847586],[126.9032241,37.4849495],[126.9032611,37.4849939],[126.9033776,37.4851351],[126.9034370,37.4852075],[126.9035243,37.4853242],[126.9036219,37.4854257],[126.9036320,37.4854356],[126.9038538,37.4857090],[126.9040328,37.4859487],[126.9040899,37.4860256],[126.9041078,37.4860510],[126.9044493,37.4864916],[126.9046699,37.4867731],[126.9047864,37.4869161],[126.9049768,37.4871586],[126.9051324,37.4873577],[126.9052993,37.4875721],[126.9053183,37.4875966],[126.9053418,37.4876264],[126.9054999,37.4878075],[126.9057410,37.4880737],[126.9059372,37.4882910],[126.9062767,37.4886965],[126.9063877,37.4888260],[126.9063989,37.4888396],[126.9065423,37.4890161],[126.9066364,37.4891328],[126.9068279,37.4893789],[126.9076243,37.4903663],[126.9078250,37.4905981],[126.9078418,37.4906162],[126.9079247,37.4907166],[126.9081567,37.4909945],[126.9082665,37.4911312],[126.9083874,37.4912832],[126.9087123,37.4916832],[126.9090386,37.4920562],[126.9091260,37.4921576],[126.9092123,37.4922581],[126.9092268,37.4922825],[126.9093065,37.4923703],[126.9094891,37.4925885],[126.9094925,37.4925930],[126.9095553,37.4926645],[126.9097280,37.4928538],[126.9098256,37.4929570],[126.9101618,37.4933607],[126.9102156,37.4934286],[126.9103388,37.4935860],[126.9104440,37.4937190],[126.9105247,37.4938249],[126.9106780,37.4940393],[126.9107093,37.4940827],[126.9107484,37.4941406],[126.9107864,37.4941976],[126.9108647,37.4943205],[126.9109418,37.4944435],[126.9111123,37.4947661],[126.9112921,37.4950663],[126.9112943,37.4950699],[126.9114105,37.4952607],[126.9115222,37.4954442],[126.9116997,37.4957416],[126.9118564,37.4959542],[126.9120369,37.4961579],[126.9121709,37.4962415],[126.9123184,37.4963324],[126.9123748,37.4963534],[126.9124650,37.4963926],[126.9126942,37.4964532],[126.9128941,37.4964876],[126.9129563,37.4964978],[126.9140179,37.4965851],[126.9141716,37.4966003],[126.9142835,37.4966098],[126.9143163,37.4966127],[126.9146419,37.4966414],[126.9149516,37.4966781],[126.9150657,37.4966985],[126.9151605,37.4967206],[126.9157863,37.4968462],[126.9160268,37.4968970],[126.9162246,37.4969286],[126.9164416,37.4969459],[126.9165027,37.4969525],[126.9167864,37.4969828],[126.9172179,37.4970696],[126.9175183,37.4971297],[126.9176843,37.4971684],[126.9187623,37.4974747],[126.9187793,37.4974793],[126.9188595,37.4974987],[126.9189949,37.4975327],[126.9193992,37.4976329],[126.9196419,37.4977035],[126.9196723,37.4977181],[126.9197715,37.4977645],[126.9198491,37.4978163],[126.9199874,37.4979332],[126.9201494,37.4981999],[126.9201771,37.4982767],[126.9202380,37.4984455],[126.9202468,37.4984672],[126.9202491,37.4984744],[126.9199654,37.4985848],[126.9197748,37.4986578],[126.9195615,37.4987325],[126.9195643,37.4988055],[126.9195662,37.4988506],[126.9195776,37.4991399]],"section":[{"pointIndex":31,"pointCount":45,"distance":1000,"name":"신사로","congestion":2,"speed":23},{"pointIndex":75,"pointCount":46,"distance":1073,"name":"시흥대로","congestion":3,"speed":19},{"pointIndex":120,"pointCount":62,"distance":1264,"name":"여의대방로","congestion":3,"speed":11}],"guide":[{"pointIndex":15,"type":3,"instructions":"신사동주민센터입구에서 '신대방역' 방면으로 우회전","distance":314,"duration":80278},{"pointIndex":31,"type":2,"instructions":"신대방역에서 '구로디지털단지역' 방면으로 좌회전","distance":248,"duration":92256},{"pointIndex":75,"type":3,"instructions":"구로디지털단지역에서 '시흥대로' 방면으로 우회전","distance":1000,"duration":151854},{"pointIndex":165,"type":1,"instructions":"기상청입구에서 '원효대교, 보라매역' 방면으로 직진","distance":2033,"duration":420798},{"pointIndex":181,"type":2,"instructions":"대방천사거리에서 '신길6동' 방면으로 좌회전","distance":304,"duration":180694},{"pointIndex":184,"type":3,"instructions":"'신풍로26길' 방면으로 우회전","distance":67,"duration":12694},{"pointIndex":187,"type":88,"instructions":"목적지","distance":45,"duration":9529}]}]}}
//		//{"code":0,"message":"길찾기를 성공하였습니다.","currentDateTime":"2020-10-20T16:23:56","route":{"traoptimal":[{"summary":{"start":{"location":[126.9174411,37.4853657]},"goal":{"location":[126.9198310,37.4991340],"dir":2},"distance":4011,"duration":944283,"departureTime":"2020-10-20T16:23:56","bbox":[[126.9028500,37.4844358],[126.9202491,37.4991399]],"tollFare":0,"taxiFare":6500,"fuelPrice":381},"path":[[126.9174086,37.4854782],[126.9171816,37.4854384],[126.9171160,37.4854263],[126.9168630,37.4853809],[126.9164609,37.4853077],[126.9160091,37.4852235],[126.9157233,37.4851752],[126.9153653,37.4851148],[126.9152444,37.4850926],[126.9150795,37.4850602],[126.9149316,37.4850315],[126.9146051,37.4849722],[126.9143567,37.4849241],[126.9141511,37.4848880],[126.9139805,37.4848538],[126.9139647,37.4848510],[126.9139388,37.4849915],[126.9138715,37.4853427],[126.9138305,37.4855272],[126.9137695,37.4858027],[126.9137530,37.4858901],[126.9137390,37.4859522],[126.9136733,37.4862502],[126.9136007,37.4865609],[126.9135901,37.4866140],[126.9135701,37.4867148],[126.9135336,37.4869030],[126.9135278,37.4869192],[126.9135187,37.4869291],[126.9134754,37.4869658],[126.9134038,37.4870051],[126.9133993,37.4870078],[126.9132124,37.4868915],[126.9131055,37.4868216],[126.9126373,37.4865245],[126.9125810,37.4864909],[126.9122243,37.4862575],[126.9121848,37.4862419],[126.9120124,37.4861582],[126.9118918,37.4861044],[126.9117869,37.4860579],[126.9114747,37.4859247],[126.9114431,37.4859156],[126.9110582,37.4858064],[126.9109691,37.4857807],[126.9107242,37.4857074],[126.9106553,37.4856872],[126.9105786,37.4856661],[126.9105075,37.4856468],[126.9103438,37.4856000],[126.9098055,37.4854477],[126.9095493,37.4853743],[126.9095110,37.4853615],[126.9093789,37.4853248],[126.9090979,37.4852450],[126.9089263,37.4851990],[126.9086881,37.4851411],[126.9081384,37.4850022],[126.9078742,37.4849333],[126.9074003,37.4847912],[126.9070494,37.4846831],[126.9067244,37.4845832],[126.9064612,37.4845332],[126.9061574,37.4844740],[126.9059993,37.4844416],[126.9058252,37.4844390],[126.9057245,37.4844358],[126.9056250,37.4844371],[126.9054485,37.4844380],[126.9047867,37.4844509],[126.9041748,37.4844577],[126.9038331,37.4844641],[126.9037867,37.4844666],[126.9031180,37.4844938],[126.9028715,37.4844917],[126.9028500,37.4844916],[126.9030247,37.4847079],[126.9030661,37.4847586],[126.9032241,37.4849495],[126.9032611,37.4849939],[126.9033776,37.4851351],[126.9034370,37.4852075],[126.9035243,37.4853242],[126.9036219,37.4854257],[126.9036320,37.4854356],[126.9038538,37.4857090],[126.9040328,37.4859487],[126.9040899,37.4860256],[126.9041078,37.4860510],[126.9044493,37.4864916],[126.9046699,37.4867731],[126.9047864,37.4869161],[126.9049768,37.4871586],[126.9051324,37.4873577],[126.9052993,37.4875721],[126.9053183,37.4875966],[126.9053418,37.4876264],[126.9054999,37.4878075],[126.9057410,37.4880737],[126.9059372,37.4882910],[126.9062767,37.4886965],[126.9063877,37.4888260],[126.9063989,37.4888396],[126.9065423,37.4890161],[126.9066364,37.4891328],[126.9068279,37.4893789],[126.9076243,37.4903663],[126.9078250,37.4905981],[126.9078418,37.4906162],[126.9079247,37.4907166],[126.9081567,37.4909945],[126.9082665,37.4911312],[126.9083874,37.4912832],[126.9087123,37.4916832],[126.9090386,37.4920562],[126.9091260,37.4921576],[126.9092123,37.4922581],[126.9092268,37.4922825],[126.9093065,37.4923703],[126.9094891,37.4925885],[126.9094925,37.4925930],[126.9095553,37.4926645],[126.9097280,37.4928538],[126.9098256,37.4929570],[126.9101618,37.4933607],[126.9102156,37.4934286],[126.9103388,37.4935860],[126.9104440,37.4937190],[126.9105247,37.4938249],[126.9106780,37.4940393],[126.9107093,37.4940827],[126.9107484,37.4941406],[126.9107864,37.4941976],[126.9108647,37.4943205],[126.9109418,37.4944435],[126.9111123,37.4947661],[126.9112921,37.4950663],[126.9112943,37.4950699],[126.9114105,37.4952607],[126.9115222,37.4954442],[126.9116997,37.4957416],[126.9118564,37.4959542],[126.9120369,37.4961579],[126.9121709,37.4962415],[126.9123184,37.4963324],[126.9123748,37.4963534],[126.9124650,37.4963926],[126.9126942,37.4964532],[126.9128941,37.4964876],[126.9129563,37.4964978],[126.9140179,37.4965851],[126.9141716,37.4966003],[126.9142835,37.4966098],[126.9143163,37.4966127],[126.9146419,37.4966414],[126.9149516,37.4966781],[126.9150657,37.4966985],[126.9151605,37.4967206],[126.9157863,37.4968462],[126.9160268,37.4968970],[126.9162246,37.4969286],[126.9164416,37.4969459],[126.9165027,37.4969525],[126.9167864,37.4969828],[126.9172179,37.4970696],[126.9175183,37.4971297],[126.9176843,37.4971684],[126.9187623,37.4974747],[126.9187793,37.4974793],[126.9188595,37.4974987],[126.9189949,37.4975327],[126.9193992,37.4976329],[126.9196419,37.4977035],[126.9196723,37.4977181],[126.9197715,37.4977645],[126.9198491,37.4978163],[126.9199874,37.4979332],[126.9201494,37.4981999],[126.9201771,37.4982767],[126.9202380,37.4984455],[126.9202468,37.4984672],[126.9202491,37.4984744],[126.9199654,37.4985848],[126.9197748,37.4986578],[126.9195615,37.4987325],[126.9195643,37.4988055],[126.9195662,37.4988506],[126.9195776,37.4991399]],"section":[{"pointIndex":31,"pointCount":45,"distance":1000,"name":"신사로","congestion":2,"speed":23},{"pointIndex":75,"pointCount":46,"distance":1073,"name":"시흥대로","congestion":3,"speed":18},{"pointIndex":120,"pointCount":62,"distance":1264,"name":"여의대방로","congestion":3,"speed":11}],"guide":[{"pointIndex":15,"type":3,"instructions":"신사동주민센터입구에서 '신대방역' 방면으로 우회전","distance":314,"duration":80278},{"pointIndex":31,"type":2,"instructions":"신대방역에서 '구로디지털단지역' 방면으로 좌회전","distance":248,"duration":92256},{"pointIndex":75,"type":3,"instructions":"구로디지털단지역에서 '시흥대로' 방면으로 우회전","distance":1000,"duration":151854},{"pointIndex":165,"type":1,"instructions":"기상청입구에서 '원효대교, 보라매역' 방면으로 직진","distance":2033,"duration":402192},{"pointIndex":181,"type":2,"instructions":"대방천사거리에서 '신길6동' 방면으로 좌회전","distance":304,"duration":195480},{"pointIndex":184,"type":3,"instructions":"'신풍로26길' 방면으로 우회전","distance":67,"duration":12694},{"pointIndex":187,"type":88,"instructions":"목적지","distance":45,"duration":9529}]}]}}
//
//
        JSONObject ss = callApi("DIRECTION15", "start=129.09144088625908,35.235685017498554&goal=128.97891356824988,35.16441535482437");
//
//		/////////////////////////////////////////////
//		// Coding test
//		// 경유지 안씀
//		// 현재순번과 다음순번만 이용하여 출도착지구성, 배송순서가 많다면, 건건이 API 콜하는 구조
//		/////////////////////////////////////////////
////		System.out.println(ss.get("code"));
////		System.out.println(ss.get("message"));
////
//
////		JSONObject route = (JSONObject)ss.get("route");
////		JSONObject traoptimal = (JSONObject)route.get("traoptimal");
//
//		//출도착 summary
////		JSONObject summary = (JSONObject)traoptimal.get("summary");
////		JSONObject distance = (JSONObject)summary.get("distance"); //총이동거리
////		JSONObject tollFare = (JSONObject)summary.get("tollFare"); //통행료
////		JSONObject fuelPrice = (JSONObject)summary.get("fuelPrice"); //유류비
//
//		//출도착 상세경로
////		JSONObject section = (JSONObject)traoptimal.get("section");
//
//		System.out.println("======================");
    }

    /**
     * baseUrl 정의
     * */
    public  String getBaseUrl(String key) {

        String url = "";

        switch (key) {
            case "DIRECTION15" :
                url = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?";
                break;
            case "DIRECTION5" :
                url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?";
                break;
            case "GEOCODING" :
                url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?";
                break;
            case "REVERSEGEOCODING" :
                url = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?";
                break;
        }
        return url;
    }


    /***
     * 네이버 클라우드 플랫폼
     * [Direction 15] : 길찾기 API는 지도 앱 메인 화면에서 주요 지역까지의 [소요 시간]과 [거리], [유류비], [통행 요금] 정보를 제공합니다. 그리고 경로상 분기점에서 안내가 필요한 경우 해당 지점에서의 guide code를 제공합니다.
     * @param : List<Map> list
     * @Desc : list 는 존재하는 경유지수만큼 size() 존재. Map 에는 KEY, VALUE 가 (필수)X, Y, (선택)name 으로 있음.
     */
    public  JSONObject getNaverApiDirection15(List<Map<String, Object>> list) {
        //API 콜
        return callApi("DIRECTION15", getNaverApiDirectionContent(list));
    }

    /***
     * 네이버 클라우드 플랫폼
     * [Direction 5] : 길찾기 API는 지도 앱 메인 화면에서 주요 지역까지의 [소요 시간]과 [거리], [유류비], [통행 요금] 정보를 제공합니다. 그리고 경로상 분기점에서 안내가 필요한 경우 해당 지점에서의 guide code를 제공합니다.
     * @param : List<Map> list
     * @Desc : list 는 존재하는 경유지수만큼 size() 존재. Map 에는 KEY, VALUE 가 (필수)X, Y, (선택)name 으로 있음.
     *         (이방식은 방문상세내역의 각 구간마다의 거리데이터, 톨비, 유류비를 구하기 어려움.)
     */
    @SuppressWarnings("unchecked")
    public  JSONObject getNaverApiDirection5(List<Map<String, Object>> list) {
        //API 콜
        return callApi("DIRECTION5", getNaverApiDirectionContent(list));
    }

    /***
     * 네이버 클라우드 플랫폼
     * [Direction 5],[Direction 15] 의 url content 형식이 동일하여 공통으로 사용.
     * @param : List<Map> list
     * @Desc :
     */
    private  String getNaverApiDirectionContent(List<Map<String, Object>> list) {
        String content = "";

        //URL Content 작성.
        //출발지와 도착지는 각각 데이터 1개는 필수며, 착지는 1개이상필요
        //출발지와 도착지는 쿼리로 조회하여, 매장명(name) 사용
        for(int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String coordX = (String)map.get("STARTLATITUDE");
            String coordY = (String)map.get("STARTLONGITUDE");
            String name = "";
            if(map.get("SHOPNM") != null || map.get("SHOPNM") != "") name = (String)map.get("SHOPNM");


            //출발지설정.
            if(i == 0) {
                content += "start=";

                //도착지설정(경유지는 loop 만큼 설정함)
                //설정방법
                //도착지가 아닐경우 경유지임
                //경유지구별코드(:)
            }else if(i == 1) {
                content += "&goal=";
            }else {
                content += ":";
            }

            try {
                content += coordX+","+coordY+(name != "" ? ",name="+ URLEncoder.encode(name, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //완성모양
        //url+ start=127.09091,23.13209183,name=보라매역&goal=127.1230123,23.123123,name=신도림역:127.123123,23.123123,name=구로디지털단지역:127.123123,23.12312321,name=구로역
        return content;
    }

    /***
     * 네이버 클라우드 플랫폼
     * [Direction 5] : 길찾기 API는 지도 앱 메인 화면에서 주요 지역까지의 소요 시간과 거리, 예상 유류비, 통행 요금 정보를 제공합니다. 그리고 경로상 분기점에서 안내가 필요한 경우 해당 지점에서의 guide code를 제공합니다.
     * @param : String startX, String startY, String endX, String endY, String startName, String endName
     * @Desc : 출발지와 도착지1(, 도착지1과 도착지2,....) 존재만큼 상위에서 루프를 돌며, 건by건으로 통신하여 데이터 반환함.
     */
    public  JSONObject getNaverApiDirection5(String startX, String startY, String endX, String endY, String startName, String endName) {

        String content = "";

        try {
            content += "start="+startX+","+startY+(startName != null  ? ",name="+URLEncoder.encode(startName, "UTF-8") : "")
                    +"&goal="+endX+","+endY+(endName != null  ? ",name="+URLEncoder.encode(endName, "UTF-8") : "");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //완성모양
        //url+ start=127.09091,23.13209183,name=보라매역&goal=127.1230123,23.123123,name=신도림역:127.123123,23.123123,name=구로디지털단지역:127.123123,23.12312321,name=구로역

        //API 콜
        return callApi("DIRECTION5", content);
    }

    /***
     * 네이버 클라우드 플랫폼
     * [REVERSE GEOCODING] : 네이버 지도에서는 좌표를 주소를 변환(coordsToAddr)하는 Reverse geocoding 서비스를 제공합니다. Reverse geocoding API를 이용해 특정 좌표에 해당하는 국내 법정동/행정동/지번주소/도로명주소 정보를 얻을 수 있습니다.
     * @param : String coordX, String coordY
     */
    public  JSONObject getNaverApiReverseGeocoding(String coordX, String coordY) {


        //상위메서드에 옮길것.
        if(coordX.isEmpty() || coordY.isEmpty()) System.out.println("에러");

        String content = "output=json&coords="+coordX+","+coordY;

        //완성모양
        //url+ coords=127.09091,23.13209183

        //API 콜
        return callApi("REVERSEGEOCODING", content);
    }


    /***
     * 네이버 클라우드 플랫폼
     * [GEOCODING] : 주소 검색 API는 지번, 도로명을 질의어로 사용해서 주소 정보를 검색합니다. 검색 결과로 주소 목록과 세부 정보를 JSON 형태로 반환합니다.
     * @param : String coordX, String coordY
     */
    public  JSONObject getNaverApiGeocoding(String addr) {


        //상위메서드에 옮길것.
        if(addr.isEmpty()) System.out.println("에러");

        String content = "";
        try {
            content = "query="+URLEncoder.encode(addr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //완성모양
        //url+ query=서울 영등포구 대림로8길 25 서울신대림초등학교

        //API 콜
        return callApi("GEOCODING", content);
    }

    /**
     * API 호출
     * */
    public  JSONObject callApi(String apiKey, String content) {

        //API KEY 정의
        String clientId = getAPIKEY(); //Client Id
        String clientSecret = getAPISECRETKEY(); //Client Secret

        //기본으로 사용 할 변수 선언
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject1 = new JSONObject();
        String innerUrl = getBaseUrl(apiKey);

        //Connection 변수 초기화
        URL obj = null;
        HttpURLConnection con = null;
        BufferedReader errorBr = null;

        try {
            //Full url 정의
            innerUrl = innerUrl + content;

            //connection
            obj = new URL(innerUrl);
            con = (HttpURLConnection) obj.openConnection();

            //connection 옵션 정의
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            con.setRequestMethod("GET"); //*****
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);


            int responseCode = 0;
            String responseMsg = "";

            //통신 response 처리
            responseCode = con.getResponseCode();
            responseMsg = con.getResponseMessage();

            //response 코드, 메세지 저장
            jsonObject.put("responseCode", responseCode);
            jsonObject.put("responseMsg", responseMsg);

            //통신 에러처리(예외처리)(생략)


            //성공
            //System.out.println("성공 입니다. / "+responseCode + " / " + responseMsg);

            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine );
            }
            br.close();
            System.out.println(response.toString());

            jsonObject = (JSONObject) jsonParser.parse(response.toString());
            JSONArray jsonArray = (JSONArray)jsonObject.get("addresses");
            jsonObject1 = (JSONObject)jsonArray.get(0);

            System.out.println(jsonObject1.toString());
            //{"addresses":[{"distance":0.0,"roadAddress":"서울특별시 금천구 금하로23가길 4-2","x":"126.9088726","jibunAddress":"서울특별시 금천구 시흥동 828-4","y":"37.4514632","addressElements":[{"types":["SIDO"],"code":"","shortName":"서울특별시","longName":"서울특별시"},{"types":["SIGUGUN"],"code":"","shortName":"금천구","longName":"금천구"},{"types":["DONGMYUN"],"code":"","shortName":"시흥동","longName":"시흥동"},{"types":["RI"],"code":"","shortName":"","longName":""},{"types":["ROAD_NAME"],"code":"","shortName":"금하로23가길","longName":"금하로23가길"},{"types":["BUILDING_NUMBER"],"code":"","shortName":"4-2","longName":"4-2"},{"types":["BUILDING_NAME"],"code":"","shortName":"","longName":""},{"types":["LAND_NUMBER"],"code":"","shortName":"828-4","longName":"828-4"},{"types":["POSTAL_CODE"],"code":"","shortName":"08574","longName":"08574"}],"englishAddress":"4-2, Geumha-ro 23ga-gil, Geumcheon-gu, Seoul, Republic of Korea"}],"meta":{"count":1,"page":1,"totalCount":1},"errorMessage":"","status":"OK"}
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            con.disconnect();
        }

        return jsonObject1;
    }
}
