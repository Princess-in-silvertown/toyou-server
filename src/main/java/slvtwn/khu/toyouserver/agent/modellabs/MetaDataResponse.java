package slvtwn.khu.toyouserver.agent.modellabs;

public record MetaDataResponse(
        String prompt,
        String model_id,
        String negative_prompt,
        String scheduler,
        String safetychecker,
        int W,
        int H,
        double guidance_scale,
        long seed,
        int steps,
        int n_samples,
        String full_url,
        String upscale,
        String panorama,
        String self_attention,
        String embeddings,
        String lora_model,
        String lora_strength,
        String outdir,
        String file_prefix
) {
}

