package configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Collection;

public class UserInside extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private int user_id;
    @Autowired
    private UserInside userInside;


    public UserInside(String username, String password, Collection<? extends GrantedAuthority> authorities, int user_id) {
        super(username, password, authorities);
        this.user_id = user_id;
    }

    public UserInside getUserInside() {
        return userInside;
    }

    public void setUserInside(UserInside userInside) {
        this.userInside = userInside;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
