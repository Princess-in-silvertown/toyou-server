package slvtwn.khu.toyouserver.agent.modellabs;

import java.util.List;

public record ModelLabsResponse(
        String status,
        double generationTime,
        int id,
        List<String> output,
        MetaDataResponse meta
) {

}
