package slvtwn.khu.toyouserver.persistance;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.UserSocialAccount;

public interface UserSocialAccountRepository extends JpaRepository<UserSocialAccount, Long> {
	Optional<UserSocialAccount> findByProviderSerial(String providerSerial);
}
