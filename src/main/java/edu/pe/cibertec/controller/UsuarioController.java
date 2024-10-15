package edu.pe.cibertec.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import edu.pe.cibertec.model.Usuario;
import edu.pe.cibertec.service.IUsuarioService;
import net.sf.jasperreports.engine.JRException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioService servicio;
	
	@GetMapping({"/"})
	public String index() {
		return "index";
	}
	
	@GetMapping({"/listarUsuarios"})
	public String listarUsuarios(Model modelo) {
		modelo.addAttribute("usuarios", servicio.listarUsuarios());
		return "usuarios";
	}
	
	@GetMapping({"/nuevoUsuario"})
	public String newUsuario(Model modelo) {
		modelo.addAttribute("usuario", new Usuario());
		return "formUsuario";
	}
	
	@PostMapping({"/save"})
	public String saveUsuario(Usuario usuario) {
		servicio.guardarUsuario(usuario);
		return "redirect:/listarUsuarios";
	}
	
	@GetMapping("/editar/{codigo}")
	public String editUsuario(@PathVariable int codigo, Model modelo) {
		modelo.addAttribute("usuario", servicio.buscarUsuario(codigo));
		return "formUsuario";
	}
	
	@GetMapping("/eliminar/{codigo}")
	public String deleteUsuario(@PathVariable int codigo) {
		servicio.eliminarUsuario(codigo);
		return "redirect:/listarUsuarios";
	}
	
	@GetMapping("/export-pdf")
	public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("usersReport", "userReport.pdf");
		return ResponseEntity.ok().headers(headers).body(servicio.exportPdf());
	}
	
	
	
}
