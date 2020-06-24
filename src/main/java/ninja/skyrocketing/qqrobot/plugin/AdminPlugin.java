package ninja.skyrocketing.qqrobot.plugin;

import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import ninja.skyrocketing.qqrobot.pojo.IdList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminPlugin extends CQPlugin {
	@Autowired
	private IdList idList;
	@Override
	public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
		long userId = event.getUserId();
		boolean userIsAdmin = idList.getUser().contains(userId);
		String msg = event.getMessage();
		
		if(userIsAdmin){
			if (msg.equals("查看插件状态")) {
				cq.sendPrivateMsg(userId, String.valueOf(cq.getStatus()), false);
				return MESSAGE_BLOCK;
			}
			if (msg.equals("清理数据目录")) {
				cq.sendPrivateMsg(userId, String.valueOf(cq.cleanDataDir("image")), false);
				return MESSAGE_BLOCK;
			}
		} else {
			cq.sendPrivateMsg(userId, "非管理员，无法操作！", false);
			return MESSAGE_BLOCK;
		}
		return MESSAGE_IGNORE;
	}
}
