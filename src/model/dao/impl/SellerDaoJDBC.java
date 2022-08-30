package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT seller.*, department.Name AS DepName "
					+ "FROM seller INNER JOIN department " + "ON seller.DepartmentId = Department.Id " + "WHERE seller.Id = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
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
			Database.closeConnetion();
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
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
