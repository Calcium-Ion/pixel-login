package work.caion.plugin.pixellogin;

import cn.hutool.core.util.StrUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import work.caion.plugin.pixellogin.action.HttpActionHandler;
import work.caion.plugin.pixellogin.login.AuthMeLoginHandler;
import work.caion.plugin.pixellogin.login.LoginHandler;

import java.util.logging.Logger;

public class PixelLoginPlugin extends JavaPlugin {

    final String prefix = "PixelLoginPlugin";

    LoginHandler loginHandler = null;

    static HttpActionHandler actionHandler;

    static PixelLoginPlugin instance;

    public static PixelLoginPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reload();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equals("plp")) {
//            reload();
        }
        return true;
    }

    public void reload() {
        getLogger().info("================" + prefix + "================");
        getLogger().info("正在加载附属" + prefix);
        reloadConfig();
        String type = getConfig().getString("type");
        if (StrUtil.equals("auto", type)) {
            if (Bukkit.getPluginManager().getPlugin("AuthMe") != null) {
                loginHandler = new AuthMeLoginHandler(Bukkit.getPluginManager().getPlugin("AuthMe"));
                getLogger().info("加载AuthMe前置成功");
            }
        }
        if (loginHandler == null) {
            getLogger().info("登录前置加载失败，当前仅支持AuthMe登录方式！");
        } else {
            actionHandler = new HttpActionHandler(loginHandler);
        }
        getLogger().info("================" + prefix + "================");
    }
}
