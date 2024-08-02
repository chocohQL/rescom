package com.chocoh.ql.service.chain.context;

import com.chocoh.ql.domain.file.FileAccessRequest;
import com.chocoh.ql.domain.vo.FileInfo;
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
