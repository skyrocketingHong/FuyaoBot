package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.util.YamlUtil;

import java.io.IOException;

public class AdminMessage {
	public static Message refreshConfigFile(MessageEntity messageEntity) throws IOException {
		boolean isAdmin = messageEntity.getYamlFile().getIdList().get("admin").contains(messageEntity.getUserId().toString());
		if (isAdmin) {
			messageEntity.getYamlFile().delAllMap();
			messageEntity.getYamlFile().setAllMap(new YamlUtil().getYaml());
			return messageEntity.sendMsg("刷新配置文件成功");
		} else {
			return messageEntity.sendMsg("非管理员，无法操作！");
		}
	}
}
