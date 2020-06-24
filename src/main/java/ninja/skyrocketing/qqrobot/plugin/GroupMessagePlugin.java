package ninja.skyrocketing.qqrobot.plugin;

import lombok.SneakyThrows;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import ninja.skyrocketing.qqrobot.pojo.CoolQMessage;
import ninja.skyrocketing.qqrobot.pojo.IdList;
import ninja.skyrocketing.qqrobot.pojo.Matches;
import ninja.skyrocketing.qqrobot.service.impl.RepeaterServiceImpl;
import ninja.skyrocketing.qqrobot.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Component
public class GroupMessagePlugin extends CQPlugin {
	@Autowired
	private IdList idList;
	@Autowired
	private Matches matches;
	@SneakyThrows
	@Override
	public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getGroupId(), event.getUserId(), cq);
		int randomNum = RandomUtil.getRandomNum(100);
		boolean userNotInBlacklist = !idList.getUser().contains(coolQMessage.getUserId());
		boolean groupNotInBlacklist = !idList.getUser().contains(coolQMessage.getGroupId());
		String keyWord = matches.getReply(coolQMessage.getMsg(), randomNum);
		
		if (userNotInBlacklist) {
			//关键字触发
			if (keyWord != null) {
				String[] className = keyWord.split("\\.");
				Class<?> clz = Class.forName("ninja.skyrocketing.qqrobot.service.impl." + className[0]);
				Method method = clz.getMethod(className[1], CoolQMessage.class);
				Constructor<?> constructor = clz.getConstructor();
				Object object = constructor.newInstance();
				String reply = (String) method.invoke(object, coolQMessage);
				System.out.println("[ userId: " + coolQMessage.getUserId() +
						" groupId: " + coolQMessage.getGroupId() +
						" msg: " + coolQMessage.getMsg() + " replyMsg: " + reply + " ]");
				return MESSAGE_BLOCK;
			}
			//复读概率 2%
			if(randomNum > 98 && groupNotInBlacklist) {
				RepeaterServiceImpl.randomRepeater(coolQMessage);
				System.out.println(coolQMessage.getUserId() + " " + coolQMessage.getGroupId() + " " + coolQMessage.getMsg());
				return MESSAGE_BLOCK;
			}
		}
		return MESSAGE_IGNORE;
	}
}
