package model.dao.daoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DbConnection;
import DB.exceptions.DbException;
import model.dao.CharacterDao;
import model.entities.Character;
import model.entities.User;

public class CharacterDaoJDBC implements CharacterDao {
	private Connection conn;
	
	public CharacterDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Character findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Character character = null;
		
		try {
			st = conn.prepareStatement("SELECT ch.*, us.id_user, us.name_user, us.password_user, us.email_user FROM tb_character as ch"
					+ " INNER JOIN tb_user as us ON ch.user_id = us.id_user WHERE id_character = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if(rs.next()) {
				User user = instantiateUser(rs);
				
				character = instantiateCharacter(rs, user);
				
				return character;
			}
			
			return null;
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DbConnection.closeStatement(st);
			DbConnection.closeResultSet(rs);
		}
	}
	
	@Override
	public void insert(Character character) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public List<Character> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Character character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findByUser(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Character instantiateCharacter(ResultSet rs, User user) throws SQLException {
		Character character = new Character();
		
		character.setId(rs.getInt("id_character"));
		character.setUser(user);
		character.setArchetype(rs.getString("archetype_character"));
		character.setName(rs.getString("name_character"));
		character.setAttack(rs.getInt("attack_character"));
		character.setDefense(rs.getInt("defense_character"));
		character.setRanking_type(rs.getString("ranking_type"));
		character.setImg_perfil(rs.getString("img_character"));
		
		
		return character;
	}
	
	private User instantiateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id_user"));
		user.setUsername(rs.getString("name_user"));
		user.setPassword(rs.getString("password_user"));
		user.setEmail(rs.getString("email_user"));
		
		return user;
	}
}
