package managedBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import dao.DaoEmail;
import dao.DaoUsuario;
import datatablelazy.LazyDataTableModelUserPessoa;
import model.EmailUser;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean implements Serializable {

	private static final long serialVersionUID = 9084995168573531022L;

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private LazyDataTableModelUserPessoa<UsuarioPessoa> list = new LazyDataTableModelUserPessoa<UsuarioPessoa>();
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();

	private BarChartModel barChartModel = new BarChartModel();
	private EmailUser emailuser = new EmailUser();

	private DaoEmail<EmailUser> daoEmail = new DaoEmail<EmailUser>();

	private String campoPesquisa;

	@PostConstruct
	public void init() {
		
		list.load(0, 5, null, null, null);
		//list = daoGeneric.listar(UsuarioPessoa.class);
		montarGrafico();

	}

	private void montarGrafico() {
		barChartModel = new BarChartModel();

		ChartSeries userSalario = new ChartSeries();

		for (UsuarioPessoa usuarioPessoa : list.list) {

			userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario());

		}

		barChartModel.addSeries(userSalario);
		barChartModel.setTitle("Gráfico de Salários");
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	// pesquisa cep
	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {

			URL url = new URL("https://viacep.com.br/ws/" + usuarioPessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";

			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);

			usuarioPessoa.setCep(userCepPessoa.getCep());
			usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
			usuarioPessoa.setBairro(userCepPessoa.getBairro());
			usuarioPessoa.setUf(userCepPessoa.getUf());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String salvar() {

		daoGeneric.salvar(usuarioPessoa);
		list.list.add(usuarioPessoa);
		usuarioPessoa = new UsuarioPessoa();
		init();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com Sucesso!"));
		return "";
	}

	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}

	public LazyDataTableModelUserPessoa<UsuarioPessoa> getList() {

		return list;
	}

	public String remover() {

		try {

			daoGeneric.removerUsuario(usuarioPessoa);
			list.list.remove(usuarioPessoa);
			usuarioPessoa = new UsuarioPessoa();
			init();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Informação: ", "Excluído com Sucesso!"));

		} catch (Exception e) {

			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Informação: ", "Existem telefones para o usuário!"));
			}
		}
		return "";
	}

	public void setEmailuser(EmailUser emailuser) {
		this.emailuser = emailuser;
	}

	public EmailUser getEmailuser() {
		return emailuser;
	}

	public void addEmail() {

		emailuser.setUsuarioPessoa(usuarioPessoa);
		emailuser = daoEmail.updateMerge(emailuser);
		usuarioPessoa.getEmails().add(emailuser);
		emailuser = new EmailUser();

		// mensagem de salvo com sucesso
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem: ", "Salvo com Suceso!"));
	}

	public void removerEmail() throws Exception {

		String codigoemail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigoemail");

		EmailUser remover = new EmailUser();
		remover.setId(Long.parseLong(codigoemail));
		daoEmail.deletarPorId(remover);
		usuarioPessoa.getEmails().remove(remover);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem: ", "Removido com Sucesso!"));

	}

	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}

	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	public void pesquisar() {
		
		list.pesquisar(campoPesquisa);
		montarGrafico();
	}

	public void upload(FileUploadEvent image) {

		String imagem = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContents());
		usuarioPessoa.setImagem(imagem);

	}

	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadID = params.get("fileDownload");
		UsuarioPessoa pessoa = daoGeneric.pesquisar(Long.parseLong(fileDownloadID), UsuarioPessoa.class);

		byte[] imagem = new Base64().decodeBase64(pessoa.getImagem().split("\\,")[1]);

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		response.addHeader("Content-Disposition", "attachment; filename=download.png");

		response.setContentType("application/octet-stream");

		response.setContentLength(imagem.length);
		response.getOutputStream().write(imagem);
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().getResponseComplete();

	}

}
