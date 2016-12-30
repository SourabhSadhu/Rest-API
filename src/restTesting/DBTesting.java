package restTesting;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;  

public class DBTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration cfg=new Configuration();  
	    cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file  
	      
	    //creating session factory object  
	    SessionFactory factory=cfg.buildSessionFactory();  
	      
	    //creating session object  
	    Session session=factory.openSession();  
	      
	    //creating transaction object  
	    Transaction t=session.beginTransaction();  
	          
	    DBPojo e1=new DBPojo();  
	    //e1.setAccountnumber(3);
	    e1.setAddress("Bardhaman");
	    e1.setAmount(12000);
	    e1.setIfsccode(1234);
	    e1.setName("Amit");
	    e1.setNo(9999);
	    e1.setType("recurring");
	      
	    session.persist(e1);//persisting the object  
	    //t.commit();
	    //t.begin();
	    Query query = session.createQuery("FROM DBPojo");
        List list = query.list();
        for(int iter=0;iter<list.size();iter++){
        	DBPojo pojo = new DBPojo();
        	pojo = (DBPojo) list.get(iter);
        	System.out.println("Name "+pojo.getName());
        }
        System.out.println(list);
        
	    t.commit();//transaction is committed  
	    session.close();  
	      
	    System.out.println("successfully completed");  
	      
	}  
}
