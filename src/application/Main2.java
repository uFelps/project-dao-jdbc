package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entites.Department;

public class Main2 {

	public static void main(String[] args) {
		
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=====TESTE FIND BY ID=====");
		Department dep = departmentDao.findById(1);
		System.out.println(dep);
		
		
		System.out.println();
		System.out.println("=====TESTE FIND ALL=====");
		List<Department> deps = departmentDao.findAll();
		
		for(Department obj : deps) {
			System.out.println(obj);
		}
		
		System.out.println();
		System.out.println("=====TESTE INSERT=====");
		Department obj2 = new Department(null, "Devlopers");
		departmentDao.insert(obj2);
		System.out.println("Usuário Inserido! Id: "+ obj2.getId());
		
		System.out.println();
		System.out.println("=====TESTE UPDATE=====");
		Department obj3 = departmentDao.findById(1);
		obj3.setName("Technology");
		departmentDao.update(obj3);
		
		System.out.println();
		System.out.println("=====TESTE DELETE=====");
		departmentDao.deleteById(1);
		
		
	}

}
