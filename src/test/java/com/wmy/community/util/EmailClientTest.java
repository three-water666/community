package com.wmy.community.util;

import com.wmy.community.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/24 23:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class EmailClientTest {
    @Autowired
    private EmailClient emailClient;

    @Test
    public void testSendEmail(){
        emailClient.sendMail("wmy1933651680@163.com","Test","测试邮件");
    }
}
