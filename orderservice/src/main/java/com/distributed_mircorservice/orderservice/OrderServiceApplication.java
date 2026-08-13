package com.distributed_mircorservice.orderservice;

import com.distributed_mircorservice.orderservice.config.LoadBalancerGlobalConfig;
import com.distributed_mircorservice.orderservice.config.LoadBalancerProductClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
// When we want to change LoadBalance Default Round Robin Algorithms to Random Load Balancer Algorithms (For One Service only)
//@LoadBalancerClient(name = "product-service", configuration = LoadBalancerProductClientConfig.class)

// This is for Multiple services multiple load balancer algorithms
@LoadBalancerClients( defaultConfiguration = LoadBalancerGlobalConfig.class,
        value = {
                @LoadBalancerClient(name = "product-service", configuration = LoadBalancerProductClientConfig.class)
                // Here you can add others with comma seperated.
        }
)
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
