package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.School;

public record SchoolSearchResponse(Long id, String name, String address, String homepageUrl) {
	public static SchoolSearchResponse from(School school) {
		return new SchoolSearchResponse(school.getId(), school.getName(), school.getAddress(), school.getHomepageUrl());
	}
}