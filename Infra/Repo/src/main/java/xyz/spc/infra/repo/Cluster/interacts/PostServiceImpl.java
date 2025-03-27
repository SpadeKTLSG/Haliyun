package xyz.spc.infra.repo.Cluster.interacts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.interacts.PostDO;
import xyz.spc.infra.mapper.Cluster.interacts.PostMapper;

@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, PostDO> implements PostService {
}
