package work.caion.plugin.pixellogin.action;

import work.caion.plugin.pixelcore.PixelCorePlugin;
import work.caion.plugin.pixellogin.login.LoginHandler;

public class ActionHandler {

    LoginHandler loginHandler;

    public ActionHandler(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
        PixelCorePlugin.getActionHandler().registerAction(new LoginAction(loginHandler));
    }

}
