package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(urlPatterns = "/bookReservation")
public class BookReservationServlet extends HttpServlet {
    private final SpringTemplateEngine templateEngine;
    private final BookReservationService bookReservationService;

    public BookReservationServlet(SpringTemplateEngine templateEngine, BookReservationService bookReservationService) {
        this.templateEngine = templateEngine;
        this.bookReservationService = bookReservationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        String bookTitle = req.getParameter("bookTitle");
        String readerName = req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        Integer numOfCopies = Integer.valueOf(req.getParameter("numOfCopies"));

        context.setVariable("bookTitle", bookTitle);
        context.setVariable("readerName", readerName);
        context.setVariable("readerAddress", readerAddress);
        context.setVariable("numOfCopies", numOfCopies);

        templateEngine.process("reservationConfirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookTitle = req.getParameter("bookTitle");
        String readerName = req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        int numOfCopies = Integer.parseInt(req.getParameter("numOfCopies"));

        BookReservation bookReservation = bookReservationService.placeReservation(readerName, readerAddress, bookTitle, numOfCopies);

        String params = String.format("bookTitle=%s&readerName=%s&readerAddress=%s&numOfCopies=%d", bookReservation.getBookTitle(), bookReservation.getReaderName(), bookReservation.getReaderAddress(), bookReservation.getNumberOfCopies());

        resp.sendRedirect("/bookReservation?"+params);
    }
}
