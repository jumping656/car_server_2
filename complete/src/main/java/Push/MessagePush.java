package Push;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.examples.PushExample;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by ejiping on 2016/3/14.
 */
public class MessagePush {

	protected static final Logger LOG = LoggerFactory.getLogger(PushExample.class);

	private static final String appKey = "43ab58a3acebd5431c2738b9";
	private static final String masterSecret = "2e6e8a9ccddaa7f6463d3070 ";
	private JPushClient jpushClient ;
	private String title;
	private String message;
	private String tag;

	public MessagePush(String message) {
		this.message = message;
		this.jpushClient = new JPushClient(masterSecret, appKey,3);
	}
	public MessagePush(String message,String title) {
		this.message = message;
		this.title=title;
		this.jpushClient = new JPushClient(masterSecret, appKey,3);
	}
	public MessagePush(String message,String title, String tag) {
		this.message = message;
		this.title=title;
		this.tag = tag;
		this.jpushClient = new JPushClient(masterSecret, appKey,3);
	}

	/**
	 * 向所有人发送消息
	 * @return 消息id
	 */
	public long sendPushAll(){
		PushPayload payload=buildPushObject_all_all_alert();
		long msgId=0;
		try {
			PushResult result=jpushClient.sendPush(payload);
			msgId=result.msg_id;
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.info("HTTP Status: " + e.getStatus());
			msgId=e.getMsgId();
		}
		return msgId;
	}
	/**
	 * 向指定别名的客户端发送消息
	 * @param alias 所有别名信息集合，这里表示发送所有学生编号
	 * @return 消息id
	 */
	public long sendPushAlias(Set<String> alias){
		PushPayload payloadAlias=buildPushObject_android_alias_alertWithTitle(alias);
		long msgId=0;
		try {
			PushResult result=jpushClient.sendPush(payloadAlias);
			msgId=result.msg_id;

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
			msgId=e.getMsgId();
		}
		return msgId;
	}
	/**
	 * 向指定组发送消息
	 * @param tag 组名称
	 * @return 消息id
	 */
	public  long sendPushTag(String tag) {
		PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle(tag);
		long msgId=0;
		try {
			PushResult result = jpushClient.sendPush(payloadtag);
			msgId=result.msg_id;
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
			msgId=e.getMsgId();
		}
		return msgId;
	}

	public PushPayload buildPushObject_all_alias_alert(Set<String> alias) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(message))
				.build();
	}

	public PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.tag(tag))
				.setNotification(Notification.android(message, title, null))
				.build();
	}

	public  PushPayload buildPushObject_android_alias_alertWithTitle(Set<String> alias) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.android(message,title,null))
				.build();
	}

	public  PushPayload buildPushObject_android_tag_alertWithTitle(String tag){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.tag(tag))
				.setNotification(Notification.android(message, title, null))
				.build();}

	public PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(message);
	}
}
