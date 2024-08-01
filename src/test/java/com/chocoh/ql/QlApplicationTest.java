package com.chocoh.ql;
import com.chocoh.ql.utils.FilePathUtil;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chocoh
 * @createTime 2024-01-29 00:13
 */
@SpringBootTest
public class QlApplicationTest {
    public static void main(String[] args) {
        System.out.println();
        System.out.println(FilePathUtil.concat(""));
        System.out.println(FilePathUtil.concat("/"));
        System.out.println(FilePathUtil.concat("user"));
        System.out.println(FilePathUtil.concat("/user"));
        System.out.println(FilePathUtil.concat("user/"));
        System.out.println(FilePathUtil.concat("/user/"));
        System.out.println(FilePathUtil.concat("/user//"));
        System.out.println(FilePathUtil.concat("//user//"));
        System.out.println(FilePathUtil.concat("/user/1"));
        System.out.println(FilePathUtil.concat("/user/1/"));
        System.out.println();
        System.out.println(FilePathUtil.concat("user", "1"));
        System.out.println(FilePathUtil.concat("user/", "1"));
        System.out.println(FilePathUtil.concat("user/", "/1"));
        System.out.println(FilePathUtil.concat("user/", "/1/"));
        System.out.println(FilePathUtil.concat("/user/", "/1/"));
    }
}
