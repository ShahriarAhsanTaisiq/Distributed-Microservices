package com.distributed_mircorservice.orderservice.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerProductClientConfig {

    ReactorLoadBalancer<ServiceInstance> productClientLoadBalancer(LoadBalancerClientFactory clientFactory) {
        return new RandomLoadBalancer(
                clientFactory.getLazyProvider("productservice", ServiceInstanceListSupplier .class
                ),
                "productservice");
    }
}
