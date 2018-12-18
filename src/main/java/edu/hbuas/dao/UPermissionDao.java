package edu.hbuas.dao;

import edu.hbuas.pojo.UPermission;

import java.util.List;

public interface UPermissionDao {
    int insert(UPermission record);

    int insertSelective(UPermission record);

    List<String> selectAllPermissionByAccount(String account);
}