package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.UserResponse;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class UserServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Test
    void 같은_그룹의_유저들을_키워드로_검색할_수_있다() {
        // given
        User user1 = new User("name1", LocalDate.now(), "introduction", "profile_picture");
        User user2 = new User("name2", LocalDate.now(), "introduction", "profile_picture");
        Group group = new Group("name");
        Member member1 = new Member(user1, group);
        Member member2 = new Member(user2, group);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(group);
        entityManager.persist(member1);
        entityManager.persist(member2);

        // when
        List<UserResponse> response = userService.findUsersWithFilteringOptions(user1, "ame2", null);

        // then
        assertThat(response)
                .containsExactly(UserResponse.of(user2));
    }

    @Test
    void 다른_그룹의_유저들을_키워드로_검색할_수_있다() {
        // given
        User user1 = new User("name1", LocalDate.now(), "introduction", "profile_picture");
        User user2 = new User("name2", LocalDate.now(), "introduction", "profile_picture");
        Group group1 = new Group("name");
        Group group2 = new Group("name");
        Member member1 = new Member(user1, group1);
        Member member2 = new Member(user2, group2);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(group1);
        entityManager.persist(group2);
        entityManager.persist(member1);
        entityManager.persist(member2);

        // when
        List<UserResponse> response = userService.findUsersWithFilteringOptions(user1, "ame2", group2.getId());

        // then
        assertThat(response)
                .containsExactly(UserResponse.of(user2));
    }
}
