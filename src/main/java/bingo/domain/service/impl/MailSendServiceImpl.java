package bingo.domain.service.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import bingo.domain.service.MailSendService;

@Service
@Transactional(readOnly = true)
public class MailSendServiceImpl implements MailSendService {

	@Value("${error.mail.address.to}")
	private String toAddress;

	@Value("${error.mail.address.from}")
	private String fromAddress;

	@Value("${ses.access.key.id}")
	private String sesAccessKeyId;

	@Value("${ses.access.secret.key}")
	private String sesAccessSecretKey;

	@Override
	public void sendErrorMail(Throwable e) {

		try {
			AmazonSimpleEmailService client =
					AmazonSimpleEmailServiceClientBuilder.standard()
														 .withCredentials(new AWSCredentialsProvider() {

															@Override
															public void refresh() {

															}

															@Override
															public AWSCredentials getCredentials() {
																return new BasicAWSCredentials(sesAccessKeyId, sesAccessSecretKey);
															}
														})
														 .withRegion(Regions.US_EAST_1).build();

			SendEmailRequest request = new SendEmailRequest()
											.withDestination(new Destination().withToAddresses(toAddress))
											.withMessage(new Message()
													.withBody(new Body()
															.withText(new Content()
																	.withCharset("UTF-8")
																	.withData(ExceptionUtils.getStackTrace(e))))
													.withSubject(new Content()
															.withCharset("UTF-8")
															.withData("500エラー発生")))
											.withSource(fromAddress);

			// メール送信
			client.sendEmail(request);
			System.out.println("mail send");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
