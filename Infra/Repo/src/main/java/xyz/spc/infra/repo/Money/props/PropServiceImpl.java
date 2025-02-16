package xyz.spc.infra.repo.Money.props;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Money.props.PropDO;
import xyz.spc.infra.mapper.Money.props.PropMapper;

@Service
@RequiredArgsConstructor
public class PropServiceImpl extends ServiceImpl<PropMapper, PropDO> implements PropService {
}
