import org.junit.jupiter.api.Test;

import NumberConvertUtil;
/**
* https://github.com/alvin198761/c2number/blob/master/src/org/alvin/c2number/C2Number_Test.java
*/
public class ChineseNumTestCase {
  @Test
  void testName02() throws Exception {
    String text = "一亿四千三百二十五万二千一百五十四";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "一亿〇二千〇四";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "一亿〇二千一百五十四";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "八千三百亿〇二千一百五十四";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "八十亿三千万";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "三千万";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "八百〇一";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "一千八百〇一亿〇五";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "二〇一四";
    System.out.println(NumberConvertUtil.cn2Arabic(text));
    text = "二〇一四年";
    System.out.println(NumberConvertUtil.cn2Arabic(text));//Exception
  }
}
