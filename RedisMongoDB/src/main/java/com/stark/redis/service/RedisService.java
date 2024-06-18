package com.stark.redis.service;


import com.stark.redis.repo.UserRepo;
import com.stark.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserRepo userRepo;

    private static final String KEY = "USER_CACHE";


    public User save(User user) {
        User save = userRepo.save(user);
        redisTemplate.opsForHash().put(KEY, user.getUserId(), save);
        return save;
    }

    public User getUser(String id) {
        User cachedUser = (User) redisTemplate.opsForHash().get(KEY, id);
        if (cachedUser != null) {
            System.out.println("User returned from cache");
            return cachedUser;
        }

        // If not found in cache, retrieve from a database
        Optional<User> user = userRepo.findById(Integer.valueOf(id));
        if (user.isPresent()) {
            // Cache the user
            redisTemplate.opsForHash().put(KEY, id, user.get());
            return user.get();
        }
        return null;
    }

    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }


    public void deleteUser(String id) {
        userRepo.deleteById(Integer.valueOf(id));
        redisTemplate.opsForHash().delete(KEY, id);
    }


}
