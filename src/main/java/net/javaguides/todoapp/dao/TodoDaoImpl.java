package net.javaguides.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.javaguides.todoapp.model.Todo;
import net.javaguides.todoapp.utils.JDBCUtils;

public class TodoDaoImpl implements TodoDao{
	
	private static final String INSERT_TODOS_SQL = "INSERT INTO todos" + 
	"(title, username, description, target_date, is_done) VALUES " + "(?,?,?,?,?) "  ;
	private static final String SELECT_TODO_BY_ID = "select id, title, username, description, target_date, is_done from todos where id =?";
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
				
			}
		}
		return null;
	}

	@Override
	public List<Todo> selectAllTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTodo(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTodo(Todo todo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
