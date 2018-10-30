package com.aboo.vbbs.serv.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class UserUtil {

	  /**
	   * 查找一段文本里以 @ 开头的字符串
	   *
	   * @param str
	   * @return
	   */
	  public static List<String> fetchUsers(String pattern, String str) {
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
