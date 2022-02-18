
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ComprobarCodigo extends HttpServlet{
	
	public void proceso (HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		response.setContentType ("text/html");
		PrintWriter out =response.getWriter();
		
		//Variables..............................................................................
		ServletContext contexto = getServletContext();
		Map<String,String> listaCodigosAsignados = (HashMap<String,String>)contexto.getAttribute("codigosAsignados");
		ArrayList<String> codLibres = (ArrayList<String>) contexto.getAttribute("codigosLibres");
	
		String mensaje = ""; String procedencia = "";
		
		//Viene desde form comprobar codigo
		if(request.getParameter("userComprobar") != null){
			String codigo = request.getParameter("codigo");
			String user = request.getParameter("userComprobar");
			String estado = listaCodigosAsignados.get(codigo);
			
			if(!listaCodigosAsignados.containsKey(codigo)){
				mensaje = "Este c�digo no est� asignado o no existe.";
			}else if(estado.equals("usado")){
				mensaje = "Este c�digo ya ha sido utilizado.";
			}else if(!estado.equals(user)){
				mensaje = "Este c�digo pertenece a otro usuario";
			}else{
				mensaje = "Todo correcto para jugar.";
			}
			
			request.setAttribute("estado", estado);
			procedencia = "comprobar";
			
		//Viene desde form generar codigo	
		}else if(request.getParameter("nombreNuevo") != null){
			String nombreNuevo = request.getParameter("nombreNuevo");
			
			if(listaCodigosAsignados.containsValue(nombreNuevo)){
				mensaje = "El usuario introducido ya tiene c�digo asignado.";
			}else if(codLibres.size() == 0){
				mensaje = "Todos los c�digos disponibles est�n asignados.";
			}else{
				mensaje = "Su c�digo asignado a "+ nombreNuevo +" es: " + codLibres.get(0);
				listaCodigosAsignados.put(codLibres.get(0), nombreNuevo);
				codLibres.remove(0);
			}
			
			procedencia = "nuevoUsuario";
		}
		request.setAttribute("mensajeCodigo", mensaje);
		request.setAttribute("procedencia", procedencia);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		proceso(request,response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		proceso(request,response);
	}
}