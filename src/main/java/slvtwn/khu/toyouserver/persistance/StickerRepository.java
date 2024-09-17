package slvtwn.khu.toyouserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.Sticker;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

}
