package nbc.subjectbaro.domain.user.repository;

import java.util.List;
import java.util.Optional;
import nbc.subjectbaro.domain.user.entity.User;

public interface UserRepository {

    User create(User user);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findAll();

    Optional<User> findById(Long id);
}
