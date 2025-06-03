package main.DAO;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Utillity {

  // 오늘 날짜를 반환하는 메소드
  public static LocalDate getTodayLocalDate(){
    return LocalDate.now();
  }
  public static String generateID(char startChar) {
    SecureRandom RANDOM = new SecureRandom();
    StringBuilder sb = new StringBuilder(9);
    for (int i = 0; i < 9; i++) {
      // CHARACTERS 문자열에서 랜덤하게 한 문자 선택
      int randomIndex = RANDOM.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length());
      sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(randomIndex));
    }
    System.out.println(startChar+sb.toString());
    return startChar+sb.toString();
  }

  public static String map2Json(HashMap<String, String> map) {
    StringBuilder jsonBuilder = new StringBuilder();
    jsonBuilder.append("{");
    int count = 0;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String key = entry.getKey().replace("\"", "\\\"");
      String value = entry.getValue().replace("\"", "\\\"");
      jsonBuilder.append("\"").append(key).append("\":")
          .append("\"").append(value).append("\"");
      if (++count < map.size()) {
        jsonBuilder.append(",");
      }
    }
    jsonBuilder.append("}");
    return jsonBuilder.toString();
  }

  // JSON 문자열을 HashMap<String, String>으로 되돌리는 메소드 (제한적)
  public static HashMap<String, String> json2Map(String jsonString) {
    HashMap<String, String> map = new HashMap<>();

    if (jsonString == null || jsonString.trim().isEmpty() || !jsonString.startsWith("{") || !jsonString.endsWith("}")) {
      // 유효하지 않은 JSON 형식 처리
      return map;
    }
    String content = jsonString.substring(1, jsonString.length() - 1);
    String[] pairs = content.split(",");
    for (String pair : pairs) {
      int colonIndex = pair.indexOf(":");
      if (colonIndex == -1) {
        continue; // 유효하지 않은 쌍 건너뛰기
      }
      String keyPart = pair.substring(0, colonIndex).trim();
      String valuePart = pair.substring(colonIndex + 1).trim();
      String key = unescapeJsonString(removeQuotes(keyPart));
      String value = unescapeJsonString(removeQuotes(valuePart));
      map.put(key, value);
    }
    return map;
  }
  private static String removeQuotes(String str) {
    if (str.startsWith("\"") && str.endsWith("\"")) {
      return str.substring(1, str.length() - 1);
    }
    return str;
  }
  private static String unescapeJsonString(String str) {
    return str.replace("\\\"", "\"");
  }

}