package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.domain.Sticker;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    void deleteAllByRollingPaperIn(List<RollingPaper> rollingPaper);
}
