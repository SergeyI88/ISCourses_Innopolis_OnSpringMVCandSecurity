package services;

import db.dao.*;
import db.pojo.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.opentest4j.AssertionFailedError;

import static org.junit.Assert.*;

public class ServiceRegAndAuthTest {

    @Mock
    UserDao userDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testOnUniqueAndAuth() throws Exception {
        User user = new User();
        user.setPassword("mock_pass");
        user.setLogin("mock_login");
        Mockito.when(userDao.checkOnUniqueLogin("login")).thenReturn(false);
        Mockito.when(userDao.auth("login", "pass")).thenReturn(40);
        Assert.assertFalse( userDao.checkOnUniqueLogin("login"));
        Assert.assertEquals( 40, userDao.auth("login", "pass"));
        Mockito.verify(userDao).checkOnUniqueLogin("login");
        Mockito.verify(userDao).auth("login", "pass");

    }
}