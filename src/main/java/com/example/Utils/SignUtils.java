package com.example.Utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

public class SignUtils {
    public static String sign(String... data) {
        Arrays.sort(data);
        return DigestUtils.sha1Hex(join(data));
    }

    public static String join(Object... param) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < param.length; i++) {
            sb.append(param[i]);
        }
        return sb.toString();
    }
}
