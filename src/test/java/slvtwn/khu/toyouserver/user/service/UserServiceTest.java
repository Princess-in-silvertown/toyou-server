package slvtwn.khu.toyouserver.user.service;

import java.time.LocalDate;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.application.UserService;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.UserResponse;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void 유저를_조회할_수_있다() {
        // given
        User user = new User("teo", LocalDate.now(), "www.profile-picture.com");
        userRepository.save(user);

        // when
        UserResponse found = userService.findUser(user.getId());

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(user.getId()).isEqualTo(found.id());
            softly.assertThat(user.getName()).isEqualTo(found.name());
            softly.assertThat(user.getProfilePicture()).isEqualTo(found.imageUrl());
        });
    }
}
