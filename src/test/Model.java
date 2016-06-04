package test;




import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Model implements Subject {
	
	private static Model uniqueInstance;
	private int cont;
	private ObjectContainer favoritos;
	private ObjectContainer livros;
	private ArrayList observers;
	private String nome;
	private String desc;
	private Livro livro;
	
	
	
	private Model()
	{
		observers = new ArrayList();
		favoritos = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"fav.db4o");
		livros =  Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "livros.db4o");
		
	}
	
	public static Model getInstance()
	{
		if(uniqueInstance == null)
		{
			uniqueInstance = new Model();
			
		}
		return uniqueInstance;
	}
	
	
	private Connection con;
	
	public void getData(String url) throws JSONException{
		con = new Connection(uniqueInstance);
		uniqueInstance = con.getData(url);
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
		
	}

	@Override
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer)observers.get(i);
			observer.update(livro);//4 polimorfismo
		}
		
	}
	
	public void setMeasurements(Livro livro)
	{
		this.livro = livro;
		notifyObservers();
	}
	
	
	//por exemplo, eu faria aqui TODOS os métodos para manipular esta base de dados
	//Exemplo: buscar coordenadas por nome
	//Exemplo: buscar as coordenadas por tipo (quero todas as coordenadas que forerm do tipo "escola")
	
	
	//------------------------------ Metodos Banco Livro ------------------------------------------------------
	
	
	
	
	
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
	
	
	//-----------------------------------------------  Metodo Favoritos --------------------------------------------------------
	
	
	
	public int adicionarLivroFavorito(Livro livro) {
		cont=0;
		
		for(Livro l: this.getAll())
		{
			if(l.equals(livro))
			{
				cont++;
			}
		}
		if(cont == 0)
		{
			favoritos.store(livro);
			
		}
		
		return cont;
	
		
	}

	public ObjectSet buscarLivroNomeFav(String nome) {
		Query query = favoritos.query();
		query.constrain(Livro.class);
		ObjectSet<Livro> allLivros = query.execute();

		return allLivros;

	

	}

	public ObjectContainer getFavoritos() {
		return favoritos;
	}

	public void deletarFav(String nome) {
		ObjectSet lista = buscarLivroNome(nome); // usar delegacao - evitar a
													// duplicacao do codigo da
													// busca
		if (!lista.isEmpty()) {
			Livro livro = (Livro) lista.next();
			favoritos.delete(livro);
		}

	}
	
	public List<Livro> getAll()
	{
		Query query = favoritos.query();
		query.constrain(Livro.class);
		List<Livro> allLivros = query.execute();

		return allLivros;

	}
	

}
