package xyz.spc.infra.special.Money.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Money.events.OutletMapper;
import xyz.spc.infra.repo.Money.events.OutletService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class EventsRepo {

    public final OutletService outletService;
    public final OutletMapper outletMapper;
}
