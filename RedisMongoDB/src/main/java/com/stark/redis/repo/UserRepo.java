package com.stark.redis.repo;

import com.stark.redis.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, Integer> {
}
