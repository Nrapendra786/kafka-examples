package com.example.kubernetesdemo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@EnableConfigurationProperties(ClientConfig.class)
public class HelloController {

    private ClientConfig clientConfig;

    public HelloController(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @GetMapping(path = {"/hello", "/hello/{name}"})
    public String hello(@PathVariable(required = false) Optional<String> name) {
        return clientConfig.getGreeting() + " " + name.orElse("World");
    }

    @GetMapping("/")
    public String greet() {
        return "Hello World";
    }
}