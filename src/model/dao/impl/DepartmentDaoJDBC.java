package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.DatabaseException;
import model.dao.DepartmentDao;
import model.entites.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}

			return null;

		}

		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}

		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM department");

			rs = ps.executeQuery();

			List<Department> deps = new ArrayList<>();

			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				deps.add(dep);
			}

			return deps;
		}

		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}

		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}

	@Override
	public void insert(Department department) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, department.getName());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				ResultSet rs = ps.getGeneratedKeys();

				if (rs.next()) {
					department.setId(rs.getInt(1));
				}
			}

			else {
				throw new DatabaseException("Unxpected Error! No rows affected!");
			}
		}

		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}

		finally {
			Database.closeStatement(ps);
		}

	}

	@Override
	public void update(Department department) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");

			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());

			ps.executeUpdate();
		}

		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}

		finally {
			Database.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			ps.setInt(1, id);

			ps.executeUpdate();
		}

		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}

		finally {
			Database.closeStatement(ps);
		}

	}

	// metodos auxiliares
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();

		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));

		return dep;
	}

}
