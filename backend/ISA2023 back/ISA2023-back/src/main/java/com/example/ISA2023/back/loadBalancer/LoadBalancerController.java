package com.example.ISA2023.back.loadBalancer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class LoadBalancerController {

    private final LoadBalancerService loadBalancerService;

    @Autowired
    public LoadBalancerController(LoadBalancerService loadBalancerService) {
        this.loadBalancerService = loadBalancerService;
    }


    @GetMapping("/api/**")
    public void handleApiRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the next server instance from the load balancer
        String selectedServer = loadBalancerService.getNextServer();

        // Extract the remaining part of the URI after "/api/"
        String remainingPath = request.getRequestURI().replace("/api", "");
        System.out.println(remainingPath);
        // Construct the full URL
        String redirectUrl = "http://" + selectedServer + "/api/v1/user" +remainingPath ;
        System.out.println(redirectUrl);

        // Set the redirect headers
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", redirectUrl);
    }

}
