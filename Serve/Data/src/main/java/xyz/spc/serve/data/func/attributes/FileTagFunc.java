package xyz.spc.serve.data.func.attributes;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.infra.special.Data.attributes.AttributesRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileTagFunc {

    /**
     * Repo
     */
     private final AttributesRepo attributesRepo;


    /**
     * 根据
     * @return
     */
    public List<FileTagDO> getAllTags() {
    }
}
