package ninja.skyrocketing.bot.fuyao.config;

import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.function.timely.Timely;
import ninja.skyrocketing.bot.fuyao.listener.admin.BotMessageListener;
import ninja.skyrocketing.bot.fuyao.listener.group.GroupMessageListener;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotQQ;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public static void RunBot(boolean devMode) {
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
        eventChannel.registerListenerHost(new BotMessageListener());

        //运行定时消息模块
        Timely.TimelyMessage();

        //挂载该机器人的线程
        FuyaoBotApplication.bot.join();
    }
}
