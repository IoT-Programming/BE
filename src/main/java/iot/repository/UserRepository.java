package iot.repository;

import iot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.status FROM User u WHERE u.name = :name")
    int findStatusByName(String name);
    User findUserByName(String name);
}
