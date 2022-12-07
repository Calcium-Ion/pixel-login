package work.caion.plugin.pixellogin.builder;

public class HttpServerHandler {

    private static LoginServer loginServer;

    public static LoginServer getServer() {
        return loginServer;
    }

    public static void shutdown() {
        if (loginServer != null) {
            loginServer.stop();
        }
    }

    public static HttpServerBuilder builder() {
        return new HttpServerBuilder();
    }

    public static void start(LoginServer server) {
        loginServer = server;
        server.start();
    }
}
