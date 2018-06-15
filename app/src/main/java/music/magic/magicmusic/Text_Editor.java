package music.magic.magicmusic;


/**
 * Created by Administrator on 2017/8/10.
 */

public class Text_Editor {


    public static int 寻找文本(String str1, String str2, int start)
    {
        if ((start < 0) || (start > str1.length()) || ("".equals(str1)) || ("".equals(str2))) {
            return -1;
        }
        return str1.indexOf(str2, start);
    }


    public static int 倒找文本(String str1, String str2, int start) {
        if ((start < 0) || (start > str1.length()) || ("".equals(str1)) || ("".equals(str2))) {
            return -1;
        }
        return str1.lastIndexOf(str2, start);
    }


    public static String 到小写(String str) {
        return str.toLowerCase();
    }


    public static String 到大写(String str) {
        return str.toUpperCase();
    }


    public static String 取文本左边(String str, int len) {
        if (("".equals(str)) || (len <= 0))
            return "";
        if (len > str.length()) {
            return str;
        }
        return str.substring(0, len);
    }


    public static String 取文本右边(String str, int len) {
        if (("".equals(str)) || (len <= 0))
            return "";
        if (len > str.length()) {
            return str;
        }
        int start = str.length() - len;
        return str.substring(start, str.length());
    }


    public static String 取文本中间(String str, int start, int len) {
        if (("".equals(str)) || (start < 0) || (len <= 0) || (start > str.length())) {
            return "";
        }
        int end = start + len;
        if (end > str.length()) {
            end = str.length();
        }
        return str.substring(start, end);
    }


    public static int 取文本长度(String str) {
        return str.length();
    }


    public static int 取文本长度2(String str)
    {
        byte[] byt = str.getBytes();
        return byt.length;
    }




    public static String 删首尾空(String str)
    {
        return str.trim();
    }


    public static String 删首空(String str) {
        char[] chars = str.toCharArray();
        int count = 0;
        for (int i = 0; (i < chars.length) && (chars[i] == ' '); count++) { i++;
        }
        String s = str;
        if (count > 0) {
            s = new String(chars, count, chars.length - count);
        }
        return s;
    }


    public static String 删尾空(String str) {
        char[] chars = str.toCharArray();
        int count = 0;
        for (int i = chars.length - 1; (i > 0) && (chars[i] == ' '); count++) { i--;
        }
        String s = str;
        if (count > 0) {
            s = new String(chars, 0, chars.length - count);
        }
        return s;
    }


    public static String 子文本替换(String str, String find, String replace) {
        if (("".equals(find)) || ("".equals(str))) {
            return "";
        }

        find = "\\Q" + find + "\\E";
        return str.replaceAll(find, replace);
    }


    public static String 子文本替换2(String str, int start, int end, String replace) {
        if (("".equals(str)) || (start < 0) || (start > str.length()) || (end < start) || (end > str.length())) {
            return "";
        }
        return str.substring(0, start) + replace + str.substring(end + 1);
    }


    public static int 文本比较(String str1, String str2) {
        return str1.compareTo(str2);
    }


    public static String 翻转文本(String str) {
        return new StringBuffer(str).reverse().toString();
    }





    public static String[] 分割文本(String str, String separator)
    {
        if (("".equals(separator)) || ("".equals(str))) {
            return new String[0];
        }

        if (separator.equals("\n")) {
            str = 子文本替换(str, "\r", "");
        }

        if (取文本右边(str, 取文本长度(separator)).equals(separator)) {
            return 取指定文本(separator + str, separator, separator);
        }
        return 取指定文本(separator + str + separator, separator, separator);
    }


    public static String[] 取指定文本(String str, String left, String right)
    {
        if (("".equals(str)) || ("".equals(left)) || ("".equals(right))) {
            return new String[0];
        }
        return zhengze.正则匹配(str, "(?<=\\Q" + left + "\\E).*?(?=\\Q" + right + "\\E)");
    }


    public static String 取指定文本2(String str, String left, String right) {
        String[] temp = 取指定文本(str, left, right);
        if (temp.length > 0) {
            return temp[0];
        }
        return "";
    }
}
