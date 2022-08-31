package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.Database;
import database.DatabaseException;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				rs = ps.getGeneratedKeys();
				System.out.println("Dados Inseridos com sucesso!");
				
				if(rs.next()) {
					seller.setId(rs.getInt(1));
				}
			}
			
			else {
				throw new DatabaseException("Unexpected error! No rows affected!");
			}
			
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}

	}

	@Override
	public void update(Seller seller) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			ps.setInt(6, seller.getId());
			
			ps.executeUpdate();
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT seller.*, department.Name AS DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = Department.Id " + "WHERE seller.Id = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);

				return seller;
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
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT seller.*, department.Name AS DepName FROM seller "
					+ "INNER JOIN department ON seller.departmentId = department.Id");

			rs = ps.executeQuery();

			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("departmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}

				sellers.add(instantiateSeller(rs, dep));
			}

			return sellers;

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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT seller.*, department.Name AS DepName FROM seller "
					+ "INNER JOIN department ON seller.departmentId = department.Id " + "WHERE seller.departmentId = ? "
					+ "ORDER BY Name");

			ps.setInt(1, department.getId());

			rs = ps.executeQuery();

			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("departmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}

				sellers.add(instantiateSeller(rs, dep));
			}

			return sellers;

		}

		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}

		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}

	// metodos auxiliares
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();

		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));

		return dep;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {

		Seller seller = new Seller();

		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);

		return seller;
	}

}
