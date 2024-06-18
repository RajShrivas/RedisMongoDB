package com.stark.redis.controller;


import com.stark.redis.model.User;
import com.stark.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService service;


    @PostMapping
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        return service.getUser(id);
    }

    @GetMapping
    public List<User> getAll() {
        Map<Object, Object> all = service.findAll();
        List<User> list = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : all.entrySet()) {
            User value = (User) entry.getValue();
            list.add(value);
        }
        return list;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        service.deleteUser(id);
    }

}
