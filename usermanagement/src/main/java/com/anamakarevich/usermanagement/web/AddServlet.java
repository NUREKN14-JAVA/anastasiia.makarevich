package com.anamakarevich.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.DatabaseException;

public class AddServlet extends EditServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }
    
    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }

}
