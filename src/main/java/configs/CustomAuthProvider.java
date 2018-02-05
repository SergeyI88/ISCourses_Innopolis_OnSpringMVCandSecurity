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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import utils.MyPasswordEncoder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
        System.out.println(authentication.getPrincipal().toString());
        try {
            User pass = userDao.getLoginAndPassAndId(name);
            if (pass.getPassword() == null || pass.getLogin() == null) {
                return null;
            }
            String[] roles = pass.getRole();
            ArrayList<GrantedAuthority> listA = new ArrayList<>();
            for (int i = 0; i < roles.length; i++) {
                listA.add(new SimpleGrantedAuthority(roles[i]));
                System.out.println(roles[i]);
            }
            ArrayList list = new ArrayList(listA);

            if (myPasswordEncoder.matches(password, pass.getPassword())) {
                User user = userDao.getLoginAndPassAndId(name);
                return new UsernamePasswordAuthenticationToken(user, pass, list);
            }
        } catch (UserDaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
