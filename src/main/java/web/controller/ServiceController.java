package web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import web.models.Service;
import web.models.ServiceUsed;
import web.models.Staff;
import web.repo.CompanyRepository;
import web.repo.ServiceRepository;
import web.repo.ServiceUsedRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Controller 
//@RequestMapping("/company")
public class ServiceController {
	private final ServiceRepository serviceRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private final ServiceUsedRepository serviceUsedRepo;
	private List<Service> listService;
	private int id;
	
	@Autowired
	public ServiceController (ServiceRepository serviceRepo, ServiceUsedRepository serviceUsedRepo)
	{
		this.serviceRepo = serviceRepo;
		this.serviceUsedRepo = serviceUsedRepo;
	}
	
	@ModelAttribute
	public void getAllService(Model model)
	{
		listService = (List<Service>) serviceRepo.findAll();			
	}
	
	@GetMapping("/service/delete/{id}")
	public String deleteService(@PathVariable String id, Model model)
	{
		LOGGER.info("DELETE " + id );
		List<ServiceUsed> listServiceUsed = serviceUsedRepo.findByServiceid(Integer.parseInt(id));
		serviceUsedRepo.deleteAll(listServiceUsed);
		serviceRepo.deleteById(Integer.parseInt(id));
		return "redirect:/service";
	}
	@PostMapping("/service/edit/{id}")
	public String editService(Model model) {
		
		return "redirect:/editService/{id}";
	}
	@GetMapping("/editService/{id}")
	public String editService(@PathVariable String id, Model model) {
		LOGGER.info("GET CALLED");
		int idServ = Integer.parseInt(id);
		this.id = idServ;
		Service selectedService = null;
		for(Service s : listService) {
			if(idServ == s.getId()) {
				selectedService = s;
				break;
			}
		}
		model.addAttribute("selectedService", selectedService);
//		getAllCompany(model);
		return "editService";
	}
	@GetMapping("/service")
	public String homeService(Model model, @Param("keyword") String keyword ) {
		LOGGER.info("GET CALLED");
		if (keyword != null && keyword.equals("") == false) {
			keyword = keyword.strip();
			List<Service> allService = (List<Service>) serviceRepo.findAll();
			List<Service> foundService = new ArrayList<>();
			for(Service c : allService) {
				if (c.getName().equals(keyword)) {
					foundService.add(c);
					model.addAttribute("allService", foundService);
					break;
				}
			}
		}
		else
			model.addAttribute("allService", listService);
//		getAllCompany(model);
		return "service";
	}
	@GetMapping("/addservice")
	public String addServiceView(Model model) {
		LOGGER.info("GET CALLED ADDSERVICE");
		model.addAttribute("service",new Service());
//		getAllCompany(model);
		return "addService";
	}
	@PostMapping("/addservice")
	public String addCompany(@ModelAttribute Service service, Model model) {
//		companyRepo.
		serviceRepo.save(service)	;
		return "redirect:/service";
		
	}
	
	@PostMapping("/service")
	public String addNewService(Model model) {		
		LOGGER.info("CHANGE TO ADD COMPANY");
		return "redirect:/addservice";
	}
	@PostMapping("/service/edit")
	public String editPost(@ModelAttribute Service service,Model model) {
//		companyRepo
//		this.company = companyRepo.getOne(id);
		
		serviceRepo.update(this.id, service.getName(), service.getType(), service.getUnit());
		return "redirect:/service";
	}
	
	
}
