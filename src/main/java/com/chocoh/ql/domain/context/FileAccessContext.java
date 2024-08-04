package com.chocoh.ql.domain.context;

import com.chocoh.ql.common.enums.system.FileAccessTypeEnum;
import com.chocoh.ql.domain.entity.RepositoryMember;
import lombok.*;
import org.apache.commons.chain.impl.ContextBase;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileAccessContext extends ContextBase {
    private Long repositoryId;
    private String filePath;
    private String fileName;
    private String pathAndName;
    private FileAccessTypeEnum accessType;
    private RepositoryMember member;
}
