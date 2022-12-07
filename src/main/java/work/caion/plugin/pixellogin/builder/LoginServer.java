package work.caion.plugin.pixellogin.builder;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.SimpleServer;

public class LoginServer {

    private SimpleServer httpServer;


    protected LoginServer(SimpleServer simpleServer) {
        httpServer = simpleServer;
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.getRawServer().stop(10);
    }
}
