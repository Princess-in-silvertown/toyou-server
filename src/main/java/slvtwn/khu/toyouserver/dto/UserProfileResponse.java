package slvtwn.khu.toyouserver.dto;

import java.time.LocalDate;
import java.util.List;

public record UserProfileResponse(Long id, LocalDate birthday, String name, String introduction,
                                  String imageUrl, List<GroupResponse> groups) {

}
