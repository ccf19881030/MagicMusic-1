package music.magic.magicmusic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/10.
 */

public class zhengze {
    private static Pattern pattern = null;

    private static Matcher matcher = null;




    public static String[] 正则匹配(String text, String statement)
    {
        Pattern pn = Pattern.compile(statement, Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
        Matcher mr = pn.matcher(text);
        List<String> list = new ArrayList();
        while (mr.find())
        {
            list.add(mr.group());
        }
        String[] strings = new String[list.size()];
        return (String[])list.toArray(strings);
    }


    public static void 创建表达式(String statement, boolean insensitive, boolean multiline) {
        if ((insensitive) && (multiline)) {
            pattern = Pattern.compile(statement, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
        } else if ((!insensitive) && (!multiline)) {
            pattern = Pattern.compile(statement);
        } else if ((insensitive) && (!multiline)) {
            pattern = Pattern.compile(statement, Pattern.CASE_INSENSITIVE);
        } else if ((!insensitive) && (multiline)) {
            pattern = Pattern.compile(statement, Pattern.MULTILINE);
        }
    }


    public static String[] 全部分割(String text) {
        if (pattern != null) {
            return pattern.split(text);
        }
        return new String[0];
    }


    public static void 开始匹配(String text)
    {
        if (pattern != null) {
            matcher = pattern.matcher(text);
        }
    }


    public static String 全部替换(String text) {
        if (matcher != null) {
            return matcher.replaceAll(text);
        }
        return "";
    }


    public static boolean 匹配下一个()
    {
        if (matcher != null) {
            return matcher.find();
        }
        return false;
    }


    public static String 取匹配文本()
    {
        if (matcher != null) {
            return matcher.group();
        }
        return "";
    }


    public static int 取匹配开始位置()
    {
        if (matcher != null) {
            return matcher.start();
        }
        return 0;
    }


    public static int 取匹配结束位置()
    {
        if (matcher != null) {
            return matcher.end();
        }
        return 0;
    }


    public static int 取子匹配数量()
    {
        if (matcher != null) {
            return matcher.groupCount();
        }
        return 0;
    }


    public static String 取子匹配文本(int 索引)
    {
        if (matcher != null) {
            return matcher.group(索引);
        }
        return "";
    }
}
