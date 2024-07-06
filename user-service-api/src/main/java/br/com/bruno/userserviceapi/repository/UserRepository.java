package br.com.bruno.userserviceapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.bruno.userserviceapi.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
