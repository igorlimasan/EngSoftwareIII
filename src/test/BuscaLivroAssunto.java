package test;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class BuscaLivroAssunto implements Busca
{
	private View view;
	private Subject subject;
	private Model model;

	Livro livro;
	private int index = 0;
	private int tam=0;
	private String texto;
	private ObjectSet result;
	
	public BuscaLivroAssunto(Model model) throws JSONException
	{
		view = new View(this);
		this.model = model;
		busca();
		
	}

	@Override
	public void busca() 
	{
		
		texto= view.buscaAssunto();
		try {
			model.getData("https://www.googleapis.com/books/v1/volumes?q=+subject="+texto.replace(" ", "_"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
		tam = model.getLivros().query(Livro.class).size();
		System.out.println(tam);
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
