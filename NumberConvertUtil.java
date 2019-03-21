import java.util.HashMap;
import java.util.Map;

/** @author zctmdc */
public class NumberConvertUtil {

  /** 中文数对于阿拉伯数字map */
  private static final Map<Character, Integer> numberMap;
  /** 数量级对应阿拉伯数map */
  private static Map<String, Long> magnitudeMap;

  /** 大写中文数字符数组 */
  static final char[] UPPER_NUMBER = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
  /** 小写中文数字符数组 */
  static final char[] LOWER_NUMBER = {'〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
  /** 阿拉伯数字符数组 */
  static final char[] ARABIC_NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  /** 量级 */
  private static final char[] MAGNITUDES = {'亿', '万', '千', '百', '十'};

  static {
    numberMap = new HashMap<>();
    /*
     * numberMap.put('〇', 0);
     * numberMap.put('一', 1);
     * numberMap.put('二', 2);
     * numberMap.put('三', 3);
     * numberMap.put('四', 4);
     * numberMap.put('五', 5);
     * numberMap.put('六', 6);
     * numberMap.put('七', 7);
     * numberMap.put('八', 8);
     * numberMap.put('九', 9);
     */
    for (int i = 0; i < UPPER_NUMBER.length; i++) {
      numberMap.put(UPPER_NUMBER[i], i);
    }
    /*
     * numberMap.put('零', 0);
     * numberMap.put('壹', 1);
     * numberMap.put('贰', 2);
     * numberMap.put('叁', 3);
     * numberMap.put('肆', 4);
     * numberMap.put('伍', 5);
     * numberMap.put('陆', 6);
     * numberMap.put('柒', 7);
     * numberMap.put('捌', 8);
     * numberMap.put('玖', 9);
     */
    for (int i = 0; i < LOWER_NUMBER.length; i++) {
      numberMap.put(LOWER_NUMBER[i], i);
    }
    magnitudeMap = new HashMap<>();
    magnitudeMap.put("十", 10L);
    magnitudeMap.put("百", 100L);
    magnitudeMap.put("千", 1000L);
    magnitudeMap.put("万", 10000L);
    magnitudeMap.put("亿", 100000000L);
  }

  /**
   * 将中文格式数字转换为阿拉伯格式数字
   *
   * @param chineseNum 中文格式数字
   * @return 阿拉伯格式数字
   * @throws RuntimeException
   */
  public static Long cn2Arabic(String chineseNum) throws RuntimeException {
    if (chineseNum == null) {
      throw new NullPointerException();
    }
    try {
      checkOK(chineseNum);
    } catch (NumberFormatException e) {
      throw e;
    }
    for (int i = 0; i < MAGNITUDES.length - 3; i++) { // 跳过千, 百, 十
      String magnitude = String.valueOf(MAGNITUDES[i]); // 按照单位从大到小分割
      if (chineseNum.contains(magnitude)) { // 如果遇到“亿”，“万”，则先分割开，再算
        return magnitudeConvert(chineseNum, magnitude);
      }
    }
    return tdConvert(chineseNum); // 看起来是不包含了，直接算。
  }

  /**
   * 根据传入的字符切断字符串，分别计算
   *
   * @param chineseNum
   * @param magnitude
   * @return
   */
  private static Long magnitudeConvert(String chineseNum, String magnitude) {
    String[] ts = chineseNum.split(magnitude);
    long value = cn2Arabic(ts[0]) * magnitudeMap.get(magnitude); // 继续判断，转换，再乘当前量级
    return ts.length == 2 ? value + cn2Arabic(ts[1]) : value; // 是不是有两部分，是的话，加起来
  }

  /**
   * 万内转换
   *
   * @param numberText
   * @return
   */
  private static Long tdConvert(String numberText) {
    long value = 0L;
    char c;
    String str;
    for (int i = 0; i < numberText.length(); i++) { // 遍历传入字符串
      c = numberText.charAt(i);
      str = String.valueOf(c);
      if (magnitudeMap.containsKey(str)) { // 如果遇到量级单位，递归计算
        return value * magnitudeMap.get(str) + tdConvert(numberText.substring(i + 1));
      }
      value = value * 10 + numberMap.get(c);
    }
    return value;
  }

  private static void checkOK(String chineseNum) throws RuntimeException {
    for (int i = 0; i < chineseNum.length(); i++) {
      char c = chineseNum.charAt(i);
      String up = new String(UPPER_NUMBER);
      String low = new String(LOWER_NUMBER);
      String m = new String(MAGNITUDES);
      if (up.indexOf(c) == -1 && low.indexOf(c) == -1 && m.indexOf(c) == -1) {
        throw new NumberFormatException("不支持字符： " + c);
      }
    }
  }
}
