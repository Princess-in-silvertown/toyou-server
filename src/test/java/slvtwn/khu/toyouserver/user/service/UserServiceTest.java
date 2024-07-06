package slvtwn.khu.toyouserver.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.user.domain.User;
import slvtwn.khu.toyouserver.user.dto.UserResponse;
import slvtwn.khu.toyouserver.user.repository.UserRepository;

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
        User user = new User("teo", "www.profile-picture.com");
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
