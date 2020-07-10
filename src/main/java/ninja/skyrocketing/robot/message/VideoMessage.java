package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.LightApp;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.VideoSearchUtil;

import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-09 009 12:38:53
 * @Version 1.0
 */
public class VideoMessage {
	public static Message bilibiliVideo(CoolQMessage coolQMessage) throws IOException {
		String json = VideoSearchUtil.bilibiliVideo(coolQMessage.getMsg().replaceAll("^来.*个视频\\s*", ""));
		return new LightApp(json);
	}
}
