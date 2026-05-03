package controller.characterControllers;

import java.io.IOException;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CharacterDao;
import model.dao.DaoFactory;
import model.entities.Character;

@WebServlet("/getcharacter")
public class CharacterFindByIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CharacterDao dao = DaoFactory.createCharacterDao();
	
	public CharacterFindByIdController() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding(StandardCharsets.UTF_8);
		
		String idParam = request.getParameter("id");
		PrintWriter out = response.getWriter();
		String jsonOut = null;
		
		if(idParam != null && !idParam.isEmpty()) {
			try {
				Integer id = Integer.parseInt(idParam);
				Character character = dao.findById(id);
				
				if(character != null) {
					jsonOut = new Gson().toJson(character);
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Character not found or not exists.");
				}
			} catch(IOException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			}
			
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id not found or not exists.");
		}
		
		out.write(jsonOut);
	}
}
