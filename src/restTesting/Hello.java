package restTesting;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

@Path("/hello")
public class Hello {
	Configuration cfg;
	SessionFactory factory;
	Session session;
	Transaction t;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}

	@GET
	@Path("/xml")
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey" + "</h1>"
				+ "<br/>Services Exposed<br/>"
				+ "add[post-name]<br/>"
				+ "<a href='hello/get'>get</a><br/>"
				+ "<a href='hello/image'>image</a><br/>"
				+ "<a href='hello/xmldata/12'>xmldata/pin</a>"
				+ "</body>"
				+ "</html> ";
	}

	@POST
	@Path("/add")
	public Response addUser(@FormParam("name") String name){
		return Response.status(200)
				.entity("addUser is called, name : " + name).build();
	}
	
	@GET
	@Path("/get")
	public Response addUser(@Context HttpHeaders headers) {

		String userAgent = headers.getRequestHeader("host").get(0);
		
		/*
		 * Header contains 
		 * host 
		 * connection 
		 * postman-token 
		 * cache-control
		 * user-agent 
		 * content-type 
		 * accept 
		 * accept-encoding 
		 * accept-language
		 * for(String header : headers.getRequestHeaders().keySet()){
		 * System.out.println(header); }
		 */
		
		return Response.status(200)
			.entity("addUser is called, userAgent : " + userAgent)
			.build();

	}
	
	@GET
	@Path("/image")
	@Produces("image/png")
	public Response getFile() {

		File file = new File("D:\\ic_launcher.png");

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=image_from_server.png");
		return response.build();

	}
	
	@GET
	@Path("xmldata/{pin}")
	@Produces(MediaType.APPLICATION_XML)
	public XmlBuilderTest getCustomerInXML(@PathParam("pin") int pin) {

		XmlBuilderTest customer = new XmlBuilderTest();
		customer.setName("Sourabh");
		customer.setPin(pin);

		return customer;
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTrackInJSON() {

	
		
		List<JSONBuilderTest> list = new ArrayList<JSONBuilderTest>();
        for (int i = 0; i < 20; i++) {
                list.add(new JSONBuilderTest(i, "Test1", "Test2", JSONBuilderTest.Status.ASSIGNED, 10));
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<JSONBuilderTest>>() {}.getType();
        String json = gson.toJson(list, type);
		return json;
	}
	
	@GET
	@Path("/jsonShow")
	@Produces(MediaType.APPLICATION_JSON)
	public String dbShow() {
		cfg=new Configuration();  
	    cfg.configure("hibernate.cfg.xml");  
	    factory=cfg.buildSessionFactory();
	    session=factory.openSession(); 
	    t=session.beginTransaction();
		Query query = session.createQuery("FROM DBPojo");
        List list = query.list();
        for(int iter=0;iter<list.size();iter++){
        	DBPojo pojo = new DBPojo();
        	pojo = (DBPojo) list.get(iter);
        	System.out.println("Name "+pojo.getName());
        }
        System.out.println(list);
        Gson gson = new Gson();
        String json = gson.toJson(list);
	    t.commit();
	    session.close();  
	    return json;
		
	}
	
	
	
	@POST
	@Path("/jsonEntry")
	@Produces(MediaType.APPLICATION_JSON)
	public String dbEntry(@FormParam("address") String address,@FormParam("amount") String amount,@FormParam("IFSC") String IFSC,@FormParam("name") String name,@FormParam("no") String no,@FormParam("type") String type) {
		try{
			cfg=new Configuration();  
			cfg.configure("hibernate.cfg.xml");  
		    factory=cfg.buildSessionFactory();
		    session=factory.openSession(); 
		    t=session.beginTransaction();
			DBPojo e1=new DBPojo();  
		    e1.setAddress(address);
		    e1.setAmount(Integer.parseInt(amount));
		    e1.setIfsccode(Integer.parseInt(IFSC));
		    e1.setName(name);
		    e1.setNo(Integer.parseInt(no));
		    e1.setType(type);   
		    session.persist(e1);
		    t.commit();//transaction is committed  
		    session.close();
		}catch(Exception ex){
			return "Error:"+ex.getMessage();
		}
	    
	    return "Entry Created for "+name;
		
	}
	
	
	
	
	
	
	
	
	
}
