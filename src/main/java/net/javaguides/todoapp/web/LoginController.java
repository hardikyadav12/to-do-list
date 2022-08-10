package net.javaguides.todoapp.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.javaguides.todoapp.dao.LoginDao;
import net.javaguides.todoapp.model.LoginBean;

@WebServlet("/login")
public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	
	public void init() {
		loginDao = new LoginDao();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login/login.jsp");
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authenticate(request, response);
	}
	
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName(request.getParameter("username"));
		loginBean.setPassword(request.getParameter("password"));
		
		try {
			if(loginDao.validate(loginBean)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
				dispatcher.forward(request, response);
			}else {
				HttpSession session = request.getSession();
				// session.setAttribute("user", username);
//                 response.sendRedirect("login/login.jsp");
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
