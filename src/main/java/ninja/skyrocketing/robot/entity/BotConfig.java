package ninja.skyrocketing.robot.entity;

import ninja.skyrocketing.robot.dao.*;
import ninja.skyrocketing.robot.entity.datebase.*;

import java.util.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:09:10
 * @Version 1.0
 */
public class BotConfig {
	public static ConfigDao config;
	public static GroupIdDao groupId;
	public static MessageQueueDao messageQueue;
	public static TriggerDao trigger;
	public static UserExpDao userExp;
	public static UserIdDao userId;
	public static OverwatchModeDao overwatchMode;
	public static FuckDao fuck;
	
	private static Set<Long> bannedUsers = new HashSet<>();
	private static Set<Long> adminUsers = new HashSet<>();
	private static Set<Long> bannedGroups = new HashSet<>();
	private static Set<Long> adminGroups = new HashSet<>();
	private static Set<Long> flashImageGroups = new HashSet<>();
	
	private static List<Trigger> triggers = new ArrayList<>();
	private static Map<String, String> triggersCommand = new HashMap<>();
	private static Map<UserExpIds, UserExp> userExpMap = new HashMap<>();
	private static Map<String, String> configMap = new HashMap<>();
	private static Map<Integer, String> fuckWordsMap = new HashMap<>();
	
	public BotConfig(ConfigDao configDao, GroupIdDao groupIdDao, MessageQueueDao messageQueueDao,
	                 TriggerDao triggerDao, UserExpDao userExpDao, UserIdDao userIdDao,
	                 OverwatchModeDao overwatchModeDao, FuckDao fuckDao) {
		BotConfig.config = configDao;
		BotConfig.groupId = groupIdDao;
		BotConfig.messageQueue = messageQueueDao;
		BotConfig.trigger = triggerDao;
		BotConfig.userExp = userExpDao;
		BotConfig.userId = userIdDao;
		BotConfig.overwatchMode = overwatchModeDao;
		BotConfig.fuck = fuckDao;
		
		refresh();
	}
	
	
	/**
	 * 刷新数据库数据缓存
	 **/
	public static void refresh() {
		refreshTriggerList();
		configListToMap();
		userExpListToMap();
		divideUsers();
		divideGroups();
		fuckListToMap();
	}
	
	public static void refreshTriggerList() {
		triggers.clear();
		triggers = getAllTrigger();
		triggersToCommandMap();
	}
	
	
	/**
	 * 获取处理后的数据库数据
	 **/
	public static Set<Long> getBannedUsers() {
		return bannedUsers;
	}
	
	public static Set<Long> getAdminUsers() {
		return adminUsers;
	}
	
	public static Set<Long> getBannedGroups() {
		return bannedGroups;
	}
	
	public static Set<Long> getAdminGroups() {
		return adminGroups;
	}
	
	public static Set<Long> getFlashImageGroups() {
		return flashImageGroups;
	}
	
	public static List<Trigger> getTriggers() {
		return triggers;
	}
	
	public static Map<String, String> getTriggersCommand() {
		return triggersCommand;
	}
	
	public static Map<UserExpIds, UserExp> getUserExpMap() {
		return userExpMap;
	}
	
	public static void setUserExpMap(UserExp userExpTmp) {
		BotConfig.userExpMap.put(userExpTmp.getUserExpIds(), userExpTmp);
		userExp.save(userExpTmp);
	}
	
	public static Map<String, String> getConfigMap() {
		return configMap;
	}
	
	public static void setConfigMap(Integer id, String key, String value) {
		configMap.put(key, value);
		config.save(new Config(id, key, value));
		configListToMap();
	}
	
	public static Map<Integer, String> getFuckWordsMap() {
		return fuckWordsMap;
	}
	
	
	/**
	 * List转Map
	 **/
	private static void configListToMap() {
		configMap.clear();
		for (Config config : getAllConfig()) {
			configMap.put(config.getName(), config.getValue());
		}
	}
	
	private static void userExpListToMap() {
		userExpMap.clear();
		for (UserExp userExp : getAllUserExp()) {
			userExpMap.put(userExp.getUserExpIds(), userExp);
		}
	}
	
	private static void fuckListToMap() {
		fuckWordsMap.clear();
		for (Fuck fuck : getAllFuck()) {
			fuckWordsMap.put(fuck.getId(), fuck.getFuckWords());
		}
	}
	
	private static void triggersToCommandMap() {
		triggersCommand.clear();
		for (Trigger trigger : triggers) {
			if (trigger.isCommandable()) {
				triggersCommand.put(trigger.getCommand(), trigger.getImplementation());
			}
		}
	}
	
	
	/**
	 * QQ群和QQ号分类集合，便于判断
	 **/
	private static void divideGroups() {
		bannedGroups.clear();
		adminGroups.clear();
		flashImageGroups.clear();
		List<GroupId> groupIdList = getAllGroupId();
		for (GroupId groupId : groupIdList) {
			if (groupId.isBanned()) {
				bannedGroups.add(groupId.getId());
			}
			if (groupId.isAdmin()) {
				adminGroups.add(groupId.getId());
			}
			if (groupId.isFlashImage()) {
				flashImageGroups.add(groupId.getId());
			}
		}
	}
	
	private static void divideUsers() {
		adminUsers.clear();
		bannedUsers.clear();
		List<UserId> userIdList = getAllUserId();
		for (UserId userId : userIdList) {
			if (userId.isAdmin()) {
				adminUsers.add(userId.getId());
			}
			if (userId.isBanned()) {
				bannedUsers.add(userId.getId());
			}
		}
	}
	
	/**
	 * 获取单个表的数据
	 **/
	private static List<Config> getAllConfig() {
		return config.findAll();
	}
	
	private static List<GroupId> getAllGroupId() {
		return groupId.findAll();
	}
	
	private static List<MessageQueue> getAllMessageQueue() {
		return messageQueue.findAll();
	}
	
	private static List<Trigger> getAllTrigger() {
		return trigger.findAll();
	}
	
	private static List<UserExp> getAllUserExp() {
		return userExp.findAll();
	}
	
	private static List<UserId> getAllUserId() {
		return userId.findAll();
	}
	
	private static List<OverwatchMode> getAllOverwatchMode() {
		return overwatchMode.findAll();
	}
	
	public static List<Fuck> getAllFuck() {
		return fuck.findAll();
	}
}
