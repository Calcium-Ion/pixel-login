package work.caion.plugin.pixellogin.login;

public interface LoginHandler {

    boolean login(String username, String password);

    boolean hasUser(String username);

    void register(String username, String password);
}
