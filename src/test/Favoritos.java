package test;

import java.util.List;

import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Favoritos {
	private static Favoritos uniqueInstance;
	private ObjectContainer favoritos;
	private int cont=0;

	private Favoritos() {
		favoritos = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"fav.db4o");
	}

	public static Favoritos getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Favoritos();
		}
		return uniqueInstance;
	}

	public int adicionarLivro(Livro livro) {
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

	public ObjectSet buscarLivroNome(String nome) {
		Query query = favoritos.query();
		query.constrain(Livro.class);
		ObjectSet<Livro> allLivros = query.execute();

		return allLivros;

		/*
		 * Query query=livros.query(); query.descend("nome").constrain(nome);
		 * ObjectSet result = query.execute(); return result;
		 */

	}

	public ObjectContainer getFavoritos() {
		return favoritos;
	}

	public void deletar(String nome) {
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
