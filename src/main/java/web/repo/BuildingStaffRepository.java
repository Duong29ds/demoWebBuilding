package web.repo;
import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.models.BuildingStaff;

@Repository
public interface BuildingStaffRepository extends CrudRepository<BuildingStaff, Integer>{
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE BuildingStaff b SET b.name = :name,"
			+ "b.dob = :dob,"
			+ "b.address = :address,"
			+ "b.phone = :phone,"
			+ "b.position = :position,"
			+ "b.level = :level ,"
			+ "b.buildingid =:buildingid "
			+ "WHERE b.id = :id")
	void update(@Param("id") int id,
			@Param("name") String name,
			@Param("dob") Date dob,
			@Param("address") String address,
			@Param("phone") String phone,
			@Param("position") String position,
			@Param("level") String level,
			@Param("buildingid")int buildingid);
//	@Modifying
//    @Query(value="INSERT INTO buildingstaff (name,dob,address,phone,level,position,buildingid) VALUES (:name,:dob,:address,:phone,:level,:position,buildingid)",nativeQuery = true)
//	@Transactional
//    void addBuildingStaff( 
//			@Param("name") String name,
//			@Param("dob") Date dob,
//			@Param("address") String address,
//			@Param("phone") String phone,
//			@Param("level") String level,
//			@Param("position")String position,
//			@Param("buildingid") int buildingid);
}
