package xyz.spc.common.constant.mq;

/**
 * RabbitMQ配置
 */
public interface SystemMQCT {

    /**
     * 重试投递次数
     */
    int retryTimes = 3;

    /**
     * 重试投递间隔
     */
    long retryInitialInterval = 500;

    /**
     * 重试投递间隔倍数
     */
    double retryMultiplier = 2.0;

    /**
     * 重试投递最大间隔
     */
    long retryMaxInterval = 5000;

}
