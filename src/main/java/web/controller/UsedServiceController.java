package web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;
import web.MainApplication;
import web.models.Service;
import web.models.ServiceUsed;
import web.repo.ServiceRepository;
import web.repo.ServiceUsedRepository;

@Slf4j
@Controller 
public class UsedServiceController {
	private ServiceRepository serviceRepo;
	private ServiceUsedRepository serviceUsedRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private List<Service> listService;
	private int id;
	@Autowired
	public UsedServiceController (ServiceRepository serviceRepo, ServiceUsedRepository serviceUsedRepo)
	{
		this.serviceRepo = serviceRepo;
		this.serviceUsedRepo = serviceUsedRepo;
	}
	@ModelAttribute
	public void getAllService(Model model)
	{
		listService = (List<Service>) serviceRepo.findAll();
		model.addAttribute("listService", listService);
			
	}
	@GetMapping("/addServiceUsed")
	public String homeAddServiceUsed(Model model) {
		LOGGER.info("GET CALLED");
		model.addAttribute("serviceused",new ServiceUsed());
//		getAllCompany(model);
		return "addServiceUsed";
	}
	
}
