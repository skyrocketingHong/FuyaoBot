package ninja.skyrocketing.fuyao.bot.function;

import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2021-08-15 11:58
 */

@Component
@NoArgsConstructor
public class ConfigFunction {
	/**
	 * 机器人群名片判断，防止恶意修改
	 * */
	public static void botNameCardCheck(Group group) {
		NormalMember botInGroup = group.getBotAsMember();
		//群名片
		String botNameCard = botInGroup.getNameCard();
		//bot自己的名字
		String botNick = botInGroup.getNick();
		//不同时直接修改为bot的名字
		if (!botNameCard.equals(botNick)) {
			botInGroup.setNameCard(botNick);
		}
	}
}
