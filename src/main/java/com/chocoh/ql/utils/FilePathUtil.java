package com.chocoh.ql.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.StrUtil;
import com.chocoh.ql.exception.file.FileNameNotAllowedException;
import com.chocoh.ql.exception.file.FilePathNotAllowedException;

/**
 * @author chocoh
 */
@SuppressWarnings("HttpUrlsUsage")
public class FilePathUtil {
    public static final char DELIMITER = '/';
    public static final String DELIMITER_STR = "/";
    public static final Character PATH_SEPARATOR_CHAR = '/';
    public static final String HTTP_PROTOCOL = "http://";
    public static final String HTTPS_PROTOCOL = "https://";

    public static void checkPathSecurity(String... paths) {
        for (String path : paths) {
            if (StrUtil.startWith(path, "/..") || StrUtil.containsAny(path, "../", "..\\")) {
                throw new FilePathNotAllowedException();
            }
        }
    }

    public static void checkNameSecurity(String... names) {
        for (String name : names) {
            if (StrUtil.containsAny(name, "\\", "/")) {
                throw new FileNameNotAllowedException();
            }
        }
    }

    /**
     * 拼接 URL，并去除重复的分隔符 '/'，但不会影响 http:// 和 https:// 这种头部.
     */
    public static String concat(String... urls) {
        StringBuilder sb = new StringBuilder(DELIMITER_STR);
        for (int i = 0; i < urls.length; i++) {
            String str = urls[i];
            if (StrUtil.isEmpty(str)) {
                continue;
            }
            sb.append(str);
            if (i != urls.length - 1) {
                sb.append(DELIMITER);
            }
        }
        return removeDuplicateSlashes(sb.toString());
    }

    /**
     * 去除路径中所有重复的 '/'
     */
    public static String removeDuplicateSlashes(String path) {
        if (StrUtil.isEmpty(path)) {
            return path;
        }

        StringBuilder sb = new StringBuilder();

        // 是否包含 http 或 https 协议信息
        boolean containProtocol =  StrUtil.containsAnyIgnoreCase(path, HTTP_PROTOCOL, HTTPS_PROTOCOL);

        if (containProtocol) {
            path = trimStartSlashes(path);
        }

        // 是否包含 http 协议信息
        boolean startWithHttpProtocol = StrUtil.startWithIgnoreCase(path, HTTP_PROTOCOL);
        // 是否包含 https 协议信息
        boolean startWithHttpsProtocol = StrUtil.startWithIgnoreCase(path, HTTPS_PROTOCOL);

        if (startWithHttpProtocol) {
            sb.append(HTTP_PROTOCOL);
        } else if (startWithHttpsProtocol) {
            sb.append(HTTPS_PROTOCOL);
        }

        for (int i = sb.length(); i < path.length() - 1; i++) {
            char current = path.charAt(i);
            char next = path.charAt(i + 1);
            if (!(current == DELIMITER && next == DELIMITER)) {
                sb.append(current);
            }
        }
        sb.append(path.charAt(path.length() - 1));
        return sb.toString();
    }

    /**
     * 移除 URL 中的第一个 '/'
     */
    public static String trimStartSlashes(String path) {
        if (StrUtil.isEmpty(path)) {
            return path;
        }

        while (path.startsWith(DELIMITER_STR)) {
            path = path.substring(1);
        }

        return path;
    }

    /**
     * 编码全部字符
     */
    public static String encodeAllIgnoreSlashes(String str) {
        if (StrUtil.isEmpty(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        int prevIndex = -1;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == PATH_SEPARATOR_CHAR) {
                if (prevIndex < i) {
                    String substring = str.substring(prevIndex + 1, i);
                    sb.append(URLEncodeUtil.encodeAll(substring));
                    prevIndex = i;
                }
                sb.append(c);
            }

            if (i == str.length() - 1 && prevIndex < i) {
                String substring = str.substring(prevIndex + 1, i + 1);
                sb.append(URLEncodeUtil.encodeAll(substring));
            }
        }

        return sb.toString();
    }
}
