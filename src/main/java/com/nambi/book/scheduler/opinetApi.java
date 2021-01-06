package com.nambi.book.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class opinetApi extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }

//    private static final Log LOG = LogFactory.getLog(OpinetAvgWeekPrice.class);

//    @Override
//    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {

//
//        LOG.debug("=======================================================================");
//        LOG.debug("오피넷 단가 수신 JOB");
//        LOG.debug("FireInstance Id :: " + ctx.getFireInstanceId());
//        LOG.debug("Worker Thread :: " + Thread.currentThread().getName());
//        LOG.debug("=======================================================================");
//
//        SqlManager sqlManager = SqlManagerFactory.getSqlManager();
//
//        //params put
//        Params params = new CommParams();
//        try {
//
//            //동원홈푸드 code
//            String code = "F661191213";
//            //오피넷 경유
//            String prodcd = "D047";
//
////	      sido=02 경기도
////			2100DC 시화 경기도 시흥시 오이도로 22-12                   sigun:0216      시흥
////			2400DC 이천물류센터 경기도 이천시 호법면 중부대로763번길 140     sigun:0208      이천
////	        2600DC 일죽물류센터 경기도 안성시 일죽면 방초리 221-5          sigun:0235   안성
//
////	      sido=07 전남
////			2300DC 호남 전라남도 담양군 대덕면 남대덕로 1109              sigun:0722     전남담양군
//
////	      sido=09 경남
////			2200DC 양산 경상남도 양산시 어곡공단2길 6                   sigun:0911     경남양산시
//
////	      sido=14 대구                                                                             sigun:1403   대구서구
////			2500DC 대구물류센터 대구광역시 서구 평리로21길 21
//
//            //시도구분
//            String sidoGyeonggiDo 	= "02";	//경기도
//            String jeonNam 			= "07"; //전남
//            String gyeongNam		= "09"; //경남
//            String daeGu   			= "14"; //대구
//
//            //sigunnm
//            String siHeung          = "0216"; //시흥시
//            String icHeon           = "0208"; //이천시
//            String anSeong          = "0235"; //안성시
//
//            String damYang          = "0722"; //담양군
//            String yangSan          = "0911"; //양산시
//            String daeGuSegu        = "1407"; //대구서구
//
//            //out Type xml&json
//            String out = "json";
//
////			String url = "http://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code=F661191213&prodcd=D047";
////			String "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=14";
//
//            /** 전주 단위로 수정 2020.05.07 jhlee
//             String siHeungUrl = "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=02&sigun=0216";
//             String icHeonUrl  = "http://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code=F661191213&prodcd=D047&sido=02"; //수정 시도별로 2020.04.28 일죽센터장 요청(여태준)
//             //			String icHeonUrl  = "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=02&sigun=0208";
//             String anSeongUrl = "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=02&sigun=0235";
//
//             String damYangUrl 	= "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=07&sigun=0722";
//             String yangSanUrl  	= "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=09&sigun=0911";
//             String daeGuSeguUrl = "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=14&sigun=1407";
//             **/
//
//            String siHeungUrl = "http://www.opinet.co.kr/api/avgLastWeek.do?out=json&code=F661191213&prodcd=D047"; //전국으로만 수정 2020.05.22 일죽센터자 여태준
//            String icHeonUrl  = "http://www.opinet.co.kr/api/avgLastWeek.do?out=json&code=F661191213&prodcd=D047&sido=02"; //수정 시도별로 2020.04.28 일죽센터장 요청(여태준)
////			String icHeonUrl  = "http://www.opinet.co.kr/api/avgSigunPrice.do?out=json&code=F661191213&prodcd=D047&sido=02&sigun=0208";
//            String anSeongUrl = "http://www.opinet.co.kr/api/avgLastWeek.do?out=json&code=F661191213&prodcd=D047&sido=02";
//
//            String damYangUrl 	= "http://www.opinet.co.kr/api/avgLastWeek.do?out=json&code=F661191213&prodcd=D047&sido=07";
//            String yangSanUrl  	= "http://www.opinet.co.kr/api/avgLastWeek.do?out=json&code=F661191213&prodcd=D047&sido=09";
//            String daeGuSeguUrl = "http://www.opinet.co.kr/api/avgLastWeek.do?out=json&code=F661191213&prodcd=D047&sido=14";
//
//
//            //시흥 건만
//            URL objSiHeung = new URL(siHeungUrl);
//            HttpURLConnection conSiHeung = (HttpURLConnection) objSiHeung.openConnection();
//            conSiHeung.setRequestMethod("POST");
//            Charset charset2 = Charset.forName("UTF-8");
//            BufferedReader inSiHeung = new BufferedReader(new InputStreamReader(conSiHeung.getInputStream(),charset2));
////	        JSONObject repjsonObjectSiHeung = new JSONObject();
//            JSONParser jsonParserSiHeung = new JSONParser();
//            org.json.simple.JSONObject repjsonObjectSiHeung = (org.json.simple.JSONObject)jsonParserSiHeung.parse(inSiHeung);
//            inSiHeung.close();
//
//            //이천
//            URL objIcHeon = new URL(icHeonUrl);
//            HttpURLConnection conIcHeon = (HttpURLConnection) objIcHeon.openConnection();
//            conIcHeon.setRequestMethod("POST");
////	        Charset charset2 = Charset.forName("UTF-8");
//            BufferedReader inIcHeon = new BufferedReader(new InputStreamReader(conIcHeon.getInputStream(),charset2));
////	        JSONObject repjsonObjectIcHeon = new JSONObject();
//            JSONParser jsonParserIcHeon = new JSONParser();
//            org.json.simple.JSONObject repjsonObjectIcHeon = (org.json.simple.JSONObject) jsonParserIcHeon.parse(inIcHeon);
//            inIcHeon.close();
//
//            //안성
//            URL objAnSeong = new URL(anSeongUrl);
//            HttpURLConnection conAnSeong = (HttpURLConnection) objAnSeong.openConnection();
//            conAnSeong.setRequestMethod("POST");
////	        Charset charset2 = Charset.forName("UTF-8");
//            BufferedReader inAnSeong = new BufferedReader(new InputStreamReader(conAnSeong.getInputStream(),charset2));
////	        JSONObject repjsonObjectAnSeong = new JSONObject();
//            JSONParser jsonParserAnSeong = new JSONParser();
//            org.json.simple.JSONObject repjsonObjectAnSeong = (org.json.simple.JSONObject) jsonParserAnSeong.parse(inAnSeong);
//            inAnSeong.close();
//
//            //담양
//            URL objDamYang= new URL(damYangUrl);
//            HttpURLConnection conDamYang= (HttpURLConnection) objDamYang.openConnection();
//            conDamYang.setRequestMethod("POST");
////	        Charset charset2 = Charset.forName("UTF-8");
//            BufferedReader inDamYang= new BufferedReader(new InputStreamReader(conDamYang.getInputStream(),charset2));
////	        JSONObject repjsonObjectDamYang= new JSONObject();
//            JSONParser jsonParserDamYang= new JSONParser();
//            org.json.simple.JSONObject repjsonObjectDamYang= (org.json.simple.JSONObject) jsonParserDamYang.parse(inDamYang);
//            inDamYang.close();
//
//            //양산
//            URL objYangSan= new URL(yangSanUrl);
//            HttpURLConnection conYangSan= (HttpURLConnection) objYangSan.openConnection();
//            conYangSan.setRequestMethod("POST");
////	        Charset charset2 = Charset.forName("UTF-8");
//            BufferedReader inYangSan= new BufferedReader(new InputStreamReader(conYangSan.getInputStream(),charset2));
////	        JSONObject repjsonObjectYangSan= new JSONObject();
//            JSONParser jsonParserYangSan= new JSONParser();
//            org.json.simple.JSONObject repjsonObjectYangSan= (org.json.simple.JSONObject) jsonParserYangSan.parse(inYangSan);
//            inYangSan.close();
//
//            //대구서구
//            URL objDaeGuSegu= new URL(daeGuSeguUrl);
//            HttpURLConnection conDaeGuSegu= (HttpURLConnection) objDaeGuSegu.openConnection();
//            conDaeGuSegu.setRequestMethod("POST");
////	        Charset charset2 = Charset.forName("UTF-8");
//            BufferedReader inDaeGuSegu= new BufferedReader(new InputStreamReader(conDaeGuSegu.getInputStream(),charset2));
////	        JSONObject repjsonObjectDaeGuSegu= new JSONObject();
//            JSONParser jsonParserDaeGuSegu= new JSONParser();
//            org.json.simple.JSONObject repjsonObjectDaeGuSegu= (org.json.simple.JSONObject) jsonParserDaeGuSegu.parse(inDaeGuSegu);
//            inDaeGuSegu.close();
//
//            //센터 조회
//            DataTable dt = sqlManager.selectDataTable("OpinetAvgWeekPrice.selectOpinetCenter");
//
//            //시흥
//            org.json.simple.JSONArray opiOilSiHeung = (org.json.simple.JSONArray) ((org.json.simple.JSONObject)repjsonObjectSiHeung.get("RESULT")).get("OIL");
//            System.out.println("opiJsonSiHeungString==>"+opiOilSiHeung.toString());
//
//            //이천
//            org.json.simple.JSONArray opiOilIcHeon = (org.json.simple.JSONArray) ((org.json.simple.JSONObject)repjsonObjectIcHeon.get("RESULT")).get("OIL");
//            System.out.println("opiJsonIcHeonString==>"+opiOilIcHeon.toString());
//
//            //안성
//            org.json.simple.JSONArray opiOilAnSeong = (org.json.simple.JSONArray) ((org.json.simple.JSONObject)repjsonObjectAnSeong.get("RESULT")).get("OIL");
//            System.out.println("opiJsonAnSeongString==>"+opiOilAnSeong.toString());
//
//            //담양
//            org.json.simple.JSONArray opiOilDamYang = (org.json.simple.JSONArray) ((org.json.simple.JSONObject)repjsonObjectDamYang.get("RESULT")).get("OIL");
//            System.out.println("opiJsonDamYangString==>"+opiOilDamYang.toString());
//
//            //양산
//            org.json.simple.JSONArray opiOilYangSan = (org.json.simple.JSONArray) ((org.json.simple.JSONObject)repjsonObjectYangSan.get("RESULT")).get("OIL");
//            System.out.println("opiJsonYangSanString==>"+opiOilYangSan.toString());
//
//            //대구서구
//            org.json.simple.JSONArray opiOilDaeGuSegu = (org.json.simple.JSONArray) ((org.json.simple.JSONObject)repjsonObjectDaeGuSegu.get("RESULT")).get("OIL");
//            System.out.println("opiJsonYDaeGuSegutring==>"+opiOilDaeGuSegu.toString());
//
//            Double siHeungMinus=(double) 70; //수정 주유값 마이너스 70원 2020.04.28 일죽센터장 요청(여태준)
//            Double AnSeongMinus=(double) 40; //수정 주유값 마이너스 40원 2020.04.28 일죽센터장 요청(여태준)
//
//
//            String thisDay = new java.text.SimpleDateFormat ("yyyyMMdd").format(new java.util.Date());
//
//            //있냐 없냐 조회 cnt
////			Params opiNetCnt = sqlManager.selectOneParams("OpinetAvgWeekPrice.selectOpiCnt", params);
//
//            int opiNetCnt = sqlManager.selectOne("OpinetAvgWeekPrice.selectOpiCnt", params);
//
//            LOG.debug("opiNetCnt======="+opiNetCnt);
////			int opiCnt = (int) opiNetCnt.getParam("CNT");
//
////			System.out.println("opiNetCnt==>"+opiCnt);
//            if (opiNetCnt ==0){
//
//                //센터별 6개 센터 주유단가 for
//                for(DataRow dr : dt){
//                    //1.체크 데이터 현재 날짜에서 ++7까지의 카운트 체크
//
//
//                    if(
//                            "2100DC".equals(dr.get("CENTER_CD")) || "2400DC".equals(dr.get("CENTER_CD")) || "2600DC".equals(dr.get("CENTER_CD"))||
//                                    "2300DC".equals(dr.get("CENTER_CD")) || "2200DC".equals(dr.get("CENTER_CD")) || "2500DC".equals(dr.get("CENTER_CD")))
//                    {
//
//
//                        //날짜 일주일치 만큼 for
//                        for (int j = 0; j < 7; j++) {
//
//                            SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
//                            Date currentTime = new Date();
//
//                            // 날짜 더하기
//                            Calendar cal = Calendar.getInstance();
//                            cal.setTime(currentTime);
//                            cal.add(Calendar.DATE, j);		//날짜 더하기
//                            //				        cal.add(Calendar.MONTH, 1);		//월 더하기
//
//                            System.out.println("날짜 7일 더하기"+formatter.format(cal.getTime()));
//
//                            params.setParam("centerCd", dr.get("CENTER_CD"));
//                            //						params.setParam("oilPriceAvr", 1000);
//                            params.setParam("deliveryDate", formatter.format(cal.getTime()));
//
//                            if ("2100DC".equals(dr.get("CENTER_CD"))){
//
//                                for(int i=0;i<opiOilSiHeung.size();i++){
//
//                                    org.json.simple.JSONObject oil = (org.json.simple.JSONObject) opiOilSiHeung.get(i);
//                                    System.out.println("SiHeungprice==>"+oil.get("PRICE"));
//                                    params.setParam("oilPriceAvr",(Double) oil.get("PRICE")-siHeungMinus);
//                                }//시흥 for end
//
//                            }//2100DC end
//
//                            if ("2400DC".equals(dr.get("CENTER_CD"))){
//
//                                for(int i=0;i<opiOilIcHeon.size();i++){
//
//                                    org.json.simple.JSONObject oil = (org.json.simple.JSONObject) opiOilIcHeon.get(i);
//                                    System.out.println("IcHeonprice==>"+oil.get("PRICE"));
//                                    params.setParam("oilPriceAvr",(Double) oil.get("PRICE"));
//                                }//시흥 for end
//
//                            }//2600DC end
//
//                            if ("2600DC".equals(dr.get("CENTER_CD"))){
//
//                                for(int i=0;i<opiOilAnSeong.size();i++){
//
//                                    org.json.simple.JSONObject oil = (org.json.simple.JSONObject) opiOilAnSeong.get(i);
//                                    System.out.println("AnSeongprice==>"+oil.get("PRICE"));
//                                    params.setParam("oilPriceAvr",(Double) oil.get("PRICE")-AnSeongMinus);
//                                }//안성 for end
//
//                            }//2600DC end
//
//                            if ("2300DC".equals(dr.get("CENTER_CD"))){
//
//                                for(int i=0;i<opiOilDamYang.size();i++){
//
//                                    org.json.simple.JSONObject oil = (org.json.simple.JSONObject) opiOilDamYang.get(i);
//                                    System.out.println("DamYangprice==>"+oil.get("PRICE"));
//                                    params.setParam("oilPriceAvr",(Double) oil.get("PRICE"));
//                                }//담양 for end
//
//                            }//2300DC end
//
//                            if ("2200DC".equals(dr.get("CENTER_CD"))){
//
//                                for(int i=0;i<opiOilYangSan.size();i++){
//
//                                    org.json.simple.JSONObject oil = (org.json.simple.JSONObject) opiOilYangSan.get(i);
//                                    System.out.println("YangSanprice==>"+oil.get("PRICE"));
//                                    params.setParam("oilPriceAvr",(Double) oil.get("PRICE"));
//                                }//담양 for end
//
//                            }//2200DC end
//
//                            if ("2500DC".equals(dr.get("CENTER_CD"))){
//
//                                for(int i=0;i<opiOilDaeGuSegu.size();i++){
//
//                                    org.json.simple.JSONObject oil = (org.json.simple.JSONObject) opiOilDaeGuSegu.get(i);
//                                    System.out.println("DaeGuSeguprice==>"+oil.get("PRICE"));
//                                    params.setParam("oilPriceAvr",(Double) oil.get("PRICE"));
//
//                                }//대구 for end
//
//                            }//2500DC end
//
//
//                            //오피넷 단가 저장 처리
//                            sqlManager.insert("OpinetAvgWeekPrice.insertOpi", params);
//                        }
//
//
//                    }
//
//                    //insert pDate+7일 화요일 ~ 월요일까지 데이터
//
//                }
//
//
//
//            }//opiCnt End
//
//        } catch (Exception e){
//            e.printStackTrace();
//            LOG.debug("emessage"+e.getMessage());
//        }
//    }
}
