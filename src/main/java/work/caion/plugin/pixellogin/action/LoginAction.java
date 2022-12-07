package work.caion.plugin.pixellogin.action;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.action.Action;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import work.caion.plugin.pixelcore.result.ResultUtil;
import work.caion.plugin.pixelcore.server.ServerAction;
import work.caion.plugin.pixellogin.PixelLoginPlugin;
import work.caion.plugin.pixellogin.login.LoginHandler;

import java.io.IOException;

public class LoginAction extends ServerAction {

    LoginHandler loginHandler;

    public LoginAction(LoginHandler loginHandler, String path) {
        super(path);
        this.loginHandler = loginHandler;
    }

    @Override
    public Action getAction() {
        return (httpServerRequest, httpServerResponse) -> {
            String body = httpServerRequest.getBody();
            JSONObject json = JSONUtil.parseObj(body);
            String username = json.getStr("username");
            String password = Base64Decoder.decodeStr(json.getStr("password"));
            PixelLoginPlugin.getInstance().getLogger().info("用户[" + username + "]尝试从网页登录");
            if (loginHandler.login(username, password)) {
                httpServerResponse.write(ResultUtil.ok("登录成功").toString());
            } else {
                httpServerResponse.write(ResultUtil.error("登录失败").toString());
            }
        };
    }
}
