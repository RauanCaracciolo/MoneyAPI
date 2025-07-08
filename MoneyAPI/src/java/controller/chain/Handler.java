package controller.chain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Handler {
	void sextNext(Handler next);
	
	void handle(HttpServletRequest request, HttpServletResponse response);
}
