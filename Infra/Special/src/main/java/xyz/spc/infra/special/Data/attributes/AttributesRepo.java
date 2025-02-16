package xyz.spc.infra.special.Data.attributes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Data.attributes.FileTagMapper;
import xyz.spc.infra.repo.Data.attributes.FileTagService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class AttributesRepo {

    public final FileTagService fileTagService;
    public final FileTagMapper fileTagMapper;
}
