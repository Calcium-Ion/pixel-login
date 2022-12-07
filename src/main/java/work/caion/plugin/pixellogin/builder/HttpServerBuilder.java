package work.caion.plugin.pixellogin.builder;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.SimpleServer;
import cn.hutool.http.server.action.Action;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpServerBuilder {


    private int port = 8081;
    private String token;
    private HashMap<String, Action> actions = new HashMap<>();

    public HttpServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public HttpServerBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public HttpServerBuilder addPath(String path, Action action) {
        actions.put(path, action);
        return this;
    }

    public LoginServer build() {
        SimpleServer httpServer = HttpUtil.createServer(port);
        httpServer.addFilter((httpServerRequest, httpServerResponse, chain) -> {
            String token = httpServerRequest.getHeader("p-token", StandardCharsets.UTF_8);
            if (!StrUtil.equals(token, SecureUtil.md5(SecureUtil.md5(this.token) + this.token))) {
                httpServerResponse.setHeader("Content-Type", "application/json; charset=utf-8");
                httpServerResponse.write("{\"code\":901, \"msg\":\"您还没有登录\", \"data\":null}");
            }
        });
        return new LoginServer(httpServer);
    }
}
