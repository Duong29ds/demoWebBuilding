package web.repo;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.models.Staff;
import web.models.Company;
@Repository
public interface StaffRepository  extends CrudRepository<Staff, Integer>{
	List<Staff> findByCompanyid(int companyid);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Staff c SET c.name = :name, "
			+ "c.idcard = :idcard, "
			+ "c.dob = :dob,"
			+ " c.phone = :phone,"
			+ " c.companyid = :companyid "
			+ "WHERE c.id = :id")
	void update(@Param("id") int id, 
			@Param("name") String name,
			@Param("idcard") String idcard,
			@Param("dob") Date dob,
			@Param("phone") String phone,
			@Param("companyid") int companyid);
	@Modifying
    @Query(value="INSERT INTO employee (name,idcard,dob,phone,companyid) VALUES (:name,:idcard,:dob,:phone,:companyid)",nativeQuery = true)
	@Transactional
    void addStaff( 
			@Param("name") String name,
			@Param("idcard") String idcard,
			@Param("dob") Date dob,
			@Param("phone") String phone,
			@Param("companyid") int companyid);
}
