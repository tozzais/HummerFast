package com.xianlv.business;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        System.out.println(UUID.randomUUID().toString().replace("-", ""));
//        System.out.println("*saas*0*55*".split("\\*")[3]);
        String s = "/bc/appUpgraded/androidDetailByAppId";
        int i = s.indexOf(63);
        System.out.println(i);
        Pattern PARAM_URL_REGEX = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        Matcher m = PARAM_URL_REGEX.matcher(s);
        while(m.find()) {
            System.out.println(m.group(1));
        }

    }
}