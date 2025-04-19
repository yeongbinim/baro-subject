package nbc.subjectbaro.unit.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryUserRepositoryTest {

    private MemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryUserRepository();
    }

    @Test
    void createUser_success() {
        // given
        User user = User.builder().username("tester").nickname("nick").build();

        // when
        User saved = repository.create(user);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("tester");
    }

    @Test
    void findByUsername_found() {
        // given
        User user = User.builder().username("hello").build();
        repository.create(user);

        // when
        Optional<User> result = repository.findByUsername("hello");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("hello");
    }

    @Test
    void findByUsername_notFound() {
        // when
        Optional<User> result = repository.findByUsername("none");

        // then
        assertThat(result).isNotPresent();
    }

    @Test
    void existsByUsername_true() {
        // given
        repository.create(User.builder().username("exists").build());

        // when
        boolean exists = repository.existsByUsername("exists");

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByUsername_false() {
        // when
        boolean exists = repository.existsByUsername("unknown");

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void findById_found() {
        // given
        User user = repository.create(User.builder().username("idTest").build());

        // when
        Optional<User> found = repository.findById(user.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("idTest");
    }

    @Test
    void findById_notFound() {
        // when
        Optional<User> found = repository.findById(999L);

        // then
        assertThat(found).isNotPresent();
    }

    @Test
    void findAll_returnsAllUsers() {
        // given
        repository.create(User.builder().username("one").build());
        repository.create(User.builder().username("two").build());

        // when
        List<User> userList = repository.findAll();

        // then
        assertThat(userList)
            .hasSize(2)
            .extracting(User::getUsername)
            .containsExactlyInAnyOrder("one", "two");
    }
}
