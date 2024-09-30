package slvtwn.khu.toyouserver.application;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import slvtwn.khu.toyouserver.agent.aws.S3Agent;
import slvtwn.khu.toyouserver.agent.gpt.ChatGptAgent;
import slvtwn.khu.toyouserver.agent.gpt.ChatGptResponse;
import slvtwn.khu.toyouserver.agent.model.StickerModelAgent;
import slvtwn.khu.toyouserver.agent.model.StickerModelRequest;
import slvtwn.khu.toyouserver.dto.GenerateStickerRequest;
import slvtwn.khu.toyouserver.dto.GenerateStickerResponse;
import slvtwn.khu.toyouserver.dto.KeywordRequest;
import slvtwn.khu.toyouserver.dto.KeywordResponse;

@AllArgsConstructor
@Service
public class AgentService {

    private static final String STICKER_MIME_TYPE = "image/png";

    private final StickerModelAgent stickerModelAgent;
    private final ChatGptAgent chatGptAgent;
    private final S3Agent s3Agent;

    public GenerateStickerResponse generateStickers(GenerateStickerRequest request) {
        List<String> urls = stickerModelAgent.generateStickers(new StickerModelRequest(request.prompt(), request.color())).stream()
                .map(each -> s3Agent.uploadFile(each, UUID.nameUUIDFromBytes(each).toString(), STICKER_MIME_TYPE))
                .map(s3Agent::getUrl)
                .toList();

        return new GenerateStickerResponse(urls);
    }

    public KeywordResponse generateKeywords(KeywordRequest request) {
        String content = request.content();
        String prompt = String.format("""
                Suggest 3 keywords that could represent emotions or characteristics in the content.
                                
                <example>
                    <request>
                        content:
                            안녕, 저번 학기에 만나서 정말 반가웠어! 앞으로 또 만날 수 있을거라 기대하고 있어.
                            정말 아쉽지만 건강하게 잘 지내!
                        keywords:
                    </request>
                    <response>
                        반가움, 기대, 아쉬움
                    </response>
                </example>
                                
                <example>
                    <request>
                        content:
                            생일 축하해! 생일 축하 파티에 직접 참여하진 못해서 아쉽지만, 즐겁게 하루를 보냈으면 좋겠어.
                        keywords:
                    </request>
                    <response>
                        축하, 아쉬움, 즐거움
                    </response>
                </example>
                                
                content: %s
                keywords:                                 
                """, content);

        ChatGptResponse chatGptResponse = chatGptAgent.requestWithPrompt(prompt);
        ChatGptResponse.Choice firstChoice = chatGptResponse.choices().get(0);
        List<String> keywords = parseKeywordFromChoice(firstChoice);

        return new KeywordResponse(keywords);
    }

    private List<String> parseKeywordFromChoice(ChatGptResponse.Choice choice) {
        String text = choice.message().content();
        return Arrays.stream(text.split(", "))
                .toList();
    }
}
