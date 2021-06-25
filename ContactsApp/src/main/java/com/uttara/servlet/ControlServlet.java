package com.uttara.servlet;

import java.io.IOException;
import java.util.List;

import com.uttara.bean.ContactBean;
import com.uttara.bean.RegBean;
import com.uttara.util.Constants;
import com.uttara.util.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("*.do")
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ControlServlet() {
		System.out.println("in Control Servlet");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in doget of Control Servlet");
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in dopost of Control Servlet");
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("in process of Control Servlet");
			String requestURI = request.getRequestURI();
			RequestDispatcher rd = null;
			// for register view
			if (requestURI.contains("registerview")) {
				System.out.println("in register view");
				rd = request.getRequestDispatcher("Register.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			// for login view
			if (requestURI.contains("loginview")) {
				rd = request.getRequestDispatcher("Login.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			// for register
			if (requestURI.contains("registering")) {
				System.out.println("in register");
				RegBean bean = (RegBean) request.getAttribute("reg");
				String result = UserService.register(bean);
				if (result.equals(Constants.SUCCESS)) {
					request.setAttribute("message", "Registered Successfully!...");
					rd = request.getRequestDispatcher("Success.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("message", result);
					rd = request.getRequestDispatcher("Register.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// for login
			if (requestURI.contains("logingin")) {
				RegBean bean = (RegBean) request.getAttribute("log");
				System.out.println(bean);
				String result = UserService.login(bean);
				if (result.equals(Constants.SUCCESS)) {
					HttpSession session = request.getSession(true);
					session.setAttribute("uname", bean.getUname());
					session.setAttribute("bean", bean);
					rd = request.getRequestDispatcher("Menu.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("message", result);
					rd = request.getRequestDispatcher("Login.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			// for LogOut
			if (requestURI.contains("logout")) {
				HttpSession session = request.getSession(false);
				session.removeAttribute("uname");
				session.removeAttribute("bean");
				session.invalidate();
				// response.setHeader("cache control", "no-cache, no-store, must-revalidate");
				request.setAttribute("message", "Logged Out Successfully");
				rd = request.getRequestDispatcher("Success.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// for add contact view
			if (requestURI.contains("addcontactview")) {
				rd = request.getRequestDispatcher("AddContact.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			// Add Contact
			if (requestURI.contains("addcontactinfo")) {
				ContactBean bean = (ContactBean) request.getAttribute("addcontact");
				HttpSession session = request.getSession(false);
				RegBean rb = (RegBean) session.getAttribute("bean");
				System.out.println(bean);
				System.out.println(rb);
				String result = UserService.addContact(bean,rb);
				if (result.equals(Constants.SUCCESS)) {
					request.setAttribute("message", "Contact has been Added Successfully!..");
					rd = request.getRequestDispatcher("ContactSuccess.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("message", result);
					rd = request.getRequestDispatcher("AddContact.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// List Contact View
			if (requestURI.contains("listcontactview")) {
				rd = request.getRequestDispatcher("ListContact.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			
			// List Contact
			if (requestURI.contains("listcontactsinfo")) {
				String order = request.getParameter("order");
				System.out.println(order);
				HttpSession session = request.getSession(false);
				RegBean bean = (RegBean) session.getAttribute("bean");
				System.out.println(bean);
				List<ContactBean> viewContacts = UserService.viewContacts(order, bean);
				if (viewContacts.isEmpty()) {
					request.setAttribute("message", "No Contacts to display");
					rd = request.getRequestDispatcher("ListContact.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("contacts", viewContacts);
					rd = request.getRequestDispatcher("ListResults.jsp");
					try {
						rd.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			// Search Contact View
			if (requestURI.contains("searchcontactview")) {
				rd = request.getRequestDispatcher("SearchContact.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			// Edit ContactView
			if (requestURI.contains("editcontactview")) {
				rd = request.getRequestDispatcher("EditContactView.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			// Search Contact
			if (requestURI.contains("searchcontactinfo")) {
				String order = request.getParameter("search");
				System.out.println(order);
			}
			// Edit Contact
			if (requestURI.contains("editcontactinfo")) {
				String order = request.getParameter("contact");
				System.out.println(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
