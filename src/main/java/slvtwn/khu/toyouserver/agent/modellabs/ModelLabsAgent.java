package slvtwn.khu.toyouserver.agent.modellabs;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.agent.WebClientWrapper;

@RequiredArgsConstructor
@Component
public class ModelLabsAgent {

    private final ModelLabsConfiguration configuration;
    private final WebClientWrapper webClientWrapper;

    public List<String> generateCoverWithKeywords(List<String> keywords) {
        ModelLabsRequest request = setupBody(keywords);

        ModelLabsResponse response = webClientWrapper.send(
                configuration.getModelLabsEndPoint(), (httpHeaders -> {
                }), request, ModelLabsResponse.class);

        return response.output();
    }

    private ModelLabsRequest setupBody(List<String> keywords) {
        // TODO: prompt, negative prompt, keywords
        String prompt = "";
        String negativePrompt = "";

        return ModelLabsRequest.builder()
                .prompt(prompt)
                .key(configuration.getKey())
                .model_id(configuration.getModelId())
                .negative_prompt(negativePrompt)
                .width("512")
                .height("512")
                .samples("3")
                .num_inference_steps("30")
                .safety_checker("no")
                .enhance_prompt("yes")
                .guidance_scale(9)
                .panorama("no")
                .self_attention("no")
                .upscale("no")
                .tomesd("yes")
                .use_karras_sigmas("yes")
                .scheduler("DPMSolverMultistepScheduler")
                .build();
    }
}
