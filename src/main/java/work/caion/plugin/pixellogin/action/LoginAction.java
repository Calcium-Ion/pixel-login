package work.caion.plugin.pixellogin.action;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import work.caion.plugin.pixelcore.action.CoreAction;
import work.caion.plugin.pixelcore.result.Result;
import work.caion.plugin.pixelcore.result.ResultUtil;
import work.caion.plugin.pixellogin.PixelLoginPlugin;
import work.caion.plugin.pixellogin.login.LoginHandler;

public class LoginAction extends CoreAction {

    LoginHandler loginHandler;

    public LoginAction(LoginHandler loginHandler) {
        super("login");
        this.loginHandler = loginHandler;
    }

    @Override
    public Result execute(Result result) {
        JSONObject json = result.getData();
        String username = json.getStr("username");
        String password = Base64Decoder.decodeStr(json.getStr("password"));
        PixelLoginPlugin.getInstance().getLogger().info("用户[" + username + "]尝试从网页登录");
        if (loginHandler.login(username, password)) {
            return ResultUtil.success("登录成功");
        } else {
            return ResultUtil.error("登录失败");
        }
    }
}
