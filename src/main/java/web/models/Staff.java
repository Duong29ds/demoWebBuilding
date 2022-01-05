package web.models;

import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@Entity
@Table(name="employee")
public class Staff {
	@Id
	private int id;
	@NonNull
	private String name;
	@NonNull
	private String idcard;
	@NonNull
	private Date dob;
	@NonNull
	private String phone;
	@NonNull
	private int companyid;
//	@NonNull 
//	@ManyToOne(targetEntity = Company.class)
//	private Company company;
}
