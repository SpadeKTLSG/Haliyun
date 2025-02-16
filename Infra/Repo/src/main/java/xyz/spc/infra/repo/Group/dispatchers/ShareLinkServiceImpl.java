package xyz.spc.infra.repo.Group.dispatchers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.dispatchers.ShareLinkDO;
import xyz.spc.infra.mapper.Group.dispatchers.ShareLinkMapper;

@Service
@RequiredArgsConstructor
public class ShareLinkServiceImpl extends ServiceImpl<ShareLinkMapper, ShareLinkDO> implements ShareLinkService {
}
