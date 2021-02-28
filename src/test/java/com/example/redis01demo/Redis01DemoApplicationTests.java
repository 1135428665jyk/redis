package com.example.redis01demo;

import com.example.redis01demo.bean.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class Redis01DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws JsonProcessingException {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        User user = new User("haha",12);
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        System.out.println(user);
        valueOperations.set("user",jsonUser);
        System.out.println(valueOperations.get("user"));


    }

}
