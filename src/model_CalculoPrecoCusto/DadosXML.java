package model_CalculoPrecoCusto;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import DadosdaNota.NotaFiscal;
import Interface.TelaInicialInterface;

public class DadosXML {
	public static NotaFiscal nota = new NotaFiscal();
	static TelaInicialInterface telaInterface = new TelaInicialInterface();
	public static ArrayList<String> Item = new ArrayList<String>();
	public static ArrayList<String> Descricao = new ArrayList<String>();
	public static ArrayList<String> CodProd = new ArrayList<String>();
	public static ArrayList<Float> vlrProd = new ArrayList<Float>();
	public static ArrayList<String> vlrICMSST = new ArrayList<String>();
	public static ArrayList<String> vlrIPI = new ArrayList<String>();
	public static ArrayList<String> custoFinal = new ArrayList<String>();
	
	public static String diretorio;


	public static void importarNfe() throws Exception {

		
			//Montagem do documento XML para importação

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			//Aqui eu passo o diretório da classe de importação

			Document doc = builder.parse(importacaoNfe.importar());

			//Criação de uma lista para colocar o tamanho do XML a partir do Nó Pai
			NodeList tamanholistaItem = doc.getElementsByTagName("det");


			//Busca o nome da Empresa na Nota Fiscal

			NodeList nodeNome = doc.getElementsByTagName("xNome");
			Element elementoNome = (Element) nodeNome.item(0);
			


			for (int numitem = 0; numitem < tamanholistaItem.getLength(); numitem++) {
				//Criação do Nó Item
				Node nNodeItem  = tamanholistaItem.item(numitem);

				if (nNodeItem.getNodeType() == Node.ELEMENT_NODE) {

					Element elementoItem = (Element) nNodeItem;
					//Aqui eu busco número do item e coloco na variável item da Nota Fiscal
					nota.setItem(elementoItem.getAttribute("nItem"));

					//Criação de uma lista de filhos do Nó Item
					NodeList listaDeFilhosItens  = tamanholistaItem.item(numitem).getChildNodes();

					for (int numItemFilho = 0; numItemFilho < listaDeFilhosItens.getLength(); numItemFilho++) {

						//Cria Lista de Produtos
						Node nNodeProd  = listaDeFilhosItens.item(numItemFilho);

						if (nNodeProd.getNodeType() == Node.ELEMENT_NODE) {

							Element elementoProd = (Element) nNodeProd;

							//Veririficação se é o filho produto("prod")
							if (nNodeProd.getNodeName().equals("prod")) {

								/*
								 * Busca os dados do produto e coloca em suas devidas variáveis
								 * Código, Descrição, Valor e Quantidade
								 */
								nota.setCodProd(elementoProd.getElementsByTagName("cProd").item(0).getTextContent());
								nota.setDescricao(elementoProd.getElementsByTagName("xProd").item(0).getTextContent());
								nota.setVlrProduto(Float.parseFloat(elementoProd.getElementsByTagName("vUnCom").item(numItemFilho).getTextContent()));
								nota.setQuantidade(Float.parseFloat(elementoProd.getElementsByTagName("qCom").item(numItemFilho).getTextContent()));
							}

							//Verifica se o filho de Item é Imposto 
							else if (nNodeProd.getNodeName().equals("imposto")) {

								//Cria Lista de Impostos
								NodeList listaDeImpostos  = listaDeFilhosItens.item(numItemFilho).getChildNodes();

								for (int numImposto = 0; numImposto < listaDeImpostos.getLength(); numImposto++) {
									Node nNodeImposto = listaDeImpostos.item(numImposto);

									if (nNodeImposto.getNodeType() == Node.ELEMENT_NODE) {

										//Verifica se imposto é ICMS
										if (nNodeImposto.getNodeName().equals("ICMS")) {

											//Cria Lista de ICMS
											NodeList listaDoICMS  = listaDeImpostos.item(numImposto).getChildNodes();

											for (int numICMS = 0; numICMS < listaDoICMS.getLength(); numICMS++){
												Node nNodeICMS = listaDoICMS.item(numICMS);

												if (nNodeImposto.getNodeType() == Node.ELEMENT_NODE){

													Element elementoICMS = (Element) nNodeICMS;

													//Verifica se é o ICMS10 para retirar o valor de ST
													if (nNodeICMS.getNodeName().equals("ICMS10")){

														//Busca o valor do Icms ST e coloca na variável ICMSST da nota fiscal
														nota.setICMSST(Float.parseFloat(elementoICMS.getElementsByTagName("vICMSST").item(numICMS).getTextContent()));

														//Se o imposto na lista de ICMS não for ICMS10 coloca valor R$0,00 no ICMSST 
													}else {
														float icmsst = 0.00f;
														//System.out.println("Valor do ICMST: R$ 0,00");
														nota.setICMSST(icmsst);
													}
												}
											}//fim da lista ICMS
											//Verifica se o Imposto é IPI	
										}if(nNodeImposto.getNodeName().equals("IPI")) {

											//Cria a lista de IPI
											NodeList listaDoIPI  = listaDeImpostos.item(numImposto).getChildNodes();

											for (int numIPI = 0; numIPI < listaDoIPI.getLength(); numIPI++){
												Node nNodeIPI = listaDoIPI.item(numIPI);

												if (nNodeImposto.getNodeType() == Node.ELEMENT_NODE){

													Element elementoIPI = (Element) nNodeIPI;

													/*
													 * Verifica se o IPI é tributado no produto,
													 * se sim buscao valor e coloca na variável de IPI da nota fiscal
													 * senão coloca valor 0,00. 
													 */
													if (nNodeIPI.getNodeName().equals("IPITrib")){

														nota.setIPI(Float.parseFloat(elementoIPI.getElementsByTagName("vIPI").item(0).getTextContent()));

													}else if(numIPI == listaDoIPI.getLength()-1){
														float ipi = 0.00f;

														//System.out.println("Valor do IPI: R$ 0,00");
														nota.setIPI(ipi);
													}
												}
											}//fim da lista de IPI
										}
									}
								}//fim da lista de imposto
							}
						}
					}
				}//Fim da lista de produto
				/*
				 * Aqui eu monto os dados das notas em arrays setando os valores de cada nó acima
				 * Numero do Item, Codigo, Descricao, valor do produto, valor do ICMS ST, Valor do IPI
				 * O custo final do produto é calculado na classe da nota no seu get
				 */
				Item.add(nota.getItem()); 
				CodProd.add(nota.getCodProd());
				Descricao.add(nota.getDescricao());
				vlrProd.add(nota.getVlrProduto());
				vlrICMSST.add(nota.getVlrICMSST());
				vlrIPI.add(nota.getVlrIPI());
				custoFinal.add(nota.getCustoFinal());
				
			}//Fim da lista de Itens
			nota.setNomeEmpresa(elementoNome.getTextContent());
			
	}


}
