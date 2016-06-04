package test;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class BuscaLivroAutor implements Busca
{
	private View view;
	private Subject subject;
	private Model model;
	Livro livro;
	private int index = 0;
	private int tam=0;
	private String texto;
	private ObjectSet result;
	
	public BuscaLivroAutor(Model model) throws JSONException 
	{
		this.model = model;
		view = new View(this);
		busca();
		
	}

	@Override
	public void busca() 
	{
	
		texto= view.buscaAutor();
		try {
			model.getData("https://www.googleapis.com/books/v1/volumes?q=+inauthor:"+texto.replace(" ", "_"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
		tam = model.getLivros().query(Livro.class).size();
		Query query = model.getLivros().query();
		query.constrain(Livro.class);
		result = query.execute();
		livro = (Livro) result.next();
		model.deletar(livro.getNome());
	
		
		
		this.model.registerObserver(view);
		view.createView();
		view.update(livro);
		
		
		//model.removeObserver(view);
	}

	@Override
	public void proximo() 
	{
		index++;
		if(index != tam && result.hasNext())
		{
			livro=(Livro) result.next();
			view.update(livro);
			model.deletar(livro.getNome());
		}
		else
		{
			view.voltar.setEnabled(true);
			view.prox.setEnabled(false);
			
		}
		
	}



}
