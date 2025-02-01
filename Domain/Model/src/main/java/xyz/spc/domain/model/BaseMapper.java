package xyz.spc.domain.model;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import xyz.spc.domain.dos.BaseDO;

/**
 * BaseModel的Mapper转换器
 */
@Mapper
public interface BaseMapper {
    BaseMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BaseMapper.class);

    void model2DO(BaseModel model, @MappingTarget BaseDO baseDO);
}
