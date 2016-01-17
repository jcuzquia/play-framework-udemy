package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

@Entity
public class Service extends Model {
	
	@Id
	public String code;
	public String description;
	
}
