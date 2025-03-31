package xyz.spc.infra.repo.Cluster.dispatchers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.dispatchers.ShareLinkDO;
import xyz.spc.infra.mapper.Cluster.dispatchers.ShareLinkMapper;

@Service
@RequiredArgsConstructor
public class ShareLinkServiceImpl extends ServiceImpl<ShareLinkMapper, ShareLinkDO> implements ShareLinkService {
}
