package slvtwn.khu.toyouserver.response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class ResponseController {

	private final ResponseService responseService;

	public ResponseController(ResponseService responseService) {this.responseService = responseService;}

	@GetMapping
	public PageableDto health() {
		return responseService.health();
	}
}
