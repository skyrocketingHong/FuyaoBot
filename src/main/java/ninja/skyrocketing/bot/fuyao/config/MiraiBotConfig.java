package ninja.skyrocketing.bot.fuyao.config;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.function.timely.Timely;
import ninja.skyrocketing.bot.fuyao.listener.admin.BotMessageListener;
import ninja.skyrocketing.bot.fuyao.listener.group.GroupEventListener;
import ninja.skyrocketing.bot.fuyao.listener.group.GroupMessageListener;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotQQ;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupRepeaterMessage;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 11:29:59
 */

@Component
public class MiraiBotConfig {
    private static BotConfigService botConfigService;
    @Autowired
    public MiraiBotConfig(BotConfigService botConfigService) {
        MiraiBotConfig.botConfigService = botConfigService;
    }

    //全局jar根目录
    public static final String jarPath =  FileUtil.GetPath();
    //全局log文件的File对象
    public static final File logFile = new File(jarPath + FileUtil.separator + "cache" + FileUtil.separator + "log");

    //全局复读消息变量
    public static Map<Long, GroupRepeaterMessage> GroupsRepeaterMessagesMap = new HashMap<>();

    //根据模式获得不同的qq号
    public static BotQQ SetBotQQByMode(boolean devMode) {
        BotQQ botQQ = new BotQQ();
        if (devMode) {
            botQQ.setQqId(Long.parseLong(botConfigService.GetConfigValueByKey("qq_id_dev")));
            botQQ.setQqPassword(botConfigService.GetConfigValueByKey("qq_password_dev"));
        } else {
            botQQ.setQqId(Long.parseLong(botConfigService.GetConfigValueByKey("qq_id")));
            botQQ.setQqPassword(botConfigService.GetConfigValueByKey("qq_password"));
        }
        return botQQ;
    }

    public static void RunBot(boolean devMode) throws IOException {
        BotQQ botQQ = SetBotQQByMode(devMode);
        //设备缓存信息
        FuyaoBotApplication.bot = BotFactory.INSTANCE.newBot(
                botQQ.getQqId(),
                botQQ.getQqPassword(),
                new BotConfiguration() {{
                    //设备缓存信息
                    setProtocol(MiraiProtocol.ANDROID_PHONE);
                    fileBasedDeviceInfo("deviceInfo.json");
                }}
        );
        //登录
        FuyaoBotApplication.bot.login();

        //注册监听事件
        EventChannel<BotEvent> eventChannel = FuyaoBotApplication.bot.getEventChannel();
        eventChannel.registerListenerHost(new GroupMessageListener());
        eventChannel.registerListenerHost(new GroupEventListener());
        eventChannel.registerListenerHost(new BotMessageListener());

        //运行定时消息模块
        Timely.TimelyMessage();

        //发送启动成功消息
        Date endDate = new Date();
        FuyaoBotApplication.bot.getFriend(Long.parseLong(botConfigService.GetConfigValueByKey("admin_user"))).sendMessage(
                "✔ 启动成功" + "\n" +
                        "耗费时间：" + DateUtil.between(FuyaoBotApplication.startDate, endDate, DateUnit.SECOND) + "s"
        );

        //挂载该机器人的线程
        FuyaoBotApplication.bot.join();
    }
}
