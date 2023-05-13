package com.ejerciciosmesa.libros.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.ejerciciosmesa.libros.util.paginator.PageRender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;



import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import com.ejerciciosmesa.libros.models.services.UploadService;





import com.ejerciciosmesa.libros.appdata.AppData;
import com.ejerciciosmesa.libros.models.entities.Libro;
import com.ejerciciosmesa.libros.models.services.LibroService;




@Controller
@SessionAttributes("libro")
@RequestMapping("/libros")
public class LibroController {

	private final AppData appData;
	private final LibroService libroService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "LIBROS"; 
	
	public LibroController(UploadService uploadService,
										 
										 
									     LibroService libroService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.libroService = libroService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Libro> pageLibro = libroService.findAll(pageRequest); 
		PageRender<Libro> paginator = new PageRender<>("/libros/list",pageLibro,5);
		

		model.addAttribute("numlibro", libroService.count());
		model.addAttribute("listlibro", pageLibro);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "libros/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Libro libro = new Libro();		
		model.addAttribute("libro",libro);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "libros/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Libro libro = libroService.findOne(id);
		if(libro==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/libro/list/" + page;
		}
		
		model.addAttribute("libro", libro);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "libros/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Libro libro,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile portada_formname,
@RequestParam("portadaImageText") String portadaImageText,
@RequestParam("portadaImageTextOld") String portadaImageTextOld,

					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(libro.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (libro.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "libros/form";
		}
		
		if(!portada_formname.isEmpty())
	AddUpdateImagePortada(portada_formname,libro);
else {
	if(portadaImageText.isEmpty() && !(portadaImageTextOld.isEmpty())) {
		uploadService.delete(portadaImageTextOld);
		libro.setPortada(null);
	}
}



		
		
		
		libroService.save(libro);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/libros/list/" + page;
	}
	
	
	private void AddUpdateImagePortada(MultipartFile image, Libro libro) {
					
			if(libro.getId()!=null &&
			   libro.getId()>0 && 
			   libro.getPortada()!=null &&
			   libro.getPortada().length() > 0) {
			
				uploadService.delete(libro.getPortada());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			libro.setPortada(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Libro libro = libroService.findOne(id);
			
			if(libro != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				libroService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/libros/list/" + page;
			}
			
			if(libro.getPortada()!=null)
	uploadService.delete(libro.getPortada());

						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/libros/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Libro libro = libroService.findOne(id);

			if (libro == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/libros/list/" + page;
			}

			model.addAttribute("libro", libro);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "libros/view";
			
		}

		return "redirect:/libros/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Libro libro = libroService.findOne(id);

			if (libro == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/libros/list";
			}

			model.addAttribute("libro", libro);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "libros/viewimg";
			
		}

		return "redirect:/libros/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = libroService.count();
		int nPag = (int) (nReg / 10);
		if (nReg % 10 == 0)
			nPag--;
		return nPag;
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

