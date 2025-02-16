package xyz.spc.infra.special.Money.donates;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Money.donates.DonateMapper;
import xyz.spc.infra.repo.Money.donates.DonateService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class DonatesRepo {

    public final DonateService donateService;
    public final DonateMapper donateMapper;
}
