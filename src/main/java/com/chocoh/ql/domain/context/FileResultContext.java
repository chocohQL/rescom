package com.chocoh.ql.domain.context;

import com.chocoh.ql.domain.request.FileAccessRequest;
import com.chocoh.ql.domain.dto.FileInfo;
import lombok.*;
import org.apache.commons.chain.impl.ContextBase;

import java.util.List;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileResultContext extends ContextBase {
    private FileAccessRequest fileAccessRequest;
    private List<FileInfo> fileInfos;
}
