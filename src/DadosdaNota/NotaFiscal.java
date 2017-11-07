package DadosdaNota;

import java.text.DecimalFormat;

import model_CalculoPrecoCusto.CustoFinal;

public class NotaFiscal {

	private String nomeEmpresa;
	private String item;
	private String codProd;
	private float vlrProduto;
	private float ICMSST;
	private float vlrICMSST;
	private float IPI;
	private float vlrIPI;
	private float quantidade;
	private float custoFinal;
	private String Descricao;

	//Objeto que formata os valores para duas casas decimais
	DecimalFormat df = new DecimalFormat("0.00");


	public NotaFiscal() {

		this.nomeEmpresa = "";
		this.item = null;
		this.codProd = null;
		this.vlrProduto = 0F;
		this.vlrICMSST = 0;
		this.vlrIPI = 0;
		this.quantidade = 0;
		this.Descricao = "";
		this.custoFinal = 0;
		this.ICMSST = 0;
		this.IPI = 0;
	}

	/*Calculo de custo final do produto unitário
	 *valor do produto + valor do icmsst + valor do ipi + valor do frete  */
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String NomeEmpresa) {
		this.nomeEmpresa = NomeEmpresa;		
	}
	public String getCustoFinal() {
		custoFinal = CustoFinal.calcularCustoFinal(getICMSST(), getIPI(), getVlrProduto(), getQuantidade());
		return df.format(custoFinal);
	}

	public String getItem() {
		return item;
	}

	public void setItem(String Item) {
		this.item = Item;
	}

	public String getCodProd() {
		return codProd;
	}

	public void setCodProd(String codProd) {
		this.codProd = codProd;
	}

	public float getVlrProduto() {
		df.format(vlrProduto);
		return vlrProduto; 
	}

	public void setVlrProduto(float vlrProduto) {
		this.vlrProduto = vlrProduto;
	}

	public float getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}

	//Este Get busca o valor unitaŕio do icmsst por produto

	public String getVlrICMSST() {
		vlrICMSST = CustoFinal.calcularICMSST(getICMSST(), getQuantidade());
		return df.format(vlrICMSST);
	}

	public void setVlrICMSST(float vlrICMSST) {
		this.ICMSST = vlrICMSST;
	}

	public float getIPI() {
		return IPI;
	}

	public void setIPI(float iPI) {
		this.IPI = iPI;
	}
	//Este Get busca o valor unitaŕio do ipi por produto
	public String getVlrIPI() {
		vlrIPI = CustoFinal.calcularIPI(getIPI(), getQuantidade());
		return df.format(vlrIPI);
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String Descricao) {
		this.Descricao = Descricao;
	}

	public float getICMSST() {
		return ICMSST;
	}

	public void setICMSST(float iCMSST) {
		this.ICMSST = iCMSST;
	}

}
