package com.anamakarevich.usermanagement.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.DatabaseException;

public class BrowseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
	    // check if any button was clicked, and if it was then 
	    // execute the appropriate operation
	    if (req.getParameter("addButton") != null) {
	        add(req, resp);
	        return;
	    }
	    if (req.getParameter("editButton") != null) {
	        edit(req, resp);
	        return;
	    }
	    if (req.getParameter("deleteButton") != null) {
	        delete(req, resp);
	        return;
	    }
	    if (req.getParameter("detailsButton") != null) {
	        details(req, resp);
	        return;
	    }
		browse(req, resp);
	}

	/**
	 * Redirects to the details page for the chosen user
	 * @param req - request that contains user id
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().length() == 0) {
            req.setAttribute("error","You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req,  resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
            req.getSession().setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error","ERROR:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").forward(req, resp);
        
    }

	/**
	 * Deletes user from database and dipatches the browse page
	 * @param req - request with the user id
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().length() == 0) {
            req.setAttribute("error","You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req,  resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
            DaoFactory.getInstance().getUserDao().delete(user);
            req.getSession().setAttribute("result", "ok");
        }
        catch (Exception e) {
            req.setAttribute("error","ERROR:" + e.toString());            
        }
        browse(req,resp);
    }

    /**
     * Redirects to the edit page for the chosen user
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        // check if the id was passed with the request
        if (idStr == null || idStr.trim().length() == 0) {
            // record error message
            req.setAttribute("error","You must select a user");
            // forward request to the browse page but with the error message
            req.getRequestDispatcher("/browse.jsp").forward(req,  resp);
            return;
        }
        
        try {
            User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
            req.getSession().setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error","ERROR:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    /**
     * Redirects to the add page  
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
        
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Collection<?> users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			req.getSession().setAttribute("users", users);
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		} catch (DatabaseException e) {
			throw new ServletException(e);
		}
		
	}

}
