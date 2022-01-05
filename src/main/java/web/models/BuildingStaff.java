package web.models;
import java.util.ArrayList;
import java.sql.Date;

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
@Table(name="buildingstaff")
public class BuildingStaff {
	@Id
	private int id;
	@Nullable
	private String name;
	@Nullable
	private Date dob;
	@Nullable
	private String address;
	@Nullable
	private String phone;
	@Nullable
	private String position;
	@Nullable
	private String level;
	@NonNull
	private int buildingid;
//	@ManyToOne(targetEntity=Building.class)
//	@JoinColumn(name="id")
//	private int buildingid;
}
