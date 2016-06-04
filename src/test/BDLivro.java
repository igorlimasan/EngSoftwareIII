package test;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class BDLivro {
	private static BDLivro uniqueInstance;
	private ObjectContainer livros; 
	
	private BDLivro(){
		livros =  Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "livros.db4o");
	}
	
	public static BDLivro getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new BDLivro();
		}
		return uniqueInstance;
	}
	
	public void adicionarLivro(Livro livro){
		livros.store(livro);
	}
	
	public ObjectSet buscarLivroNome(String nome){
	    Query query = livros.query();
		query.constrain(Livro.class);
		ObjectSet<Livro> allLivros = query.execute();
		
		return allLivros;
		
	}
	
	
	public ObjectContainer getLivros() {
		return livros;
	}

	public void deletar(String nome){
		ObjectSet lista = buscarLivroNome(nome); //usar delegacao - evitar a duplicacao do codigo da busca
		if(!lista.isEmpty()){
			Livro livro = (Livro) lista.next();
			livros.delete(livro);
		}
			
	}

}
