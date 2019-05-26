package cn.xxm.ad.common.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.DecoderException;


/**
 * 字符串工具类
 *
 */
public class StringUtil {
	private static final Log logger = LogFactory.getLog(StringUtil.class);
	
	public static String substituteParams(String msgtext, Object params[]) {
		if (params == null || msgtext == null) {
			return msgtext;
		}
		MessageFormat mf = new MessageFormat(msgtext);
		return mf.format(params);
	}

	/**
	 * 
	 * 如果target字符串为null 就将默认值default返回
	 * @param target
	 * @param defaultStr
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2019年1月7日    	     徐晓鸣    新建
	 * </pre>
	 */
	public static String defaultNull(String target,String defaultStr) {
		if(StringUtil.isEmpty(target) && null != defaultStr) {
			return defaultStr;
		}
		return target;
	}
	
	/**
	 * 日期格式转换
	 * 
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String formatDate(Date date, String formatString) {
		try {
			if( isEmpty(formatString) ) //如果为空默认为yyyyMMddHHmmss
			{
				formatString = "yyyyMMddHHmmss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			return sdf.format(date);
		} catch (Exception e) {
			logger.error("ServiceRouter.formatDate() error!", e);
			return date.toString();
		}
	}
	
	/**
	 * 日期格式转换
	 * 
	 * @param dateStr
	 * @param formatString
	 * @return
	 */
	public static Date toDate(String dateStr, String formatString) {
		try {
			if( isEmpty(formatString) ) //如果为空默认为yyyyMMddHHmmss
			{
				formatString = "yyyyMMddHHmmss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			logger.error("ServiceRouter.formatDate() error!", e);
		}
		return null;
	}
	/**
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String isoToGBK(String str) {
		if (str == null) {
			return "";
		}
		try {
			byte[] bytes = str.getBytes("iso-8859-1");
			String destStr = new String(bytes, "GBK");
			return destStr;
		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}

	/**
	 * 转换指定字符串的编码
	 * 
	 * @param str
	 * @param fromEncoding
	 * @param toEncoding
	 * @return
	 */
	public static String convert(String str, String fromEncoding,
			String toEncoding) {
		if (str == null) {
			return "";
		}
		try {
			byte[] bytes = str.getBytes(fromEncoding);
			String destStr = new String(bytes, toEncoding);
			return destStr;
		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}

	public static String toUnicode(String text) {
		if (text == null){
			return "";
		}
		char chars[] = text.toCharArray();
		StringBuffer sb = new StringBuffer();
		int length = chars.length;
		for (int i = 0; i < length; i++) {
			int s = chars[i];
			sb.append("&#");
			sb.append(s);
			sb.append(";");
		}

		return sb.toString();
	}
	/**  
     * 删除input字符串中的html格式  
     * @author lsm
     *   
     * @param input  
     * @param length  
     * @return  
     */  
    public static String splitAndFilterString(String input, int length)
    {
        if (input == null || input.trim().equals(""))
        {
            return "";
        }
        input = input.replaceAll("\\\\r", "").replaceAll("\\\\n", "").replaceAll("\\\\t", "");
        input = input.replaceAll("<\\\\/", "");
        input = input.replaceAll("&nbsp;", "");
        input = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        input = input.replaceAll("[(/>)<]", "");
        //如果包含汉字，截取长度为length 
        
        int len = input.length();
        if(chinese(input))
        {
            if (len <= 20)
            {
                return input;
            } else
            {
                input = input.substring(0, 20);
                input += "......";
            }
            
        }else
        {
            if (len <=  length)
            {
                return input;
            } else
            {
                input = input.substring(0, length);
                input += "......";
            }
        }
       
        return input;
    }
	/**
	 * 检测字符串里是否有中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean chinese(String str) {
		if (str == null) {
			return false;
		}
		String regex = "[\u0391-\uFFE5]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean validate = m.matches();
		return validate;
	}

	

	
	
	/**
	 * 格式化Object对象为字符串，如果为null则返回空白.
	 * 
	 * @param str
	 * @return str
	 */
	public static String trim(Object str) {
		return (str == null) ? "" : str.toString().trim();
	}
	
	/**
	 * 检测输入的邮政编码是否合法
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isPostCode(String code) {
		if (code == null) {
			return false;
		}
		String regex = "[1-9]\\d{5}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(code);
		boolean validate = m.matches();
		return validate;
	}

//	/**
//	 * 检测字符串是否为空，或者空字符串
//	 * 
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static boolean isEmpty(String str) {
//		str = StringUtil.nullStringToEmptyString(str);
//		String regex = "\\s*";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(str);
//		boolean validate = m.matches();
//		return validate;
//	}

	/**
	 * 字符串是否是"nul"字符串
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || "null".equals(str) ) {
			return true;
		}
		return false;
	}

	/**
	 * 将"null"字符串或者null值转换成""
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStringToEmptyString(String str) {
		if (str == null) {
			str = "";
		}
		if ("null".equals(str)) {
			str = "";
		}
		return str;
	}

	/**
	 * 将"null"字符串或者null值转换成""
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStringToSetString(String str) {
		if (StringUtil.isEmpty(str)) {
			str = "设置";
		}
		if (str == null) {
			str = "设置";
		}
		if ("null".equals(str)) {
			str = "设置";
		}
		return str;
	}

	/**
	 * 将"null"字符串或者null值转换成""
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStringToUnknowString(String str) {
		if (str == null) {
			str = "未知";
		}
		if ("null".equals(str)) {
			str = "未知";
		}
		return str;
	}

	/**
	 * 屏掉WML不支持的代码
	 * 
	 * @param str
	 * @return
	 */
	public static String wmlEncode(String str) {
		if (str == null){
			return "";
		}
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "&apos;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("\n", "<br/>");
		str = str.replaceAll("<br>", "<br/>");
		return str;
	}

	/**
	 * 将字节转换成16进制
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * 是否是数字
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		str = StringUtil.nullStringToEmptyString(str);
		String regex = "\\d+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean validate = m.matches();
		return validate;
	}

	/**
	 * 检查书的ISBN号是否合法
	 * 
	 * 
	 * @param isbn
	 * @return
	 */
	public static boolean isISBN(String isbn) {
		if (StringUtil.isEmpty(isbn)) {
			return false;
		}
		int len = isbn.length();
		if (len != 13) {
			return false;
		}
		String[] splits = isbn.split("-");
		len = splits.length;
		if (len != 4) {
			return false;
		}
		len = splits[0].length();
		if (len < 1 || len > 5) {
			return false;
		}
		len = splits[1].length();
		if (len < 2 || len > 5) {
			return false;
		}
		len = splits[2].length();
		if (len < 1 || len > 6) {
			return false;
		}
		len = splits[3].length();
		if (len != 1) {
			return false;
		}
		String realISBN = isbn.replaceAll("-", "");
		char[] numbers = realISBN.toCharArray();
		int sum = 0;
		for (int i = 10; i > 1; i--) {
			int index = 10 - i;
			int number = Integer.parseInt(String.valueOf(numbers[index]));
			sum = sum + number * i;
		}
		int code = 11 - (sum % 11);
		String codeStr = String.valueOf(code);
		if (code == 10) {
			codeStr = "X";
		}
		if (!splits[3].equals(codeStr)) {
			return false;
		}
		return true;
	}

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}

//	public static String substring(String str, int start, int length) {
//		int len = str.length();
//		if (len > 15) {
//			str = str.substring(start, length);
//		}
//		str = str + "......";
//		return str;
//	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			logger.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (byte anEncodedPassword : encodedPassword) {
			if ((anEncodedPassword & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(anEncodedPassword & 0xff, 16));
		}

		return buf.toString();
	}





	public static void main(String... args) {
		String arr[] = splitString("10111|sdsysadmin|sdsysadmin|0|||","|");
		System.out.println(arr.length);
	}


    /** 空字符串。 */
    public static final String EMPTY_STRING = "";

    /**
     * 比较两个字符串（大小写敏感）。
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
     * <pre>
     * StringUtil.isEmpty(null)      = false
     * StringUtil.isEmpty("")        = false
     * StringUtil.isEmpty(" ")       = true
     * StringUtil.isEmpty("bob")     = true
     * StringUtil.isEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果不为空, 则返回<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     *
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.indexOf(searchStr);
    }

    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * <pre>
     * StringUtil.indexOf(null, *, *)          = -1
     * StringUtil.indexOf(*, null, *)          = -1
     * StringUtil.indexOf("", "", 0)           = 0
     * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtil.indexOf("aabaabaa", "b", -1) = 2
     * StringUtil.indexOf("aabaabaa", "", 2)   = 2
     * StringUtil.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     * @param startPos 开始搜索的索引值，如果小于0，则看作0
     *
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        // JDK1.3及以下版本的bug：不能正确处理下面的情况
        if ((searchStr.length() == 0) && (startPos >= str.length())) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }

    /**
     * 取指定字符串的子串。
     * 
     * <p>
     * 负的索引代表从尾部开始计算。如果字符串为<code>null</code>，则返回<code>null</code>。
     * <pre>
     * StringUtil.substring(null, *, *)    = null
     * StringUtil.substring("", * ,  *)    = "";
     * StringUtil.substring("abc", 0, 2)   = "ab"
     * StringUtil.substring("abc", 2, 0)   = ""
     * StringUtil.substring("abc", 2, 4)   = "c"
     * StringUtil.substring("abc", 4, 6)   = ""
     * StringUtil.substring("abc", 2, 2)   = ""
     * StringUtil.substring("abc", -2, -1) = "b"
     * StringUtil.substring("abc", -4, 2)  = "ab"
     * </pre>
     * </p>
     *
     * @param str 字符串
     * @param start 起始索引，如果为负数，表示从尾部计算
     * @param end 结束索引（不含），如果为负数，表示从尾部计算
     *
     * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STRING;
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。
     * <pre>
     * StringUtil.contains(null, *)     = false
     * StringUtil.contains(*, null)     = false
     * StringUtil.contains("", "")      = true
     * StringUtil.contains("abc", "")   = true
     * StringUtil.contains("abc", "a")  = true
     * StringUtil.contains("abc", "z")  = false
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     *
     * @return 如果找到，则返回<code>true</code>
     */
    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return str.indexOf(searchStr) >= 0;
    }

    /**
     * <p>Checks if the String contains only unicode digits.
     * A decimal point is not a unicode digit and returns false.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String ("") will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

	public static void testEncode(String s) {
		try {
			System.out.println("String : " + s);
			System.out.println("Result 1: "
					+ new String(s.getBytes("utf-8"), "gbk"));
			System.out.println("Result 2: "
					+ new String(s.getBytes("utf-8"), "iso8859-1"));
			System.out.println("Result 3: " + new String(s.getBytes("utf-8")));

			System.out.println("Result 4: "
					+ new String(s.getBytes("iso8859-1"), "gbk"));
			System.out.println("Result 5: "
					+ new String(s.getBytes("iso8859-1"), "utf-8"));
			System.out.println("Result 6: "
					+ new String(s.getBytes("iso8859-1")));

			System.out.println("Result 7: "
					+ new String(s.getBytes("gbk"), "iso8859-1"));
			System.out.println("Result 8: "
					+ new String(s.getBytes("gbk"), "utf-8"));
			System.out.println("Result 9: " + new String(s.getBytes("gbk")));

		} catch (Exception e) {
			logger.error(e);
		}
	}
    
	/**
	 * 计算百分比并且格式化成 xx.00%
	 * @param x 分子
	 * @param y 分母
	 * @return
	 */
	public static String precentFormat(double x,double y) {
	   String precent = "";//接受百分比的值
	   double dPrecent = x/y;
	   //NumberFormat nf   =   NumberFormat.getPercentInstance();     注释掉的也是一种方法
	   //nf.setMinimumFractionDigits( 2 );        保留到小数点后几位
	   DecimalFormat df = new DecimalFormat("##.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
	    //baifenbi=nf.format(fen);   
	   precent= df.format(dPrecent);  
	   return precent;
	}

	public static String[] splitString(String str, String separator) {
		Vector splitArrays = new Vector();
		int i = 0;
		int j = 0;
		while (i <= str.length()) {
			j = str.indexOf(separator, i);
			if (j < 0) {
				j = str.length();
			}
			splitArrays.addElement(str.substring(i, j));
			i = j + 1;
		}
		int size = splitArrays.size();
		String[] array = new String[size];
		System.arraycopy(splitArrays.toArray(), 0, array, 0, size);
		return array;
	}
	
	public static String getStrValue(Map m , String name) {
		Object t = m.get(name) ;
		if(t == null ){
			return "" ;
		}
		return ((String)m.get(name)).trim() ;
	}
	
	/**
	 * 把为null的字符串转换成空白。
	 * 
	 * @param str
	 * @return str
	 */
	public static String nonNull(String str) {
		return (str == null) ? "" : str;
	}
	
	public static boolean hasText(String str) {
		return !"".equals(nonNull(str));
	}

}
