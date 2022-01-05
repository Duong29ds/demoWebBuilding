package web.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class StatisticCompany {
	private int companyid;
	private String name;
	private String field;
	private String diachi;
	private String totalService ;
	private String totalRent;
	private String total;
}
