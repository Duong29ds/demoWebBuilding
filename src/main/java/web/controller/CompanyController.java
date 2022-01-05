package web.controller;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import
org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import web.MainApplication;
import web.models.Company;
import web.models.ServiceUsed;
import web.repo.CompanyRepository;
import web.repo.ServiceUsedRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Controller 
public class CompanyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private final CompanyRepository companyRepo;
	private final ServiceUsedRepository serviceUsedRepo;
//	private 
	private int id;
	private Company company;

	public CompanyController(CompanyRepository companyRepo, ServiceUsedRepository serviceUsedRepo) {
		this.companyRepo = companyRepo;
		this.serviceUsedRepo =serviceUsedRepo;
	}
	@GetMapping("/addcompany")
	public String add(Model model) {
		model.addAttribute("company",new Company());
		List<Company> listCompany = (List<Company>) model.getAttribute("allCompany");
		LOGGER.info("name" + listCompany);
		
		return "addcompany";
	}
	@GetMapping("/editCompany/{id}")
	public String editGet(@PathVariable String id, Model model) {
		model.addAttribute("company",new Company());
		List<Company> listCompany = (List<Company>) model.getAttribute("allCompany");
		LOGGER.info("name" + listCompany);
		this.id = Integer.parseInt(id);
		return "editCompany";
	}
	@PostMapping("/editCompany")
	public String editPost(@ModelAttribute Company company,Model model) {
//		companyRepo
		company.setAddress("Nam Tu Liem");
//		this.company = companyRepo.getOne(id);
		
		companyRepo.update(this.id, company.getName(), company.getTaxcode(), company.getFund(), company.getField(), company.getNumofem());
		
		return "redirect:/company";
	}
	@PostMapping("/addcompany")
	public String addCompany(@ModelAttribute Company company, Model model) {
//		companyRepo.
		company.setAddress("Nam Tu Liem");
		companyRepo.save(company)	;
		List<Company> listCompany = (List<Company>) companyRepo.findAll();
		int id = listCompany.get(listCompany.size()-1).getID();
		long millis=System.currentTimeMillis(); 
		List<ServiceUsed> list = new ArrayList<>();
		
		ServiceUsed serviceused = new ServiceUsed();
		serviceused.setCompanyid(id);
		serviceused.setBillid(null);
		serviceused.setServiceid(1);
		Date sqlDate = new Date(millis);
		serviceused.setUsedday(sqlDate);
		
		
		
		ServiceUsed serviceused1 = new ServiceUsed();
		serviceused1.setCompanyid(id);
		serviceused1.setBillid(null);
		serviceused1.setServiceid(4);
		Date sqlDate1 = new Date(millis);
		serviceused1.setUsedday(sqlDate);
		
		List<ServiceUsed> listServiceUsed = (List<ServiceUsed>) serviceUsedRepo.findAll();
		int id1 = listServiceUsed.get(listServiceUsed.size()-1).getId()+1;
		int id2 = id1+1;
		serviceused.setId(id1);
		serviceused1.setId(id2);
		
		list.add(serviceused);
		list.add(serviceused1);
		
		serviceUsedRepo.saveAll(list);
		return "redirect:/company";
		
	}
//	
}
