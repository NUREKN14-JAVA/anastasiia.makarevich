package com.anamakarevich.usermanagement.web;


import java.util.Properties;

import org.junit.After;
import org.junit.Before;

import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.MockDaoFactory;
import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
	
	private Mock mockUserDao;

	public Mock getMockUserDao() {
		return mockUserDao;
	}

	public void setMockUserDao(Mock mockUserDao) {
		this.mockUserDao = mockUserDao;
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Properties properties = new Properties();
		properties.setProperty("dao.factory", MockDaoFactory.class.getName());
		DaoFactory.init(properties);
		setMockUserDao(((MockDaoFactory)DaoFactory.getInstance()).getMockUserDao());
	}

	@After
	public void tearDown() throws Exception {
		getMockUserDao().verify();
		super.tearDown();
	}

}
