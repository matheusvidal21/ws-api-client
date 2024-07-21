package com.client.ws.api.client.repository.jpa;

import com.client.ws.api.client.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
