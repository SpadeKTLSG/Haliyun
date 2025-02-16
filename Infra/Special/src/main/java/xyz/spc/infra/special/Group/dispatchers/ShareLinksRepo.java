package xyz.spc.infra.special.Group.dispatchers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.dispatchers.ShareLinkMapper;
import xyz.spc.infra.repo.Group.dispatchers.ShareLinkService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class ShareLinksRepo {
    
    public final ShareLinkService shareLinkService;
    public final ShareLinkMapper shareLinkMapper;
}
