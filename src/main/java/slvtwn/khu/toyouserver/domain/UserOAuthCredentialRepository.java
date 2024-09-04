package slvtwn.khu.toyouserver.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOAuthCredentialRepository extends JpaRepository<UserOAuthCredential, Long> {
	Optional<UserOAuthCredential> findBySerialId(String serialId);
}
