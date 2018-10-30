package com.aboo.vbbs.test.serv;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aboo.vbbs.TestApplication;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class })
public class UserServiceTest {
	
	
	@Autowired
	private UserService userService;
	
	//@Test
	public void testInsertUser() {
		User user = new User();
		boolean result  = userService.insert(user);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testSelectUser() {
		User user  = userService.selectById(1L);
		System.out.println();
	}
	
	

}
