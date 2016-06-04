package test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class BuscaLivroNome implements Busca {
	private View view;
	private Subject subject;
	private Model model;
	Livro livro;
	private int index = 0;
	private int tam=0;
	private String texto;
	private ObjectSet result;
	
	
	
	
	public BuscaLivroNome(Model model) throws JSONException {
		this.model = model;
		view = new View(this);
		busca();
		
		
	}




	@Override
	public void busca() {
		

		texto=view.buscaNome();
		try {
			model.getData("https://www.googleapis.com/books/v1/volumes?q="+texto.replace(' ', '_'));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	
		
		tam=model.getLivros().query(Livro.class).size();
		Query query = model.getLivros().query();
		query.constrain(Livro.class);
		result = query.execute();
		livro=(Livro) result.next();
		model.deletar(livro.getNome());
	
		
		
		this.model.registerObserver(view);
		view.createView();
		view.update(livro);
		
		
		

	}




	@Override
	public void proximo() {
		// TODO Auto-generated method stub
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
