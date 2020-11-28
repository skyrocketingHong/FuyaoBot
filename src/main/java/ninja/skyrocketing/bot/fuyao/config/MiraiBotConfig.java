package ninja.skyrocketing.bot.fuyao.config;

import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.listener.group.GroupMessageListener;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotQQ;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 11:29:59
 * @Version 1.0
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

    public static void RunBot(boolean devMode) throws InterruptedException {
        BotQQ botQQ = SetBotQQByMode(devMode);
        // 设备缓存信息
        FuyaoBotApplication.bot = BotFactoryJvm.newBot(
                botQQ.getQqId(),
                botQQ.getQqPassword(),
                new BotConfiguration() {{
                    // 设备缓存信息
                    setProtocol(MiraiProtocol.ANDROID_PHONE);
                    fileBasedDeviceInfo("deviceInfo.json");
                }}
        );
        // 登录
        FuyaoBotApplication.bot.login();

        // 注册监听事件
        Events.registerEvents(FuyaoBotApplication.bot, new GroupMessageListener());

        // 挂载该机器人的线程
        FuyaoBotApplication.bot.join();
    }
}
