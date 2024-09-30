package slvtwn.khu.toyouserver.agent.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.exception.ToyouException;

@RequiredArgsConstructor
@Component
public class S3Agent {

    private final AmazonS3Client amazonS3Client;
    private final AwsConfiguration configuration;

    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(fileData.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
            amazonS3Client.putObject(new PutObjectRequest(configuration.getBucket(), fileName, byteArrayInputStream, metadata));

            return fileName;
        } catch (Exception e) {
            throw new ToyouException(ResponseType.INTERNAL_SERVER_ERROR);
        }
    }

    public String getUrl(String key) {
        return amazonS3Client.getResourceUrl(configuration.getBucket(), key);
    }
}
