package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoidanit.laptopshop.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    List<User> findByEmail(String email);

    User findOneByEmail(String email);

    List<User> findFirstByEmail(String email);

    boolean existsByEmail(String email);
}
