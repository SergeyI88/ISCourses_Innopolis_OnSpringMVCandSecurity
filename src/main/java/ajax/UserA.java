package ajax;

public class UserA {
    private int id;
    private String login;
    private String password;
    private String oldPwd;
    private String oldPwd2;
    private String name;
    private String last_name;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getOldPwd2() {
        return oldPwd2;
    }

    public void setOldPwd2(String oldPwd2) {
        this.oldPwd2 = oldPwd2;
    }

    public UserA() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
