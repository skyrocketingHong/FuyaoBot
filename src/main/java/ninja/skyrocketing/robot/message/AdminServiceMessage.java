package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.YamlUtil;

import java.io.IOException;

public class AdminServiceMessage {
	public static Message refreshConfigFile(CoolQMessage coolQMessage) throws IOException {
		boolean isAdmin = coolQMessage.getYamlFile().getIdList().get("admin").contains(coolQMessage.getUserId().toString());
		if (isAdmin) {
			coolQMessage.getYamlFile().delAllMap();
			coolQMessage.getYamlFile().setAllMap(new YamlUtil().getYaml());
			return coolQMessage.sendMsg("刷新配置文件成功");
		}
		return coolQMessage.sendMsg("非管理员，无法操作！");
	}
}
