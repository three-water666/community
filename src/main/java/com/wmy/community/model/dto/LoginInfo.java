package com.wmy.community.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/15 20:00
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginInfo {
    String username;
    String password;
    String kaptcha;
}
