package web.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@Entity
@Table(name="usedservice")
public class ServiceUsed {
	@Id
	private int id;
	@NonNull
	private int companyid;
	@Nullable
	private int serviceid;
	@Nullable
	private Integer billid;
	@Nullable
	private Date usedday;
	private String nameservice;

}
