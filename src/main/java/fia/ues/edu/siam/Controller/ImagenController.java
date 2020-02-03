package fia.ues.edu.siam.Controller;

import java.net.MalformedURLException;
import java.nio.file.Path;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fia.ues.edu.siam.Services.impl.FileServiceImpl;

@Controller
@RequestMapping("/imagen")
public class ImagenController {
	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileServiceImpl fileService;
	
	
	@GetMapping("/imgs/{image:.+}")
	public ResponseEntity<Resource> index(@PathVariable String image) {
		Path path = fileService.getPath(image, 1);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		}catch (MalformedURLException e) {
			System.out.println("Problema con las url, url mal formada o no existe recurso");
		}
		
		if(!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("Failed to  load image"+image);
		}
		HttpHeaders heads = new HttpHeaders();
		heads.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"");
		return new ResponseEntity<Resource>(resource, heads ,HttpStatus.OK);
	}
	
	
}
