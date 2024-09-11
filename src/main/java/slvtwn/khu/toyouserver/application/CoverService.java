package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.agent.modellabs.ModelLabsAgent;
import slvtwn.khu.toyouserver.domain.Cover;
import slvtwn.khu.toyouserver.dto.CoverRequest;
import slvtwn.khu.toyouserver.dto.CoverResponse;
import slvtwn.khu.toyouserver.persistance.CoverRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CoverService {

    private static final int FIRST_COVER_IMAGE = 0;

    private final ModelLabsAgent modelLabsAgent;

    private final CoverRepository coverRepository;

    public CoverResponse generateCoverImage(CoverRequest request) {
        List<String> keywords = request.keywords();
        String coverImageUrl = modelLabsAgent.generateCoverWithKeywords(keywords)
                .get(FIRST_COVER_IMAGE);

        Cover cover = new Cover(coverImageUrl);
        coverRepository.save(cover);
        return CoverResponse.from(cover);
    }
}
