package work.caion.plugin.pixellogin.action;

import work.caion.plugin.pixelcore.server.HttpServerHandler;
import work.caion.plugin.pixellogin.login.LoginHandler;

public class HttpActionHandler {

    LoginHandler loginHandler;

    public HttpActionHandler(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
        HttpServerHandler.addAction(new LoginAction(loginHandler, "/login"));
    }
}
