package web.models;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="company")
public class Company {
	@Id
	private int ID;
	@NonNull
	private String name;
	@NonNull
	private String taxcode;
	@NonNull
	private float fund;
	@NonNull
	private String field;
	@Nullable
	private int numofem;
	@Nullable
	private String address;
	@Nullable
	private String phone;
	@Nullable
	private float square;

}
