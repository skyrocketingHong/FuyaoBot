package ninja.skyrocketing.fuyao.bot.config;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.function.TimelyFunction;
import ninja.skyrocketing.fuyao.bot.listener.bot.BotMessageListener;
import ninja.skyrocketing.fuyao.bot.listener.friend.FriendEventListener;
import ninja.skyrocketing.fuyao.bot.listener.friend.FriendMessageListener;
import ninja.skyrocketing.fuyao.bot.listener.group.GroupEventListener;
import ninja.skyrocketing.fuyao.bot.listener.group.GroupMessageListener;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotQQ;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
import ninja.skyrocketing.fuyao.bot.sender.friend.FriendMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 11:29:59
 */

@Component
public class MiraiBotConfig {
    private static BotConfigService botConfigService;
    @Autowired
    public MiraiBotConfig(BotConfigService botConfigService) {
        MiraiBotConfig.botConfigService = botConfigService;
    }
    
    public static GlobalVariables globalVariables = new GlobalVariables();

    /**
     * 全局jar根目录
     * */
    public static final String JAR_PATH =  FileUtil.getPath();
    /**
     * 全局cache目录
     * */
    public static final String CACHE_PATH = JAR_PATH + FileUtil.separator + "cache";
    /**
     * 全局log文件的File对象
     * */
    public static final String LOG_FILE = CACHE_PATH + FileUtil.separator + "log" + FileUtil.separator;
    /**
     * 全局hs卡牌缓存目录
     * */
    public static final String HS_CACHE_PATH = CACHE_PATH + FileUtil.separator + "Hearthstone";
    /**
     * 全局防止滥用变量
     * */
    public static Map<User, Long> GroupUserTriggerDelay = new HashMap<>();
    /**
     * 全局防止滥用（已通知）变量
     * */
    public static List<User> userTriggerDelayNotified = new ArrayList<>();

    /**
     * 根据模式获得不同的qq号
     * */
    @Deprecated(since = "4.4.5.61")
    public static BotQQ setBotQQByMode(boolean devMode) {
        BotQQ botQQ = new BotQQ();
        if (devMode) {
            botQQ.setQqId(Long.parseLong(botConfigService.getConfigValueByKey("qq_id_dev")));
            botQQ.setQqPassword(botConfigService.getConfigValueByKey("qq_password_dev"));
        } else {
            botQQ.setQqId(Long.parseLong(botConfigService.getConfigValueByKey("qq_id")));
            botQQ.setQqPassword(botConfigService.getConfigValueByKey("qq_password"));
        }
        return botQQ;
    }

    /**
     * 运行机器人
     * */
    public static void runBot() throws IOException {
        //bot配置
        FuyaoBotApplication.bot = BotFactory.INSTANCE.newBot(
                Long.parseLong(botConfigService.getConfigValueByKey("qq_id")),
                botConfigService.getConfigValueByKey("qq_password"),
                new BotConfiguration() {{
                    //设备缓存信息
                    setProtocol(MiraiProtocol.IPAD);
                    fileBasedDeviceInfo("device.json");
                    //心跳策略
                    setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
                    //开启所有列表缓存
                    enableContactCache();
                    //可选设置有更新时的保存时间间隔
                    new ContactListCache().setSaveIntervalMillis(10000);
                }}
        );
        //登录
        FuyaoBotApplication.bot.login();

        //注册监听事件
        EventChannel<BotEvent> eventChannel = FuyaoBotApplication.bot.getEventChannel();
        eventChannel.registerListenerHost(new GroupMessageListener());
        eventChannel.registerListenerHost(new GroupEventListener());
        eventChannel.registerListenerHost(new BotMessageListener());
        eventChannel.registerListenerHost(new FriendEventListener());
        eventChannel.registerListenerHost(new FriendMessageListener());

        //运行定时消息模块
        TimelyFunction.timelyMessage();

        //发送启动成功消息
        Date endDate = new Date();
        FriendMessageSender.sendMessageByFriendId(
                "✔ 启动成功" + "\n" +
                        "耗费时间：" + DateUtil.between(FuyaoBotApplication.StartDate, endDate, DateUnit.SECOND) + "s",
                FuyaoBotApplication.bot.getFriend(Long.parseLong(botConfigService.getConfigValueByKey("admin_user")))
        );

        //挂载该机器人的线程
        FuyaoBotApplication.bot.join();
    }
}
