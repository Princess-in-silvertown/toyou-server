package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.domain.Sticker;
import slvtwn.khu.toyouserver.domain.StickerSide;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.MemberRepository;
import slvtwn.khu.toyouserver.persistance.RollingPaperRepository;
import slvtwn.khu.toyouserver.persistance.StickerRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RollingPaperService {

    private final RollingPaperRepository rollingPaperRepository;
    private final StickerRepository stickerRepository;
    private final MemberRepository memberRepository;

    public void sendRollingPaper(RollingPaperRequest request, Long groupId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ToyouException(ResponseType.BAD_REQUEST));

        RollingPaper rollingPaper = new RollingPaper(request.coverImageUrl(), request.title(),
                request.content(), request.themeId(), member);
        List<Sticker> stickers = parseStickers(request, rollingPaper);

        rollingPaperRepository.save(rollingPaper);
        stickerRepository.saveAll(stickers);
    }

    private List<Sticker> parseStickers(RollingPaperRequest request, RollingPaper rollingPaper) {
        return request.stickers().stream()
                .map(each -> new Sticker(rollingPaper, each.imageUrl(), each.x(), each.y(),
                        each.rotate(), each.scale(), StickerSide.valueOf(each.side())))
                .toList();
    }

    public RollingPaperResponse getRollingPaper(Long groupId, Long memberId, Long rollingPaperId) {
        // TODO: 검증 필요
        RollingPaper rollingPaper = rollingPaperRepository.findById(rollingPaperId)
                .orElseThrow(() -> new ToyouException(ResponseType.BAD_REQUEST));

        return RollingPaperResponse.from(rollingPaper);
    }
}
