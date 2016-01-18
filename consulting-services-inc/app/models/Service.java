package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.*;

import com.avaje.ebean.Model;

@Entity
public class Service extends Model {
	
	@Id
	public String code;
	
	@Constraints.Required
	public String description;
	
	@Constraints.Required
	public double amount;
	
	private static Model.Finder<String, Service> find = new Model.Finder<>(Service.class);
	
	public static List<Service> findAll(){
		return Service.find.orderBy("code").findList();
	}

	public static Service retrieve(String code) {
		return find.ref(code);
	}

	public static boolean exists(String code) {
		return (find.where().eq("code", code).findRowCount() == 1) ? true : false;
	}
	
}
