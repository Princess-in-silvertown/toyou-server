package slvtwn.khu.toyouserver.response;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import slvtwn.khu.toyouserver.common.PageInfoResponse;

@AutoConfigureMockMvc
@SpringBootTest
public class ResponseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResponseService responseService;

	@DisplayName("서버 응답 테스트를 실행한다.")
	@Test
	public void 응답_테스트() throws Exception {
		PageableDto expect = new PageableDto(1L, "test", PageInfoResponse.of(1, 1, 1));
		given(responseService.health())
				.willReturn(expect);
		mockMvc.perform(get("/test"))
				.andExpect(status().isOk())
				.andExpect(result -> {
					String contentAsString = result.getResponse().getContentAsString();
					System.out.println(contentAsString);
				});
	}
}