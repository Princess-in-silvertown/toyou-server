package slvtwn.khu.toyouserver.agent.gpt;

import java.util.List;

public record ChatGptResponse(String id, String object, Long created, List<Choice> choices, Usage usage) {

    public record Choice(
            int index,
            Message message,
            String logprobs,
            String finish_reason
    ) {
    }

    public record Message(
            String role,
            String content
    ) {
    }

    public record Usage(
            int prompt_tokens,
            int completion_tokens,
            int total_tokens
    ) {
    }
}
