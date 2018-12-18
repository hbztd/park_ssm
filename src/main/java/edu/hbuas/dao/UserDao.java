package edu.hbuas.dao;

import edu.hbuas.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String account);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String account);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectLogin(@Param("username")String username, @Param("password") String password);

    int checkAccount(@Param("account") String account);

    String getPwd(String username);

    List<String> selectAllPermissionByAccount(String account);
}