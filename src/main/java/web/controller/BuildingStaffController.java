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

import web.models.BuildingStaff;
import web.models.Staff;
import web.repo.BuildingStaffRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
@Controller
public class BuildingStaffController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	@Autowired
	private BuildingStaffRepository buildingstaffRepo;
	private int id;
	
	private BuildingStaff buildingstaff;
	private List<BuildingStaff> listBuildingStaff;
	public BuildingStaffController() {
		
	}
	
	@GetMapping("/staffBuilding")
	public String getBuildingStaff(Model model , @Param("keyword") String keyword) {
		listBuildingStaff = (List<BuildingStaff>) buildingstaffRepo.findAll();
		if (keyword != null && keyword.equals("") == false) {
			keyword = keyword.strip();
			List<BuildingStaff> allStaff = (List<BuildingStaff>) buildingstaffRepo.findAll();
			List<BuildingStaff> foundStaff = new ArrayList<>();
			for(BuildingStaff c : allStaff) {
				if (c.getName().equals(keyword)) {
					foundStaff.add(c);
					model.addAttribute("listStaffBuilding", foundStaff);
					break;
				}
			}
		}
		else
			model.addAttribute("listStaffBuilding", listBuildingStaff);
		return "staffBuilding";		
	}
	@GetMapping("/addStaffBuilding")
	public String getAddBuildingStaff(Model model) {
		model.addAttribute("buildingstaff",new BuildingStaff());
		listBuildingStaff = (List<BuildingStaff>) buildingstaffRepo.findAll();
		model.addAttribute("listStaffBuilding", listBuildingStaff);
		return "addStaffBuilding";		
	}
	@PostMapping("/editStaffBuilding/{id}")
	public String editStaff(Model model,@PathVariable String id)
	{
		Optional<BuildingStaff> buildingstaff= buildingstaffRepo.findById(Integer.parseInt(id));
		model.addAttribute("buildingstaff",buildingstaff);
		this.id=Integer.parseInt(id);
		return "editStaffBuilding";
	}

	
	@PostMapping("/EditStaffBuilding")
	public String postEditStaff(@ModelAttribute BuildingStaff buildingstaff,Model model, String id) {
		buildingstaffRepo.update(this.id, buildingstaff.getName(), buildingstaff.getDob(), buildingstaff.getAddress(), buildingstaff.getPhone(),buildingstaff.getPosition(),buildingstaff.getLevel(),buildingstaff.getBuildingid());
		String res="redirect:/staffBuilding";
		return res;
	}
	
	@PostMapping("/addStaffBuilding")
	public String addBuildingStaff(Model model)
	{
		model.addAttribute("buildingstaff",new BuildingStaff());
		return "redirect:/addStaffBuilding";
	}
	
	@PostMapping("/AddStaffBuilding")
	public String postAddStaff(@ModelAttribute BuildingStaff buildingstaff,Model model) {
//		LOGGER.debug(null)
		buildingstaffRepo.save(buildingstaff);
//		buildingstaffRepo.addBuildingStaff(buildingstaff.getName(),buildingstaff.getDob(),buildingstaff.getAddress(),buildingstaff.getPhone(),buildingstaff.getLevel(),buildingstaff.getPosition(),buildingstaff.getBuildingid());
		String res="redirect:/staffBuilding";
		return res;
	}
	
	@GetMapping("/deleteStaffBuilding/{id}")
	public String deletebBuildingStaff(Model model,@PathVariable String id)
	{
		buildingstaffRepo.deleteById(Integer.parseInt(id));
		String res="redirect:/staffBuilding";
		return res;
	}
	
}
