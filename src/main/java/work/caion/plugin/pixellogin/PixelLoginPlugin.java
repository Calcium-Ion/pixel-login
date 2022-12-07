package work.caion.plugin.pixellogin;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.action.Action;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import work.caion.plugin.pixellogin.builder.HttpServerHandler;
import work.caion.plugin.pixellogin.builder.LoginServer;
import work.caion.plugin.pixellogin.login.AuthMeLoginHandler;
import work.caion.plugin.pixellogin.login.LoginHandler;

import java.io.IOException;

public class PixelLoginPlugin extends JavaPlugin {

    final String prefix = "PixelLoginPlugin";

    LoginHandler loginHandler = null;

    Logger logger = LogManager.getLogger();

    Action loginCheck = (httpServerRequest, httpServerResponse) -> {
        String body = httpServerRequest.getBody();
        JSONObject json = JSONUtil.parseObj(body);
        String username = json.getStr("username");
        String password = Base64Decoder.decodeStr(json.getStr("password"));
        if (loginHandler.login(username, password)) {
            httpServerResponse.setHeader("Content-Type", "application/json; charset=utf-8");
            httpServerResponse.write("{\"code\":200, \"msg\":\"登录成功\", \"data\":null}");
        } else {
            httpServerResponse.setHeader("Content-Type", "application/json; charset=utf-8");
            httpServerResponse.write("{\"code\":901, \"msg\":\"您还没有登录\", \"data\":null}");
        }
    };

    @Override
    public void onEnable() {
        reload();
    }

    @Override
    public void onDisable() {
        logger.info(prefix + "正在卸载插件");
        try {
            logger.info("正在关闭LoginServer");
            HttpServerHandler.shutdown();
            logger.info(prefix + "卸载完成，感谢您的使用");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equals("plp")) {
            if (sender.hasPermission("plp.admin")) {
                if (args[0].equals("reload")) {
                    reload();
                }
            }
        }
        return true;
    }

    public void reload() {
        logger.info(prefix + "正在加载插件");
        reloadConfig();
        try {
            String type = getConfig().getString("type");
            if (StrUtil.equals("auto", type)) {
                if (Bukkit.getPluginManager().getPlugin("AuthMe") != null) {
                    loginHandler = new AuthMeLoginHandler(Bukkit.getPluginManager().getPlugin("AuthMe"));
                    logger.info("加载AuthMe前置成功");
                }
            }
            if (loginHandler == null) {
                logger.error("登录前置加载失败，当前仅支持AuthMe登录方式！");
                throw new Exception("登录前置加载失败，当前仅支持AuthMe登录方式！");
            }
            int port = getConfig().getInt("http.port");
            String token = getConfig().getString("token");
            logger.info("正在关闭LoginServer");
            HttpServerHandler.shutdown();
            LoginServer server = HttpServerHandler.builder().setPort(port).setToken(token).addPath("/login", loginCheck).build();
            logger.info("正在启动LoginServer");
            HttpServerHandler.start(server);
            logger.info(prefix + "启动完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(prefix + "启动失败，服务器即将关闭！！！");
            Bukkit.shutdown();
        }
    }
}
