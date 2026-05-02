package controller.userControllers;

import java.io.IOException; 
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

@WebServlet("/putuser")
public class UserUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao dao = DaoFactory.createUserDao();
	
	public UserUpdateController() {
		super();
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		response.setContentType("application/json");
		response.setCharacterEncoding(StandardCharsets.UTF_8);

		String idParam = request.getParameter("id");
		PrintWriter out = response.getWriter();
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonOut = null;
		
		if(idParam != null && !idParam.isEmpty()) {
			try {
				Integer id = Integer.parseInt(idParam);
				User user = dao.findById(id);
				ObjectReader reader = mapper.readerForUpdating(user);
				user = reader.readValue(request.getReader());
				
				if(user != null) {
					dao.update(user);
					
					jsonOut = new Gson().toJson(user);
					out.write(jsonOut);
				} else {
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Cannot update user.");
				}
			} catch (IOException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Id not found or null.");
		}
	}
}
