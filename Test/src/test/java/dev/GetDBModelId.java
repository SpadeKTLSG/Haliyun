package dev;

import org.junit.jupiter.api.Test;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;

public class GetDBModelId {

    @Test
    public void getSFID() {
        //生成6个:
        for (int i = 0; i < 6; i++) {
            System.out.println("雪花算法唯一ID生成器生成:= No." + i + "===>" + SnowflakeIdUtil.nextId());
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
