package xyz.spc.infra.repo.Group.interacts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.interacts.PostDO;
import xyz.spc.infra.mapper.Group.interacts.PostMapper;

@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, PostDO> implements PostService {
}
