package com.wmy.community.dao;


import com.wmy.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/23 0:18
 */
@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
