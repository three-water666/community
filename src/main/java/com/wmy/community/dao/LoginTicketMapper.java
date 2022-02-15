package com.wmy.community.dao;

import com.wmy.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @Description: 登录凭证
 * @Author: 三水
 * @Date: 2022/2/15 19:16
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired",
            "from login_ticket where ticket=${ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket}"
    })
    int updateStatus(String ticket,int status);
}
