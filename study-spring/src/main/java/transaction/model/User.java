package transaction.model;

/**
 * Created by zouziwen on 2017/3/30.
 */
public class User {
    private String username;

    private String password;

    private From from;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
        from.setUsername(username);
    }
}
