package xyz.spc.infra.special.Cluster.dispatchers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.dispatchers.ShareLinkMapper;
import xyz.spc.infra.repo.Cluster.dispatchers.ShareLinkService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class ShareLinksRepo {
    
    public final ShareLinkService shareLinkService;
    public final ShareLinkMapper shareLinkMapper;
}
