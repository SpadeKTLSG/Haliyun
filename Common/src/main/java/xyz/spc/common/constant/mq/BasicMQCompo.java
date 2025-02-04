package xyz.spc.common.constant.mq;

/**
 * RabbitMQ基础组件
 */
public interface BasicMQCompo {

    /**
     * 简单队列
     */
    String BASIC_QUEUE = "haliyun_basic_queue";

    /**
     * 简单交换机
     */
    String BASIC_EXCHANGE = "haliyun_basic_exchange";

    /**
     * 稳定队列
     */
    String RETRY_QUEUE = "haliyun_retry_queue";

    /**
     * 稳定交换机
     */
    String RETRY_EXCHANGE = "haliyun_retry_exchange";
}
