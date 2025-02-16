package xyz.spc.infra.special.Money.props;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Money.props.PropMapper;
import xyz.spc.infra.repo.Money.props.PropService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class PropsRepo {

    public final PropService propService;
    public final PropMapper propMapper;
}
