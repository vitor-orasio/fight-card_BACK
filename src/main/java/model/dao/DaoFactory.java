package model.dao;

import DB.DbConnection;
import model.dao.daoJDBC.CharacterDaoJDBC;
import model.dao.daoJDBC.UserDaoJDBC;

public class DaoFactory {

    public static UserDao createUserDao() {
        return new UserDaoJDBC(DbConnection.getConnection());
    }
    
    public static CharacterDao createCharacterDao() {
    	return new CharacterDaoJDBC(DbConnection.getConnection());
    }
}
