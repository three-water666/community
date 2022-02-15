package com.wmy.community.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description:注册信息
 * @Author: 三水
 * @Date: 2022/2/14 19:59
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegisterInfo {
    private String username;
    private String password;
    private String email;
}
