package web.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.models.Service;

@Repository
public interface ServiceRepository  extends CrudRepository<Service, Integer> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Service s SET s.name = :name , "
			+ "s.type = :type, "
			+ "s.unit = :unit "
			+ "WHERE s.id = :id")
	void update(@Param("id") int id, 
			@Param("name") String name,
			@Param("type") String type,
			@Param("unit") float unit
			);
	
}
