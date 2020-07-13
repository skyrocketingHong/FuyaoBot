package ninja.skyrocketing.robot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import ninja.skyrocketing.robot.entity.YamlFileEntity;
import ninja.skyrocketing.robot.message.LoggerMessage;
import ninja.skyrocketing.robot.sender.GroupMessageSender;

public class GroupMessageListener extends SimpleListenerHost {
	static YamlFileEntity yamlFileEntity;
	
	public GroupMessageListener(YamlFileEntity file) {
		yamlFileEntity = file;
	}
	
	@EventHandler
	public void onMessage(GroupMessageEvent event) throws Exception {
		Message message = GroupMessageSender.Sender(event, yamlFileEntity);
		if (!yamlFileEntity.isBanedUser(event.getSender().getId())) {
			if (message != null) {
				event.getGroup().sendMessage(message);
				Thread.sleep(1000);
				LoggerMessage.logger(event, message, Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0)));
			}
			if (event.getMessage().toString().contains("[mirai:at:3055823718]")) {
				event.getGroup().sendMessage("@我是没用的，推荐发送\n憨憨机器人的功能\n获取功能列表");
				LoggerMessage.logger(event, new PlainText("被@了"), Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0)));
			}
		}
	}
}
