package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.data.validation.*;

import com.avaje.ebean.Model;

import constants.ModeConst;

@Entity
@Table(name = "service")
public class Service extends Model {
	
	@Constraints.Required(message = "required.message")
	@Id
	public String code;
	
	@Constraints.Required(message = "required.message")
	@Constraints.MaxLength(value = 50, message = "length.message")
	@Constraints.MinLength(value = 3, message = "length.message")
	public String description;
	
	@Constraints.Required(message = "required.message")
	public double amount;
	
	@Transient
	public String mode;
	
	private static Model.Finder<String, Service> find = new Model.Finder<String, Service>(Service.class);
	
	public static List<Service> findAll(){
		return Service.find.orderBy("code").findList();
	}

	public static Service retrieve(String code) {
		return find.ref(code);
	}

	public static boolean exists(String code) {
		return (find.where().eq("code", code).findRowCount() == 1) ? true : false;
	}
	
	public List<ValidationError> validate(){
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (exists(code)){
			if(ModeConst.ADD.equals(mode)){
				errors.add(new ValidationError("code", "Duplicate code"));
			}
		}
		
		return errors.isEmpty() ? null : errors;
	}
	
}
