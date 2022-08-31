package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("=====TESTE FIND BY ID=====");
		Seller seller = sellerDao.findById(1);
		System.out.println(seller);

		System.out.println();
		System.out.println("=====TESTE FIND BY DEPARTMENT=====");
		List<Seller> obj2 = sellerDao.findByDepartment(new Department(1, null));

		for (Seller s : obj2) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("=====TESTE FIND ALL=====");
		List<Seller> obj3 = sellerDao.findAll();

		for (Seller s : obj3) {
			System.out.println(s);
		}
		
		
		System.out.println();
		System.out.println("=====TESTE UPDATE=====");
		seller = sellerDao.findById(1);
		seller.setBaseSalary(5000.0);
		sellerDao.update(seller);
		System.out.println("Update completed");
		
		
		System.out.println();
		System.out.println("=====TESTE INSERT=====");
		Seller usuario1 = new Seller(null, "Felipe Sandes", "felipe@gmail.com", new Date(), 7000.0, new Department(1, null));
		sellerDao.insert(usuario1);
		System.out.println("Usuário Inserido! Id: " + usuario1.getId());
		
		
		
		System.out.println();
		System.out.println("=====TESTE DELETE=====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed!");
		
		
		
		
		sc.close();
		
	}

}
