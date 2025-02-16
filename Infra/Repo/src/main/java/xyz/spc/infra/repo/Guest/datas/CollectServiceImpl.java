package xyz.spc.infra.repo.Guest.datas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.datas.CollectDO;
import xyz.spc.infra.mapper.Guest.datas.CollectMapper;

@Service
@RequiredArgsConstructor
public class CollectServiceImpl extends ServiceImpl<CollectMapper, CollectDO> implements CollectService {
}
