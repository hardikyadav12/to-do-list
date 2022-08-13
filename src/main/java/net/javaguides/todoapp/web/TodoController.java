package net.javaguides.todoapp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javaguides.todoapp.dao.TodoDao;
import net.javaguides.todoapp.dao.TodoDaoImpl;
import net.javaguides.todoapp.model.Todo;
import net.javaguides.todoapp.utils.JDBCUtils;

@WebServlet("/")
public class TodoController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private TodoDao todoDAO;
	
	public void init() {
		todoDAO = new TodoDaoImpl();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
			switch(action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertTodo(request, response);
				break;
			case "/delete":
				deleteTodo(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateTodo(request, response);
				break;
			case "/list":
				listTodo(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login/login.jsp");
				dispatcher.forward(request, response);
				break;
			}
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<Todo> listTodo = todoDAO.selectAllTodos();
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/todo/todo-list.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/todo/todo-form.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
	
}
