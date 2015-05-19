package my.cool.app.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * CSRF対策及び、２重送信制御のためのHandlerInterceptor
 * 
 * TransactionTokenCheckアノテーションを見てトークンの発行、及びトークンチェックを行います。
 * 
 * @author mitsui0273
 *
 */
@Component
public class TransactionTokenInterceptor implements HandlerInterceptor {

	public static final String TRANSACTION_TOKEN_NAME = "X-TRANSACTION-TOKEN";

	@Autowired
	TransactionTokenInfoStore transactionTokenInfoStore;

	@Autowired
	TokenStringGenerator tokenStringGenerator;

	@Override
	public void afterCompletion(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception {
	}

	@Override
	public void postHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		TransactionTokenInfo tokenInfo = transactionTokenInfoStore
				.getTransactionTokenInfo(handlerMethod);
		String attributeName = TRANSACTION_TOKEN_NAME + "/"
				+ tokenInfo.getTokenName();
		String token;
		switch (tokenInfo.getTokenType()) {
		case BEGIN:
			token = tokenStringGenerator.generate();
			request.getSession().setAttribute(attributeName, token);
			response.setHeader(TRANSACTION_TOKEN_NAME, token);
			break;
		case CHECK:
			token = request.getHeader(TRANSACTION_TOKEN_NAME);
			HttpSession session = request.getSession();
			synchronized (session) {
				if (token == null
						|| !token.equals(session.getAttribute(attributeName))) {
					throw new TransactionTokenException();
				}
				session.removeAttribute(attributeName);
			}

			break;
		default:
		}
		return true;
	}

}
