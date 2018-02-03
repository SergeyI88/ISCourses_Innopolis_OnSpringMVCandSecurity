package configs;

import db.dao.UserDao;
import db.dao.UserDataDao;
import db.dao.exceptions.UserDaoException;
import db.pojo.User;
import db.pojo.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import utils.MyPasswordEncoder;

import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
    @Autowired
    UserDao userDao;
    @Autowired
    MyPasswordEncoder myPasswordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            ArrayList list = new ArrayList();
            User pass = userDao.getLoginAndPassAndId(name);
            if (pass.getPassword() == null || pass.getLogin() == null) {
                return null;
            }
            list.add(new SimpleGrantedAuthority("role_user"));
            if (myPasswordEncoder.matches(password, pass.getPassword())) {
                return new UsernamePasswordAuthenticationToken(name, pass, list);
            }
        } catch (UserDaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
