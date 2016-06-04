package test;

public class Livro {
	private String nome;
	private String descricao;
	private String capa;
	
	public Livro(String nome,String descricao,String capa) {
		this.nome = nome;
		this.descricao = descricao;
		this.capa = capa;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	
	
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getCapa()
	{
		return capa;
	}

}
