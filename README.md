# Haliyun 蛤蜊云 玄桃K的网盘毕设


个人集大成之 基于 Spring Cloud Alibaba + COLA 框架的 PaaS 网盘 毕设

可能有些界面不好看(不好调), 有些地方糙了,但是重要的是思想的落地实现

这是后端, 其他文档在 /Asset 中

### 状态

阶段性封档, 不再做大规模更新, 拟合论文必要 bug 除外

论文结束后会提供更多材料和文档

### 技术栈

Spring Cloud Vue3 RabbitMQ Redisson JS ElementUI Postman Sentinel ESLint

### 项目描述

一个专注解决垂直领域中群组治理与完全私密等需求的 PaaS 网盘，综合基础 Spring Cloud Alibaba 与 COLA 框架充血模型优势独立设计了提供用户存储、群组管理、文件分享等功能的 Flow-Func-Repo 前后端分离式架构。是继承了 EduExchM 核心思路的个人毕设项目。

### 项目详情

* 搭建 Local 与 Dockerfile 中的 HDFS，并封装调用工具类实现存储层解耦
* 利用 RabbitMQ + Spring Event，选择可靠消息最终一致性方案实现分布式事务
* 手写惰性令牌桶并整合 Sentinel+Redis 实现多维度限流套件，支持多种自定义接口保护方案
* 针对鉴权场景设计多模态的 JSR303 校验组预处理入参，并制作责任链模式组件进行可插拔业务层校验
* 使用异步线程池结合 CompletableFuture + CountdownLaunch，异步编排和并行优化了批量上传下载场景中的写历史记录功能，在以百为量级的文件批量下载中，接口处理性能提升了 95.83%
