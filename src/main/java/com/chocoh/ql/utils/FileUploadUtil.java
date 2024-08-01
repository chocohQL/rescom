package com.chocoh.ql.utils;

import com.chocoh.ql.exception.file.FileMd5CalculationErrorException;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author chocoh
 */
public class FileUploadUtil {
    public static String getFileMD5(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(bytes);
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            throw new FileMd5CalculationErrorException();
        }
    }
}
