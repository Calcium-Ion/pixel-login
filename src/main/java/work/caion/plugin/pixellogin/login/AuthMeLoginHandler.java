package work.caion.plugin.pixellogin.login;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.plugin.Plugin;

public class AuthMeLoginHandler implements LoginHandler {

    AuthMe authMe;

    public AuthMeLoginHandler(Plugin authMe) {
        this.authMe = (AuthMe) authMe;
    }

    @Override
    public boolean login(String username, String password) {
        return AuthMeApi.getInstance().checkPassword(username, password);
    }
}
