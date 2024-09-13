package slvtwn.khu.toyouserver.agent.gpt;

import java.util.List;

public record ChatGptResponse(String id, String object, Long created, List<Choice> choices, Usage usage) {

    public record Choice(
            String text,
            int index,
            String logprobs,
            String finish_reason
    ) {
    }

    public record Usage(
            int prompt_tokens,
            int completion_tokens,
            int total_tokens
    ) {
    }
}
