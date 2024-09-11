package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.agent.modellabs.ModelLabsAgent;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.dto.CoverRequest;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.RollingPaperRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RollingPaperService {

    private static final int FIRST_COVER_IMAGE = 0;

    private final ModelLabsAgent modelLabsAgent;
    private final RollingPaperRepository rollingpaperRepository;

    @Transactional
    public void generateCoverImageAndUpdateRollingPaper(CoverRequest request, Long rollingPaperId) {
        List<String> keywords = request.keywords();
        String coverImageUrl = modelLabsAgent.generateCoverWithKeywords(keywords)
                .get(FIRST_COVER_IMAGE);

        RollingPaper rollingPaper = rollingpaperRepository.findById(rollingPaperId)
                .orElseThrow(() -> new ToyouException(ResponseType.BAD_REQUEST));
        rollingPaper.updateCoverImage(coverImageUrl);
    }
}
