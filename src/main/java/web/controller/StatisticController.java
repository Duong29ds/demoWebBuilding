package web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import web.MainApplication;
import web.models.Company;
import web.models.Service;
import web.models.ServiceUsed;
import web.models.Staff;
import web.models.StatisticCompany;
import web.repo.CompanyRepository;
import web.repo.ServiceRepository;
import web.repo.ServiceUsedRepository;
import web.repo.StaffRepository;

@Slf4j
@Controller
public class StatisticController {
	private final CompanyRepository companyRepo;
	private final StaffRepository staffRepo;
	private final ServiceUsedRepository serviceUsedRepo;
	private final ServiceRepository serviceRepo;
	
	private List<Service> listService ;
	private List<Staff> listStaff ;
	private List<ServiceUsed> listServiceUsed ;
	private List<Company> listCompany ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	
	@Autowired
	public StatisticController(CompanyRepository companyRepo, StaffRepository staffRepo, ServiceUsedRepository serviceUsedRepo, ServiceRepository serviceRepo)
	{
		this.companyRepo = companyRepo;
		this.staffRepo = staffRepo;
		this.serviceUsedRepo = serviceUsedRepo;
		this.serviceRepo = serviceRepo;
		
		
	}
	
	private float getDetailUsedPrice(int id){
		
		float price = 0;
		
		for(Service s: listService ) {
			if(s.getId() == id)
			{
				return s.getUnit();
			}
		}
		
		return price;
	}
	
	

	
	private float countTotalService(int id) {
		float result = 0;
		int percent = 0;
		
		List<ServiceUsed> listService = serviceUsedRepo.findByCompanyid(id);
		List<Staff> listStaff = staffRepo.findByCompanyid(id);
		int numofStaff = listStaff.size();
		for(ServiceUsed s: listService) {
			if(numofStaff > 5) {
				percent = numofStaff/5;
				percent *= 5;
			}
			result +=  ((percent+100)*getDetailUsedPrice(s.getServiceid()))/100;
		}
		
		return result;
	}
	private StatisticCompany getStatisticCompany(Company company) {
		StatisticCompany sc = new StatisticCompany();
		double totalService = countTotalService(company.getID());
		double totalRent = company.getSquare()*500000;
		double total = totalService + totalRent;
		LOGGER.debug("totalService = "+ String.valueOf(totalService));
		sc.setCompanyid(company.getID());
		sc.setName(company.getName());
		sc.setField(company.getField());
		sc.setDiachi(company.getAddress());
		sc.setTotalService(String.format("%,.2f",totalService));
		sc.setTotalRent(String.format("%,.2f",totalRent));
		sc.setTotal(String.format("%,.2f",total));
		
		return sc;
	}
	
	@GetMapping("/statisticcompany")
	String view(Model model)
	{
		listService = (List<Service>) serviceRepo.findAll();
		listCompany = (List<Company>) companyRepo.findAll();
		List<StatisticCompany> list = new ArrayList<>();
		for(int i = 0 ;i < listCompany.size() ; i++)
		{
			StatisticCompany sc = getStatisticCompany(listCompany.get(i));
			list.add(sc);
			
		}
//		Collections.sort(list, new Comparator<StatisticCompany>() {
//            @Override
//            public int compare(StatisticCompany o1, StatisticCompany o2) {
//                return o2.get;
//            }
//        });
		model.addAttribute("listStatisticCompany", list);
		return "statisticcompany";
	}
	
	
}
