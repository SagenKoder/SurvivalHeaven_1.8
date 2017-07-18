package info.nordbyen.survivalheaven.subplugins.remote;

public class User
{
    private final String username;
    private final String password;
    
    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getUsername() {
        return this.username;
    }
}
