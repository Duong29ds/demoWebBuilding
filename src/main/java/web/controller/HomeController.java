package web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
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
import web.models.Staff;
import web.repo.CompanyRepository;
import web.repo.ServiceUsedRepository;
import web.repo.StaffRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Controller 
//@RequestMapping("/company")
public class HomeController {
	private final CompanyRepository companyRepo;
	private final StaffRepository staffRepo;
	private final ServiceUsedRepository serviceUsedRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	
	@Autowired
	public HomeController(CompanyRepository companyRepo, StaffRepository staffRepo, ServiceUsedRepository serviceUsedRepo)
	{
		this.companyRepo = companyRepo;
		this.staffRepo = staffRepo;
		this.serviceUsedRepo = serviceUsedRepo;
	}

	
	@ModelAttribute
	public void getAllCompany(Model model)
	{
			List<Company> allCompany = (List<Company>) companyRepo.findAll();
			LOGGER.info("Ten Cong ty" + allCompany.get(0).getName());
			model.addAttribute("allCompany", allCompany);
		
			
	}
	@GetMapping("/delete/{id}")
	public String deleteCompany(@PathVariable String id, Model model)
	{
		LOGGER.info("DELETE COMPANY" + id );
		List<ServiceUsed> listService = serviceUsedRepo.findByCompanyid(Integer.parseInt(id));
		List<Staff> listStaff = (List<Staff>) staffRepo.findByCompanyid(Integer.parseInt(id));
		staffRepo.deleteAll(listStaff);
		serviceUsedRepo.deleteAll(listService);
		companyRepo.deleteById(Integer.parseInt(id));
		
		return "redirect:/company";
	}
	@PostMapping("/edit/{id}")
	public String editCompany(Model model) {
		
		return "redirect:/editCompany/{id}";
	}
	@GetMapping("/company")
	public String home(Model model,@Param("keyword") String keyword) {
		LOGGER.debug("keyword = " + keyword);
		if (keyword != null && keyword.equals("") == false) {
			keyword = keyword.strip();
			List<Company> allCompany = (List<Company>) companyRepo.findAll();
			List<Company> foundCompany = new ArrayList<>();
			for(Company c : allCompany) {
				LOGGER.debug("company name : " + c.getName()+".");
				if (c.getName().equals(keyword)) {
					foundCompany.add(c);
					model.addAttribute("allCompany", foundCompany);
					break;
				}
			}
			
		}
		LOGGER.info("GET CALLED");
//		getAllCompany(model);
		return "company";
	}
	
	@PostMapping("/company")
	public String addNewCompanny(Model model) {		
		LOGGER.info("CHANGE TO ADD COMPANY");
		return "redirect:/addcompany";
	}
	
	
}
