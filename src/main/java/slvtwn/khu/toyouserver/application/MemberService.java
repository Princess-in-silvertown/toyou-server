package slvtwn.khu.toyouserver.application;

import org.springframework.stereotype.Service;
import slvtwn.khu.toyouserver.common.ErrorType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.MemberRepository;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@Service
public class MemberService {

	private final UserRepository userRepository;
	private final MemberRepository memberRepository;
	private final GroupRepository groupRepository;


	public MemberService(GroupRepository groupRepository, UserRepository userRepository,
	                     MemberRepository memberRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.memberRepository = memberRepository;
	}

	public Member registerMember(long groupId, long userId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ToyouException(ErrorType.GROUP_NOT_FOUND));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ErrorType.USER_NOT_FOUND));
		return memberRepository.save(new Member(user, group));
	}
}
