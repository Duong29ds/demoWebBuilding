package web.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.models.Company;

@Repository
public interface CompanyRepository  extends CrudRepository<Company, Integer> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Company c SET c.name = :name , "
			+ "c.taxcode = :taxcode, "
			+ "c.fund = :fund ,"
			+ " c.field = :field,"
			+ " c.numofem = :numofem "
			+ "WHERE c.ID = :ID")
	void update(@Param("ID") int ID, 
			@Param("name") String name,
			@Param("taxcode") String taxcode,
			@Param("fund") float fund,
			@Param("field") String field,
			@Param("numofem") int numofem);
	
}
