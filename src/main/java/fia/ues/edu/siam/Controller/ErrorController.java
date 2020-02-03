package fia.ues.edu.siam.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		String retornar = "errores/unknow";
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);		
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				retornar = "errores/E404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				retornar = "errores/E500";
			}else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				retornar = "errores/E403";
			}
		}
		return retornar;
	}
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
    
}
