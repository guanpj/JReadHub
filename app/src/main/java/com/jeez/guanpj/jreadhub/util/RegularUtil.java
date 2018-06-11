package com.jeez.guanpj.jreadhub.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
    private static Pattern webSitePattern = Pattern.compile("(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?");

    public static String matchWebSite(String source) {
        Matcher m = webSitePattern.matcher(source);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

}
