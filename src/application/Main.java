package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class Main {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		System.out.println(obj);
		
		Seller obj2 = new Seller(21, "Bob", "Bob@gmail.com", new Date(), 3000.0, obj);
		System.out.println(obj2);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();

	}

}
