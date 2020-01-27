package view.bean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class BeanViewAbstract {

	protected void refresh() throws Exception {

		// pega contexto do JSF
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		// pega informacoes solicitadas e http
		StringBuffer requestURL = ((HttpServletRequest) ec.getRequest()).getRequestURL();

		// pega a URL completa da solicitacao
		String queryString = ((HttpServletRequest) ec.getContext()).getQueryString();

		// permite mostrar as mensagens apos redirecionamento
		ec.getFlash().setKeepMessages(true);

		// faz refresh da pagina JSF
		ec.redirect(
				(queryString == null) ? requestURL.toString() : 
					requestURL.append('?').append(queryString).toString());

	}
}
