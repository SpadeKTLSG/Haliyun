package xyz.spc.infra.special.Guest.messages;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.messages.SelfMailMapper;
import xyz.spc.infra.repo.Guest.messages.SelfMailService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class SelfMailsRepo {

    public final SelfMailService selfMailService;
    public final SelfMailMapper selfMailMapper;
}
