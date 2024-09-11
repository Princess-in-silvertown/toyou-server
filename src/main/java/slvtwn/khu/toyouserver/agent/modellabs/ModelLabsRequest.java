package slvtwn.khu.toyouserver.agent.modellabs;

import lombok.Builder;

@Builder
public record ModelLabsRequest(
        String key,
        String model_id,
        String prompt,
        String negative_prompt,
        String width,
        String height,
        String samples,
        String num_inference_steps,
        String safety_checker,
        String enhance_prompt,
        String seed,
        int guidance_scale,
        String panorama,
        String self_attention,
        String upscale,
        String embeddings_model,
        String lora_model,
        String tomesd,
        String use_karras_sigmas,
        String vae,
        String lora_strength,
        String scheduler,
        String webhook,
        String track_id
) {
}
