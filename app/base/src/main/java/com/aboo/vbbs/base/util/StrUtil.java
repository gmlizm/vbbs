package com.aboo.vbbs.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class StrUtil {

  public static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
      'e', 'f'};
  public static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  public static final Random random = new Random();
  public static final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
  public static final String userNameCheck = "[a-z0-9A-Z]{2,16}";

  public static boolean check(String text, String regex) {
    if (StringUtils.isEmpty(text)) {
      return false;
    } else {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      return matcher.matches();
    }
  }

  /**
   * 随机指定长度的字符串
   *
   * @param length
   * @return
   */
  public static String randomString(int length) {
    StringBuffer sb = new StringBuffer();
    for (int loop = 0; loop < length; ++loop) {
      sb.append(hexDigits[random.nextInt(hexDigits.length)]);
    }
    return sb.toString();
  }

  /**
   * 随机指定长度的数字
   *
   * @param length
   * @return
   */
  public static String randomNumber(int length) {
    StringBuffer sb = new StringBuffer();
    for (int loop = 0; loop < length; ++loop) {
      sb.append(digits[random.nextInt(digits.length)]);
    }
    return sb.toString();
  }

  /**
   * 检测是否是用户accessToken
   */
  public static boolean isUUID(String accessToken) {
    if (StringUtils.isEmpty(accessToken)) {
      return false;
    } else {
      try {
        // noinspection ResultOfMethodCallIgnored
        UUID.fromString(accessToken);
        return true;
      } catch (Exception e) {
        return false;
      }
    }
  }


  /**
   * remove duplicate
   *
   * @param strs
   * @return
   */
  public static List<String> removeDuplicate(String[] strs) {
    List<String> list = new ArrayList<>();
    for (String s : strs) {
      if (!list.contains(s)) {
        list.add(s);
      }
    }
    return list;
  }
  
  
  /**
   * 查找一段文本里以 @ 开头的字符串
   *
   * @param str
   * @return
   */
  public static List<String> fetchAt(String pattern, String str) {
    List<String> ats = new ArrayList<>();
    if (StringUtils.isEmpty(pattern))
      pattern = "@[^\\s]+\\s?";
    Pattern regex = Pattern.compile(pattern);
    Matcher regexMatcher = regex.matcher(str);
    while (regexMatcher.find()) {
      ats.add(regexMatcher.group());
    }
    return ats;
  }

}
