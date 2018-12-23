package edu.hbuas.dao;

import edu.hbuas.pojo.UPermission;

import java.util.List;

public interface UPermissionDao {
    int insert(UPermission record);

    int insertSelective(UPermission record);

    List<UPermission> selectAllPermissionByAccount(String account);

    String selectPermissionName(int pid);

    String selectPermissionUrl(int pid);

    int permissionIsExist(UPermission uPermission);

    int removeUserPermission(UPermission uPermission);

    List<Integer> selectAllPermissionByDefault();
}