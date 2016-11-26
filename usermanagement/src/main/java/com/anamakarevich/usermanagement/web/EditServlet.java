package com.anamakarevich.usermanagement.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.DatabaseException;

public class EditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("okButton") != null) {
            doOk(req, resp);
            return;
        }
        if (req.getParameter("cancelButton") != null) {
            doCancel(req, resp);
            return;
        }
        showPage(req,resp);
    }

    /**
     * gets request dispatcher at path /edit.jsp, wraps into RequestDispatcher object and forward the request
     * @param req - original request object
     * @param resp - response object
     * @throws ServletException
     * @throws IOException
     */
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    /**
     * Redirects to the original brows page without making any changes to the database
     * @param req - original request
     * @param resp - response object
     * @throws ServletException
     * @throws IOException
     */
    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        
    }

    /**
     * Extract user from request, processes it and show the browse page again
     * @param req - original request with user data in it
     * @param resp - response object
     * @throws ServletException
     * @throws IOException
     */
    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        try {
            user = getUser(req);
        } catch (ValidationException e1) {
            req.setAttribute("error", e1.getMessage());
            showPage(req,resp);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            new ServletException(e);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    /**
     * Extract user from request
     * @param req - original request
     * @return - selected user
     * @throws ValidationException
     */
    private User getUser(HttpServletRequest req) throws ValidationException {
        User user = new User();
        
        // extract values from request
        String idStr = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateStr = req.getParameter("date");
        
        // check if the user filled in all the field
        if (firstName == null || firstName.isEmpty()) {
            throw new ValidationException("First name is empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new ValidationException("Last name is empty");
        }
        if (dateStr == null || dateStr.isEmpty()) {
            throw new ValidationException("Date is empty");
        }
        
        // check the id was found
        if (idStr != null) {
            user.setId(new Long(idStr));
        }
        
        // set user's fields
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        // try to parse date and if we faile then inform user
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            user.setDateOfBirthd(LocalDate.parse(dateStr,formatter));
        }
        catch (DateTimeParseException e) {
            throw new ValidationException("Date format is incorrect");
        }
        return user;
    }
    
    /**
     * Execute the appropriate database operation
     * @param user - user to be processed
     * @throws DatabaseException
     */
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().update(user);
    }

}
