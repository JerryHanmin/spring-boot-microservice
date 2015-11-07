# Spring Cloud Example Project

使用Spring Cloud构建微服务，本项目主要用于学习Spring Cloud、Docker、Gradle等相关技术。

## discovery-service 服务发现
核心组件，使用Netflix Eureka，用于管理在容器中的众多微服务实例；
各个微服务可以通过serviceid进行联系，而不需要具体的IP。

## config-service 配置服务
核心组件，微服务应用的配置应该存储在环境中，而不是本地项目中。
各个微服务的配置文件可以存放于Git中集中管理，修改配置文件时不需要重新编译。


## demo1-service & demo2-service
最初的示例，使用Spring Boot创建的微服务，使用discoveryClient可以相互发现。

## sample-kafka-producer & sample-kafka-consumer
kafka的生产者和消费者实例。
kafka或者rabbitMQ 等消息系统是微服务架构中不可缺少的部分，微服务的一些特殊操作通常需要发出事件（即消息）来进行同步操作。


## sample-ribbon-server & sample-ribbon-client
Spring Cloud集成了ribbon负载均衡算法，这两个实例验证了ribbon负载均衡。
