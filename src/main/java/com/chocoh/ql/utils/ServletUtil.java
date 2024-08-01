package com.chocoh.ql.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.chocoh.ql.domain.model.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author chocoh
 * @Description servlet服务工具类
 * @createTime 2023-12-22 16:41
 */
public class ServletUtil {
    public static HttpServletRequest getHttpServletRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return getRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }

    public static String getRemoteAddr() {
        return getHttpServletRequest().getRemoteAddr();
    }

    public static void outputResponse(int code, String msg) throws IOException {
        String string = JSON.toJSONString(new Response(code, msg, null));
        outputString(string, getHttpServletResponse());
    }

    public static void outputString(String string, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(string);
    }

    public static void writeFile(File file) throws IOException {
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        HttpServletResponse response = ServletUtil.getHttpServletResponse();
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + FilePathUtil.encodeAllIgnoreSlashes(file.getName()));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        OutputStream outputStream = response.getOutputStream();
        if (request.getHeader("Range") == null) {
            // 普通下载
            IoUtil.copy(FileUtil.getInputStream(file), outputStream);
        } else {
            // 分片下载 Range: bytes=start-end
            String[] range = request.getHeader("Range").replaceAll("bytes=", "").split("-");
            long from = Long.parseLong(range[0]);
            long to = range.length > 2 ? Long.parseLong(range[1]) : 0;
            int size = (int) (from > to ? to - from : file.length() - from);

            response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            response.addHeader(HttpHeaders.CONTENT_LENGTH, size + "");

            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                byte[] buffer = new byte[size];
                raf.write(buffer);
                IoUtil.copy(new ByteArrayInputStream(buffer), outputStream);
            }
        }
        response.flushBuffer();
    }

    public static String getExtractPathWithinPattern() {
        HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();
        String path = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return apm.extractPathWithinPattern(bestMatchPattern, path);
    }
}
