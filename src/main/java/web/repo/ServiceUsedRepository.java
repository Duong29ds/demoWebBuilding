package web.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import web.models.Service;
import web.models.ServiceUsed;
import web.models.Staff;

@Repository
public interface ServiceUsedRepository  extends CrudRepository<ServiceUsed, Integer> {
	List<ServiceUsed> findByCompanyid(int companyid);
	List<ServiceUsed> findByServiceid(int serviceid);

}
