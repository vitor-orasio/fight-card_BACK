package controller.userControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

@WebServlet("/deleteuser")
public class UserDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao dao = DaoFactory.createUserDao();
	
	public UserDeleteController() {
		super();
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		response.setContentType("application/json");
		response.setCharacterEncoding(StandardCharsets.UTF_8);
		
		String idParam = request.getParameter("id");
		PrintWriter out = response.getWriter();
		
		if(idParam != null && !idParam.isEmpty()) {
			try {
				Integer id = Integer.parseInt(idParam);
				User user = dao.findById(id);
				
				if(user != null) {
					dao.deleteById(user.getId());
					out.write("OK");
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found or not exists.");
				}
			} catch (IOException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
			}
			
		} else {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Id not found or no exists");
		}
	}
}
