package xyz.spc.serve.guest.func.records;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.gate.dto.Guest.users.UserDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticFunc {

    /**
     * 注册统计表, 做日常数据统计
     *
     * @param userDTO
     */
    public void registerStatistics(UserDTO userDTO) {
    }
}
