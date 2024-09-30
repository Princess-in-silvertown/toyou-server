package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.agent.modellabs.ModelLabsAgent;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.domain.Sticker;
import slvtwn.khu.toyouserver.domain.StickerSide;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.CoverRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.MemberRepository;
import slvtwn.khu.toyouserver.persistance.RollingPaperRepository;
import slvtwn.khu.toyouserver.persistance.StickerRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class RollingPaperService {

	private static final int FIRST_COVER_IMAGE = 0;

	private final RollingPaperRepository rollingPaperRepository;
	private final StickerRepository stickerRepository;
	private final MemberRepository memberRepository;
	private final ModelLabsAgent modelLabsAgent;
	private final RollingPaperRepository rollingpaperRepository;
	private final UserRepository userRepository;

	public void generateCoverImageAndUpdateRollingPaper(CoverRequest request, Long rollingPaperId) {
		List<String> keywords = request.keywords();
		String coverImageUrl = modelLabsAgent.generateCoverWithKeywords(keywords)
				.get(FIRST_COVER_IMAGE);
		RollingPaper rollingPaper = rollingpaperRepository.findById(rollingPaperId)
				.orElseThrow(() -> new ToyouException(ResponseType.BAD_REQUEST));
		rollingPaper.updateCoverImage(coverImageUrl);
	}

	public void sendRollingPaper(Long recipientUserId, RollingPaperRequest request) {
		Member member = memberRepository.findByUserIdAndGroupId(recipientUserId, request.groupId()).orElseThrow(
				() -> new ToyouException(ResponseType.BAD_REQUEST));
		RollingPaper rollingPaper = new RollingPaper(request.coverImageUrl(), request.title(),
				request.content(), request.themeId(), member);
		List<Sticker> stickers = parseStickers(request, rollingPaper);
		rollingPaperRepository.save(rollingPaper);
		stickerRepository.saveAll(stickers);
	}

	public RollingPaperResponse findById(Long userId, Long rollingPaperId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));
		RollingPaper rollingPaper = rollingPaperRepository.findById(rollingPaperId)
				.orElseThrow(() -> new ToyouException(ResponseType.BAD_REQUEST));
		assertUserIsRollingPaperOwner(user, rollingPaper);
		return RollingPaperResponse.from(rollingPaper);
	}

	public List<RollingPaperResponse> findReceivedRollingPapers(Long userId, Long groupId,
	                                                            Long targetId, Integer limit) {
		List<Long> memberIds = getMemberIds(userId, groupId);
		PageRequest pageRequest = PageRequest.ofSize(limit);
		return rollingPaperRepository.findAllByMembersAfterCursor(memberIds, targetId, pageRequest).stream()
				.map(RollingPaperResponse::from)
				.toList();
	}

	private List<Sticker> parseStickers(RollingPaperRequest request, RollingPaper rollingPaper) {
		return request.stickers().stream()
				.map(each -> new Sticker(rollingPaper, each.imageUrl(), each.x(), each.y(),
						each.rotate(), each.scale(), StickerSide.valueOf(each.side())))
				.toList();
	}

	private void assertUserIsRollingPaperOwner(User user, RollingPaper rollingPaper) {
		if (!rollingPaper.getMember().getUser().equals(user)) {
			throw new ToyouException(ResponseType.UNAUTHORIZED_USER_ACCESS);
		}
	}

	private List<Long> getMemberIds(Long userId, Long groupId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));
		if (groupId == null) {
			List<Member> members = memberRepository.findByUser(user);
			return members.stream()
					.map(Member::getId)
					.toList();
		} else {
			Member member = memberRepository.findByUserIdAndGroupId(userId, groupId)
					.orElseThrow(() -> new ToyouException(ResponseType.MEMBER_NOT_FOUND));
			return List.of(member.getId());
		}
	}
}
