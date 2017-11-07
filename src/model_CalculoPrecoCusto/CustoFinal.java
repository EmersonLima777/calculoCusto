package model_CalculoPrecoCusto;

public class CustoFinal {
	static float vlricmsst;
	static float vlripi;
	static float custofinal;

	//Método de calculo de icmsst
	public static float calcularICMSST(float icmsst, float quantidade){
		vlricmsst = icmsst/quantidade;
		return vlricmsst;
	}

	public static float calcularIPI(float ipi, float quantidade){
		vlripi = ipi/quantidade;
		return vlripi;
	}	

	public static float calcularCustoFinal(float icmsst, float ipi, float custoproduto, float quantidade){
		custofinal = custoproduto + icmsst/quantidade + ipi/quantidade;
		return custofinal;
	}

}
