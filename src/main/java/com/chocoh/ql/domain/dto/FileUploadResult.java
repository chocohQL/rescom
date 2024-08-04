package com.chocoh.ql.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult {
    private Boolean isComplete;
    private Date time;
    private List<Integer> lostSlices;

    public static FileUploadResult Complete() {
        return FileUploadResult.builder().isComplete(true).build();
    }
}
