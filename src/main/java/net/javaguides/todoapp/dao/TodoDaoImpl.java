package net.javaguides.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.todoapp.model.Todo;
import net.javaguides.todoapp.utils.JDBCUtils;

public class TodoDaoImpl implements TodoDao{
	
	private static final String INSERT_TODOS_SQL = "INSERT INTO todos" + 
	"(title, username, description, target_date, is_done) VALUES " + "(?,?,?,?,?) "  ;
	private static final String SELECT_TODO_BY_ID = "select id, title, username, description, target_date, is_done from todos where id =?";
	private static final String SELECT_ALL_TODOS = "select * from todos";
	private static final String DELETE_TODO_BY_ID = "delete form todos where id =?";
	private static final String UPDATE_TODO = "update todos set title=?, username=?, description=?, target_date=?, is_done=? where id=?";
	
	public TodoDaoImpl() {};

	@Override
	public void insertTodo(Todo todo) throws SQLException {
		try(Connection connection = JDBCUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODOS_SQL)) {
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getUsername());
			preparedStatement.setString(3, todo.getDescription());
			preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
			preparedStatement.setBoolean(5, todo.getStatus());
			
			preparedStatement.executeUpdate();
		}catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	@Override
	public Todo selectTodo(long todoId) {
		Todo todo = null;
		try(Connection connection = JDBCUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TODO_BY_ID)) {
			preparedStatement.setLong(1, todoId);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todo = new Todo(id, title, username, description, targetDate, isDone);
				
			}
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return todo;
	}

	@Override
	public List<Todo> selectAllTodos() {
		List<Todo> todos = new ArrayList<Todo>();
		
		try(Connection connection = JDBCUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TODOS)) {
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todos.add(new Todo(id, title, username, description, targetDate, isDone));
			}
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return todos;
	}

	@Override
	public boolean deleteTodo(int id) throws SQLException {
		boolean rowDeleted = false;
		
		try(Connection connection  = JDBCUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TODO_BY_ID)) {
			preparedStatement.setLong(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
			
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		
		return rowDeleted;
	}

	@Override
	public boolean updateTodo(Todo todo) throws SQLException {
		boolean rowUpdated = false;
		
		try(Connection connection = JDBCUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TODO)) {
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getUsername());
			preparedStatement.setString(3, todo.getDescription());
			preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
			preparedStatement.setBoolean(5, todo.getStatus());
			preparedStatement.setLong(6, todo.getId());
			
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return rowUpdated;
	}

}
