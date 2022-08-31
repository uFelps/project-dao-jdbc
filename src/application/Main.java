package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class Main {

	public static void main(String[] args) {
		
		SellerDao seller = DaoFactory.createSellerDao();

		System.out.println("=====TESTE FIND BY ID=====");
		Seller obj = seller.findById(1);
		System.out.println(obj);

		System.out.println();
		System.out.println("=====TESTE FIND BY DEPARTMENT=====");
		List<Seller> obj2 = seller.findByDepartment(new Department(1, null));

		for (Seller s : obj2) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("=====TESTE FIND ALL=====");
		List<Seller> obj3 = seller.findAll();

		for (Seller s : obj3) {
			System.out.println(s);
		}
		
		
		System.out.println();
		System.out.println("=====TESTE INSERT=====");
		Seller usuario1 = new Seller(null, "Felipe Sandes", "felipe@gmail.com", new Date(), 7000.0, new Department(1, null));
		
		seller.insert(usuario1);
		System.out.println("Usuário Inserido! Id: " + usuario1.getId());
	}

}
