package ninja.skyrocketing.qqrobot.service.impl;

import net.lz1998.cq.utils.CQCode;
import ninja.skyrocketing.qqrobot.pojo.CoolQMessage;
import ninja.skyrocketing.qqrobot.util.FileUtil;
import ninja.skyrocketing.qqrobot.util.RegexUtil;
import ninja.skyrocketing.qqrobot.util.TimeUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class QueryServiceImpl {
	//更新日志
	public static String releaseNote(CoolQMessage coolQMessage) throws IOException {
		String func = null;
		if(coolQMessage.getUserId() == 1055823718){
			ClassPathResource classPathResource = new ClassPathResource("note.txt");
			try (InputStream input = classPathResource.getInputStream()) {
				func = FileUtil.readAsString(input);
			}
			coolQMessage.sendGroupMessage(func);
		}
		return func;
	}
	
	//获取功能
	public static String getFunction(CoolQMessage coolQMessage) throws IOException {
		String func;
		ClassPathResource classPathResource = new ClassPathResource("function.txt");
		try (InputStream input = classPathResource.getInputStream()) {
			func = FileUtil.readAsString(input);
		}
		coolQMessage.sendGroupMessage(func);
		return func;
	}
	
	//最后发言时间
	public static String lastSeenTime(CoolQMessage coolQMessage) {
		//(^|\d)([0-9]{6,13})($^|\d)
		//(^|lastSentTime=)([0-9]{6,13})($^|\d)
		List<String> queryId = RegexUtil.extractMessage("(^|\\d)([0-9]{6,13})($^|\\d)", coolQMessage.getMsg());
		if (!queryId.isEmpty()) {
			String result = coolQMessage.getCoolQ().getGroupMemberInfo(coolQMessage.getGroupId(), Long.parseLong(queryId.get(0)), true).toString();
			List<String> lastSeenTimeString = RegexUtil.extractMessage("(^|lastSentTime=)([0-9]{6,13})($^|\\d)", result);
			String[] lastSeenTime = TimeUtil.getDateTimeOfTimestamp(Long.parseLong(lastSeenTimeString.get(0).split("=")[1])).toString().split("T");
			String resultMessage = CQCode.at(coolQMessage.getUserId()) + "\n" + queryId.get(0) + "的最后发言时间为\n" + lastSeenTime[0] + " " + lastSeenTime[1];
			coolQMessage.sendGroupMessage(resultMessage);
			return result;
		}
		return null;
	}
	
	//课表查询
	public static void course(CoolQMessage coolQMessage) {
	
	}
}
