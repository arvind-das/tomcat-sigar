package com.transcodingapp.status;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.win32.Service;
import org.hyperic.sigar.win32.Win32Exception;

/**
 * Servlet implementation class TomcatStatus
 */
@WebServlet(name = "status", urlPatterns = { "/" })
public class TomcatStatus extends HttpServlet {
    private static final long serialVersionUID = 1L;
public TomcatStatus() {
        super();
    }
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        int status ;
        final String serviceName = "tomcat7";
        String viewableStatus = null;
        Service service = null;
        try {
            service = new Service(serviceName);
        } catch (Win32Exception e) {
            e.printStackTrace();
        }
        Win32Service wservice = new Win32Service();
        status = service.getStatus();
        System.out.println(status+"");
        if(request.getParameter("type")==null&&status==4){
            viewableStatus = "running";
        }
        else if(request.getParameter("type")==null && status==1){
            viewableStatus = "stopped";
        }
        else if(request.getParameter("type").equals("start")&&status == 1){
            viewableStatus = wservice.triggerService(new String[]{serviceName,"start"});
        }
        else if(request.getParameter("type").equals("restart"))
        {
            viewableStatus = wservice.triggerService(new String[]{serviceName,"restart"});
        }
        else if(request.getParameter("type").equals("stop") && status==4)
        {
            viewableStatus = wservice.triggerService(new String[]{serviceName,"stop"});
        }
        else if(request.getParameter("type").equals("stop") && status==1)
            viewableStatus = "stopped";
        else if(request.getParameter("type").equals("start") && status==4)
            viewableStatus = "running";
         
        else{          
            viewableStatus = "error";
            System.out.println("Hello2");
        }
        while(service.getStatus()!=1 && service.getStatus()!=4)
        {
            System.out.println("it is not a start or stop state");
        }
        status = service.getStatus();
        System.out.println(status+"");
        request.setAttribute("tomcatStatus", status);
        request.setAttribute("serviceName",serviceName);
        request.setAttribute("currentStatus", viewableStatus);
        RequestDispatcher view = getServletContext().getRequestDispatcher(
                "/WEB-INF/views/tomcatStatus.jsp");
        view.forward(request, response);
    }
 
}
