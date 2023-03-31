package com.meli.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.meli.app.component.AppProperties;
import com.meli.app.dto.UrlDTO;
import com.meli.app.rest.cache.ShortenerCache;

@RestController
@RequestMapping("/meli")
public class MyRestController {
	
	@Autowired
	private AppProperties appProperties;

	@PostMapping("/urlshort")
	public UrlDTO urlshort(@RequestBody UrlDTO url) {
		ShortenerCache.addPropertieUrlBase(appProperties.getIpPublica());
		final UrlDTO respuesta = new UrlDTO();
		if(url.getUrl() != null && !url.getUrl().isEmpty()) {
			respuesta.setUrl(ShortenerCache.getUrlShort(url.getUrl()));	
		}
		return respuesta;
	}
	
	@PostMapping("/urlLarge")
	public ResponseEntity<UrlDTO> urlLarge(@RequestBody UrlDTO url) {
		ShortenerCache.addPropertieUrlBase(appProperties.getIpPublica());
		final UrlDTO respuesta = new UrlDTO();
		
		if(url.getUrl() != null && !url.getUrl().isEmpty()) {
			String urlOriginal = ShortenerCache.getUrlOriginal(url.getUrl());
			if(urlOriginal != null) {
				respuesta.setUrl(urlOriginal);
				return ResponseEntity.ok(respuesta);	
			}else {
				respuesta.setUrl("");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	}
	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteUser(@PathVariable String id) {
		    ShortenerCache.addPropertieUrlBase(appProperties.getIpPublica());
	        ShortenerCache.deleteUrlShort(id);
	        return ResponseEntity.ok("Url Corta eliminada exitosamente");
	    } 
	 @GetMapping("/{id}")
	    public RedirectView  redirectUrl(@PathVariable String id) {
		 RedirectView redirectView = new RedirectView();
		 ShortenerCache.addPropertieUrlBase(appProperties.getIpPublica());
		 String urlOriginal = ShortenerCache.getUrlOriginal(id);
		 if(urlOriginal != null) {
			 
		        redirectView.setUrl(urlOriginal);
		 }else {
			 redirectView.setStatusCode(HttpStatus.NOT_FOUND);
			 redirectView.setUrl("");
		 }
		 return redirectView;
	    }
}
