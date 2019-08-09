package pushy;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.ApnsClientBuilder;
import com.relayrides.pushy.apns.ClientNotConnectedException;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class PushyExample {

	public static void main(String[] args) throws Exception {
		final ApnsClient apnsClient = new ApnsClientBuilder()
				.setClientCredentials(new File("/letv/data/certification/iPadPushDev.p12"), "111111").build();
		final Future<Void> connectFutrue = apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
		// 等待连接apns成功, 良好的编程习惯，需要有最长等待时间
		try {
			connectFutrue.await(10 , TimeUnit.MINUTES);
		} catch (Exception e) {
			if(e instanceof InterruptedException) {
				System.out.println("Failed to connect APNs , timeout");
			}
			e.printStackTrace();
		}
		final ApnsPayloadBuilder payBuilder = new ApnsPayloadBuilder();
		payBuilder.setAlertBody("pushy Example");
		String payload = payBuilder.buildWithDefaultMaximumLength();
		final String token = "68a246d33b21ba175743abe3791eae07140880f492a5f8a8984af77d1fd0f4f3";
		SimpleApnsPushNotification notification = new SimpleApnsPushNotification(token, null, payload);
		Future<PushNotificationResponse<SimpleApnsPushNotification>> responseFuture = apnsClient
				.sendNotification(notification);
		responseFuture
				.addListener(new GenericFutureListener<Future<PushNotificationResponse<SimpleApnsPushNotification>>>() {

					@Override
					public void operationComplete(Future<PushNotificationResponse<SimpleApnsPushNotification>> arg0)
							throws Exception {
						try {
							final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse = arg0
									.get();
							
							if (pushNotificationResponse.isAccepted()) {
								System.out.println("Push notification accepted by APNs gateway.");
							} else {
								System.out.println("Notification rejected by the APNs gateway: "
										+ pushNotificationResponse.getRejectionReason());

								if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {
									System.out.println("\t…and the token is invalid as of "
											+ pushNotificationResponse.getTokenInvalidationTimestamp());
								}
							}
						} catch (final ExecutionException e) {
							System.err.println("Failed to send push notification.");
							e.printStackTrace();

							if (e.getCause() instanceof ClientNotConnectedException) {
								System.out.println("Waiting for client to reconnect…");
								apnsClient.getReconnectionFuture().await();
								System.out.println("Reconnected.");
							}
						}
					}
				});
		// 结束后关闭连接, 该操作会直到所有notification都发送完毕并回复状态后关闭连接
		Future<Void> disconnectFuture = apnsClient.disconnect();
		try {
			disconnectFuture.await(1 , TimeUnit.HOURS);
		} catch (Exception e) {
			if(e instanceof InterruptedException) {
				System.out.println("Failed to disconnect APNs , timeout");
			}
			e.printStackTrace();
		}
		
	}
}
