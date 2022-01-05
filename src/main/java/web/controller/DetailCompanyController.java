package web.controller;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import web.repo.StaffRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Controller 
public class DetailCompanyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private final StaffRepository staffRepo;
	private final CompanyRepository companyRepo;
	private final ServiceUsedRepository serviceUsedRepo;
	private final ServiceRepository serviceRepo;
	private List<Service> listService;
	private List<Staff> listStaff;
	private List<ServiceUsed> listServiceUsed;
	private int id_company;
	private int id;
	private Staff staff;
	
	@Autowired
	public DetailCompanyController(StaffRepository staffRepo, CompanyRepository companyRepo,ServiceRepository serviceRepo, ServiceUsedRepository serviceUsedRepo) {
		this.staffRepo = staffRepo;
		this.companyRepo = companyRepo;
		this.serviceUsedRepo = serviceUsedRepo;
		this.serviceRepo = serviceRepo;
	}
	
	private String getName(int id) {
		for(Service s: listService) {
			if(s.getId() == id)
			{
				return s.getName();
			}
		}
		return "Unknow";
	}
	
	@GetMapping("/staff/{id}")
	public String getStaff(Model model,@PathVariable String id, @Param("keyword") String keyword ) {
		
		LOGGER.debug("keyword : " + keyword);
		listStaff = (List<Staff>) staffRepo.findByCompanyid(Integer.parseInt(id));
		if (keyword != null && keyword.equals("") == false) {
			keyword = keyword.strip();
			List<Staff> allStaff = (List<Staff>) staffRepo.findAll();
			List<Staff> foundStaff = new ArrayList<>();
			for(Staff c : allStaff) {
				if (c.getName().equals(keyword)) {
					foundStaff.add(c);
					model.addAttribute("liststaff", foundStaff);
					break;
				}
			}
		}
		else
			model.addAttribute("liststaff", listStaff);
		
		listService = (List<Service>) serviceRepo.findAll();
		model.addAttribute("listService", listService);
		listServiceUsed = (List<ServiceUsed>) serviceUsedRepo.findByCompanyid(Integer.parseInt(id));
		for(int i=0; i < listServiceUsed.size() ; i++) {
			listServiceUsed.get(i).setNameservice(getName(listServiceUsed.get(i).getServiceid()));
		}
//		listService.get
		model.addAttribute("listServiceUsed", listServiceUsed);
		
		this.id_company=Integer.parseInt(id);
		model.addAttribute("idCompany", this.id_company);
		return "staff";		
	}
	@PostMapping("/editstaff/{id}")
	public String editStaff(Model model,@PathVariable String id)
	{
		Optional<Staff> staff= staffRepo.findById(Integer.parseInt(id));
		model.addAttribute("staff",staff);
		this.id=Integer.parseInt(id);
		return "editStaff";
	}
	
	@PostMapping("/EditStaff")
	public String postEditStaff(@ModelAttribute Staff staff,Model model, String id) {
		staffRepo.update(this.id, staff.getName(), staff.getIdcard(), staff.getDob(), staff.getPhone(),this.id_company);
		String res="redirect:/staff/"+this.id_company;
		return res;
	}
	
	@PostMapping("/addStaff")
	public String addStaff1(Model model)
	{
		model.addAttribute("staff",new Staff());
		return "addStaff";
	}
	
	@PostMapping("/AddStaff")
	public String postAddStaff(@ModelAttribute Staff staff,Model model) {
		staffRepo.addStaff(staff.getName(), staff.getIdcard(), staff.getDob(), staff.getPhone(), this.id_company);
		String res="redirect:/staff/"+this.id_company;
		return res;
	}
	
	@PostMapping("/addServiceUsedProcess/{idser}")
	public String postAddServiceUsed(Model model ,@PathVariable String idser) {
//		logger
		int  iddservice = Integer.parseInt(idser);
		Service choosenSer = new Service();
		for(Service s : listService) {
			if(s.getId() == iddservice) {
				choosenSer = s;
				break;
			}
		}
		
		long millis=System.currentTimeMillis();  
		ServiceUsed serviceused = new ServiceUsed();
		serviceused.setCompanyid(this.id_company);
		serviceused.setBillid(null);
		serviceused.setServiceid(iddservice);
//		Date date = new Date();
		Date sqlDate = new Date(millis);
		serviceused.setUsedday(sqlDate);
		serviceUsedRepo.save(serviceused);
		String res="redirect:/staff/"+this.id_company;
		return res;
	}
	
	
	@GetMapping("/deletestaff/{id}")
	public String deleteStaff(Model model,@PathVariable String id)
	{
		staffRepo.deleteById(Integer.parseInt(id));
		String res="redirect:/staff/"+this.id_company;
		return res;
	}
	@GetMapping("/deleteServiceUsed/{id}")
	public String deleteServiceUsed(Model model,@PathVariable String id)
	{
		serviceUsedRepo.deleteById(Integer.parseInt(id));
		String res="redirect:/staff/"+this.id_company;
		return res;
	}
	@PostMapping("/addServiceUsed")
	public String goAddServiceUsed(Model model)
	{
		model.addAttribute("serviceused",new ServiceUsed());
		return "redirect:/addServiceUsed";
	}
}
