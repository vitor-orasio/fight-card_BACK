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
	public void insert(Character character) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO tb_character(user_id, archetype_character, name_character, attack_character, defense_character, ranking_type, img_character)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, character.getUser().getId());
			st.setString(2, character.getArchetype());
			st.setString(3, character.getName());
			st.setInt(4, character.getAttack());
			st.setInt(5, character.getDefense());
			st.setString(6, character.getRanking_type());
			st.setString(7, character.getImg_perfil());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);
					character.setId(id);
				}
				
				DbConnection.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DbConnection.closeStatement(st);
		}
	}
	
	@Override
	public List<Character> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM tb_character");
			
			rs = st.executeQuery();
			List<Character> listCharacter = new ArrayList<Character>();
			
			while(rs.next()) {
//				Character character = instantiateCharacter(rs, user);
//				listCharacter.add(character);
			}
			
			return listCharacter;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DbConnection.closeStatement(st);
			DbConnection.closeResultSet(rs);
		}
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
}
