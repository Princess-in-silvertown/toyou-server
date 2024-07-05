package slvtwn.khu.toyouserver.common;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slvtwn.khu.toyouserver.exception.ToyouException;

public class ExceptionTest {
	@DisplayName("비즈니스 예외를 정의할 수 있다")
	@Test
	public void 비즈니스_예외_생성() {
		Assertions.assertThrows(ToyouException.class, () -> {
			throw new ToyouException(ErrorType.BAD_REQUEST);
		});
	}

	@DisplayName("비즈니스 예외는 RuntimeException을 상속받는다")
	@Test
	public void 비즈니스_예외는_RuntimeException을_상속받는다() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			throw new ToyouException(ErrorType.BAD_REQUEST);
		});
	}

	@DisplayName("NOT_FOUND는 TYU-404 코드를 가진다")
	@Test
	public void NOT_FOUND는_TYU404_코드를_가진다() {
		ToyouException throwable = Assertions.assertThrows(ToyouException.class, () -> {
			throw new ToyouException(ErrorType.NOT_FOUND);
		});
		Assertions.assertEquals("TYU-404", throwable.code());
	}

	@DisplayName("표준 예외는 공통 응답 형식을 따른다")
	@Test
	public void 표준_예외는_공통_응답_형식을_따른다() {
		ToyouException throwable = Assertions.assertThrows(ToyouException.class, () -> {
			throw new ToyouException(ErrorType.BAD_REQUEST);
		});
		Assertions.assertEquals("TYU-400", throwable.code());
		Assertions.assertEquals("잘못된 요청입니다.", throwable.message());
	}
}
