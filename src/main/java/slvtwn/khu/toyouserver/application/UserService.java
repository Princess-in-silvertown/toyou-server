package slvtwn.khu.toyouserver.application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Event;
import slvtwn.khu.toyouserver.domain.EventType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupRequest;
import slvtwn.khu.toyouserver.dto.UserResponse;
import slvtwn.khu.toyouserver.dto.UserUpdateRequest;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.EventRepository;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.MemberRepository;
import slvtwn.khu.toyouserver.persistance.RollingPaperRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

	private static final int PERIOD_UPPER_BOUND = 100;

	private final UserRepository userRepository;
	private final MemberRepository memberRepository;
	private final GroupRepository groupRepository;
	private final EventRepository eventRepository;
	private final RollingPaperRepository rollingPaperRepository;

	public UserResponse getProfile(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));
		return UserResponse.of(user);
	}

	// TODO: 쿼리 / 구조 최적화 & N + 1
	public List<UserResponse> findUsersWithFilteringOptions(Long userId, String search, Long groupId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));
		if (groupId == null) {
			return findAllUsersWithSameGroups(user, search);
		}
		return findUsersWithSpecificGroup(user, search, groupId);
	}

	private List<UserResponse> findAllUsersWithSameGroups(User user, String search) {
		return memberRepository.findByUser(user).stream()
				.map(Member::getGroup)
				.map(each -> memberRepository.findByGroupAndUserNameLike(each, search))
				.flatMap(List::stream)
				.filter(each -> !each.getUser().getId().equals(user.getId()))
				.map(each -> UserResponse.of(each.getUser(), each.getGroup().getId()))
				.toList();
	}

	private List<UserResponse> findUsersWithSpecificGroup(User user, String search, Long groupId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ToyouException(ResponseType.GROUP_NOT_FOUND));

		return memberRepository.findByGroupAndUserNameLike(group, search).stream()
				.filter(each -> !each.getUser().getId().equals(user.getId()))
				.map(each -> UserResponse.of(each.getUser(), each.getGroup().getId()))
				.toList();
	}

	@Transactional
	public void updateUser(Long userId, UserUpdateRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));

		user.updateInfo(request.name(), request.birthday(), request.introduction(), request.imageUrl());
		createBirthdayEvents(user);
		changeUserGroups(user, request.groups());
	}

	private void createBirthdayEvents(User user) {
		eventRepository.deleteAllByUserIs(user);
		List<Event> events = generatePeriodicalBirthdayEvents(user);
		eventRepository.saveAll(events);
	}

	private static List<Event> generatePeriodicalBirthdayEvents(User user) {
		List<Event> events = new ArrayList<>();
		LocalDate birthday = user.getBirthday();

		for (int yearAfter = 1; yearAfter <= PERIOD_UPPER_BOUND; yearAfter++) {
			LocalDate birthdayYearAfter = birthday.plus(yearAfter, ChronoUnit.YEARS);
			events.add(new Event(user.getName(), birthdayYearAfter, EventType.BIRTHDAY,
					"오늘 생일이에요!", user));
		}
		return events;
	}

	private void changeUserGroups(User user, List<GroupRequest> groupRequests) {
		List<Member> members = memberRepository.findByUser(user);

		rollingPaperRepository.deleteAllByMemberIn(members);
		memberRepository.deleteAll(members);

		saveMembersWithNewGroups(user, groupRequests);
	}

	private void saveMembersWithNewGroups(User user, List<GroupRequest> groupRequests) {
		List<Member> newMembers = groupRequests.stream()
				.map(each -> groupRepository.findById(each.id())
						.orElseThrow(() -> new ToyouException(ResponseType.BAD_REQUEST)))
				.map(each -> new Member(user, each))
				.map(memberRepository::save)
				.toList();
		memberRepository.saveAll(newMembers);
	}
}
