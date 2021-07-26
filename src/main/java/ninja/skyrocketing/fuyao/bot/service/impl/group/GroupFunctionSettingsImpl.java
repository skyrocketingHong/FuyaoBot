package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupFunctionSettingsMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupFunctionSettings;
import ninja.skyrocketing.fuyao.bot.service.group.GroupFunctionSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2021-7-25 10:56
 */

@Service
public class GroupFunctionSettingsImpl implements GroupFunctionSettingsService {
	private static GroupFunctionSettingsMapper groupFunctionSettingsMapper;
	@Autowired
	public GroupFunctionSettingsImpl(GroupFunctionSettingsMapper groupFunctionSettingsMapper) {
		GroupFunctionSettingsImpl.groupFunctionSettingsMapper = groupFunctionSettingsMapper;
	}
	
	/**
	 * 获取所有GroupFunctionSettings
	 */
	@Override
	public GroupFunctionSettings getAllGroupFunctionSettings() {
		return null;
	}
}
