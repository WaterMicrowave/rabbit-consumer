# rabbit-consumer
###交换机选择topic模式
~~~
@RabbitListener(bindings = @QueueBinding(
                               value = @Queue(value = "queue-1", durable = "true"),
                               exchange = @Exchange(name = "exchange-1",
                                       durable = "true",
                                       type = "topic",
                                       ignoreDeclarationExceptions = "true"),
                key = "springboot.*")
~~~