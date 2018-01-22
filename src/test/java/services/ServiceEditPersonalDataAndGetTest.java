package services;

import db.connection.DbConnection;
import db.dao.UserDao;
import db.dao.UserDaoImpl;
import db.dao.UserDataDao;
import db.dao.UserDataDaoImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

public class ServiceEditPersonalDataAndGetTest {
    DbConnection dbConnection;
    @Mock
    UserDao userDao;
    @Mock
    UserDataDao userDataDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Test
    public void test() throws Exception {


        Mockito.when(userDao.editPassword(40, "passw")).thenReturn(1);
        Mockito.when(userDataDao.getNameUserDataFromIdUser(40)).thenReturn("name");
        Mockito.when(userDataDao.getLastNameUserDataFromIdUser(40)).thenReturn("last_name");
        assertEquals(1, userDao.editPassword(40, "passw"));
        assertEquals("name", userDataDao.getNameUserDataFromIdUser(40));
        assertEquals("last_name", userDataDao.getLastNameUserDataFromIdUser(40));
        Mockito.verify(userDao).editPassword(40, "passw");
        Mockito.verify(userDataDao).getLastNameUserDataFromIdUser(40);
        Mockito.verify(userDataDao).getNameUserDataFromIdUser(40);
    }
}