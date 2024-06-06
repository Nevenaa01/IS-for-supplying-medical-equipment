package com.example.ISA2023.back.loadBalancer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoadBalancerService {

    private final List<String> serverInstances;
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    public LoadBalancerService loadBalancerService() {
        // Define your server instances here
        List<String> serverInstances = List.of("localhost:8090", "localhost:8091");
        return new LoadBalancerService(serverInstances);
    }

    public LoadBalancerService(List<String> serverInstances) {
        this.serverInstances = serverInstances;
    }

    public String getNextServer() {
        List<String> serverInstances = List.of("localhost:8090", "localhost:8091");
        int index = currentIndex.getAndIncrement();
        return serverInstances.get(index % serverInstances.size());
    }
}