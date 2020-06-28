package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.YamlUtil;

import java.io.IOException;

public class AdminServiceImpl {
	public static String refreshConfigFile(CoolQMessage coolQMessage) throws IOException {
		boolean isAdmin = coolQMessage.getYamlFile().getIdList().get("admin").contains(coolQMessage.getUserId().toString());
		if (isAdmin) {
			coolQMessage.getYamlFile().delAllMap();
			coolQMessage.getYamlFile().setAllMap(new YamlUtil().getYaml());
			return "刷新配置文件成功";
		}
		return "非管理员，无法操作！";
	}
}
