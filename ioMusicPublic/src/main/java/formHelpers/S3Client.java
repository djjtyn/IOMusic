//This class wil lfacilitate the uploading of images to an S3 bucket

package formHelpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Service
public class S3Client {
	
	private AmazonS3 amazonS3;
	
	//The endpoint variable contains the buckets URL address
	@Value("${S3Endpoint}")
	private String endpoint;
	
	@Value("${S3BucketName}")
	private String bucketName;
	
	@Value("${S3AccessKey}")
	private String accessKey;
	
	@Value("${S3SecretKey}")
	private String secretKey;

	public AmazonS3 getAmazonS3() {
		return amazonS3;
	}

	public void setAmazonS3(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	//Constructors
	public S3Client() {}
	
	public S3Client(String endpoint, String bucketName, String accessKey, String secretKey) {
		super();
		this.endpoint = endpoint;
		this.bucketName = bucketName;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public void createConnection() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.amazonS3 = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.EU_WEST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
	}
}
