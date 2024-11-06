package slvtwn.khu.toyouserver.dto;

public record RollingPaperExchangeCountResponse(String userName, Long sentCount, Long receivedCount) {
	public static RollingPaperExchangeCountResponse of(String userName, Long sentCount, Long receivedCount) {
		return new RollingPaperExchangeCountResponse(userName, sentCount, receivedCount);
	}
}
