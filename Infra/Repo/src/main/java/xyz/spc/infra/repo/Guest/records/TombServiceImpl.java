package xyz.spc.infra.repo.Guest.records;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.records.TombDO;
import xyz.spc.infra.mapper.Guest.records.TombMapper;

@Service
@RequiredArgsConstructor
public class TombServiceImpl extends ServiceImpl<TombMapper, TombDO> implements TombService {
}
