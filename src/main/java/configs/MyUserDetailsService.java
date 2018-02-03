package configs;

import db.dao.UserDao;
import db.dao.UserDataDao;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;
import db.pojo.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    UserInside userInside;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInside userInside1 = null;
        try {
            User user = userDao.getLoginAndPassAndId(username);
            GrantedAuthority[] grantedAuthorities = new GrantedAuthority[1];
            grantedAuthorities[0] = new SimpleGrantedAuthority(user.getRole()[0]);
            userInside = new UserInside(user.getLogin(), user.getPassword(),
                    Arrays.asList(grantedAuthorities), user.getId());
        } catch (UserDaoException e) {
            e.printStackTrace();
        }
        userInside = userInside1;
        return userInside1;
    }

    public UserInside getUserInside() {
        return userInside;
    }

    public void setUserInside(UserInside userInside) {
        this.userInside = userInside;
    }
}
