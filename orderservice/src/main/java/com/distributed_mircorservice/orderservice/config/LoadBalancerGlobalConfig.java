package com.distributed_mircorservice.orderservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.Serial;

@Configuration
//add this annotation for avoid runtime exceptions that's can occur from for one service it may be possible to two algorithms attached.
@ConditionalOnMissingBean(ReactorLoadBalancer.class)
public class LoadBalancerGlobalConfig {
    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(LoadBalancerClientFactory clientFactory, Environment environment) {
        String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RoundRobinLoadBalancer(clientFactory.getLazyProvider(
                serviceId, ServiceInstanceListSupplier.class
        ),
                serviceId
        );}
}
