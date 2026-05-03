package model.dao;

import model.entities.Character;
import model.entities.User;

import java.util.List;

public interface CharacterDao {
    void insert(Character character);
    void update(Character character);
    void deleteById(Integer id);
    Character findById(Integer id);
    List<Character> findAll();

    User findByUser(Integer id);
}
