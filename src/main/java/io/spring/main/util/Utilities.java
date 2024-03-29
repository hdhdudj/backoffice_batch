package io.spring.main.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 21-05-26 Pecan
 * 유틸 class : 여러 service에서 공통적으로 쓰일 편의 함수 모음 클래스
 */
@Slf4j
public class Utilities {
    /**
     * 21-04-25 Pecan
     * 유틸 함수 : "009"를 받아 정수화해서 1을 더한 후 "010"으로 return
     * @param calcNeedStringNumber
     * @param length
     * @return String
     */
    public static String plusOne(String calcNeedStringNumber, int length){ // 들어온 string의 숫자는 정수여야 함
        if(calcNeedStringNumber == null){
            return null;
        }
        String calcRes = "";
        try{
            calcRes = StringUtils.leftPad(Long.toString((long)Double.parseDouble(calcNeedStringNumber) + 1), length, '0');
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        return calcRes;
    }
    /**
     * 21-04-25 Pecan
     * 유틸 함수 : char가 앞에 붙은 숫자 반환. (예 : C00000001 )
     * @param calcNeedStringNumber
     * @param length
     * @return String
     */
    public static String getStringNo(char alphabet, String calcNeedStringNumber, int length){ // 들어온 string의 숫자는 정수여야 함
        if(calcNeedStringNumber == null){
            return null;
        }
        String calcRes = "";
        try{
            calcRes = org.flywaydb.core.internal.util.StringUtils.leftPad(Long.toString(Long.parseLong(calcNeedStringNumber)), length - 1, '0');
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        calcRes = alphabet + calcRes;
        return calcRes;
    }
    /**
     * 21-05-04 Pecan
     * 유틸 함수 : "9999-12-31 23:59:59"를 yyyy-MM-dd HH:mm:ss꼴 Date로 반환
     * @return Date
     */
    public static Date getStringToDate(String strDate){
        Date getDate = null;
        try{
            getDate = new SimpleDateFormat(StringFactory.getDateTimeFormat()).parse(strDate);
        }
        catch(Exception e){
            log.debug(e.getMessage());
        }
        return getDate;
    }

    /**
     * String 날짜(yyyy-MM-dd HH:mm:ss 꼴)를 받아서 LocalDateTime으로 변환해 반환하는 함수
     * @param strDt
     * @return LocalDateTime
     */
    public static LocalDateTime strToLocalDateTime(String strDt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        return LocalDateTime.parse(strDt, formatter);
    }

    /**
     * LocalDateTime을 받아서 T를 뗀 String(1111-11-11 11:11:11 꼴)을 반환하는 함수
     * @return String
     */
    public static String removeTAndTransToStr(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String strDate = localDateTime.format(formatter);
        return strDate.replace('T', ' ');
    }

    /**
     * 21-05-04 Pecan
     * 유틸 함수 : Date "9999-12-31 23:59:59"를 String으로 반환
     * @return Date
     */
    public static String getDateToString(String dateFormat, Date strDate){
        String str = new java.text.SimpleDateFormat(dateFormat).format(strDate);
//        System.out.println("date -------------------- " + str);
        return str;
    }

    /**
     * mode : Calendar.DATE, Calendar.MONTH 등...
     * n : +는 현재 날짜에서 더하기, -는 현재 날짜에서 빼기
     */
    public static String getAnotherDate(String startDt, String dataFormat, int mode, int n){
        //한달 전
        DateFormat form = new java.text.SimpleDateFormat(dataFormat);
        Calendar mon = Calendar.getInstance();
        if(startDt != null){
            try{
                Date startDt2 = form.parse(startDt);
                mon.setTime(startDt2);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        mon.add(mode , n);
        String beforeMonth = form.format(mon.getTime());
        return beforeMonth;
    }

//    /**
//     * 'y'는 '01'로, 'n'은 '02'로 변환
//     */
//    public static String ynToOneTwo(String yn){
//        String returnStr = null;
//        if(yn == null){
//        }
//        else if(yn.equals(StringFactory.getStrY())){ // 'y'
//            returnStr = StringFactory.getGbOne();
//        }
//        else if(yn.equals(StringFactory.getStrN())){ // 'n'
//            returnStr = StringFactory.getGbTwo();
//        }
//        else if(yn.equals(StringFactory.getStrLight())){ // 'light'
//            returnStr = StringFactory.getGbOne();
//        }
//        else if(yn.equals(StringFactory.getStrFurn())){ // 'furn' 02
//            returnStr = StringFactory.getGbTwo();
//        }
//        return returnStr;
//    }

    public static Map<String, Object> makeStringToMap(String addField) {
        if(addField == null || addField.trim().equals("") || addField.equals("[]")){
            log.debug("Utilities.makeStringToMap : input String이 null이거나 공백입니다.");
            return null;
        }
        Map<String, Object> map = new HashMap<>(); // convert JSON string to Map
        try{
            map = new ObjectMapper().readValue(addField, new TypeReference<Map<String, Object>>(){});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }

	public static String filterBadXMLCharactors(String textToFilter) {
		StringBuffer filteredString = new StringBuffer();
		char current;

		if (textToFilter == null || "".equals(textToFilter)) {
			return "";
		}

		for (int i = 0; i < textToFilter.length(); i++) {
			current = textToFilter.charAt(i);

			if ((current == 0x9) || //
					(current == 0xA) || //
					(current == 0xD) || //
					((current >= 0x20) && (current <= 0xD7FF)) || //
					((current >= 0xE000) && (current <= 0xFFFD)) || //
					((current >= 0x10000) && (current <= 0x10FFFF))) {
				filteredString.append(current);
			}
		}
		return filteredString.toString();
	}

    /**
     * Date를 localDateTime으로 변환
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        if(date == null){
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
