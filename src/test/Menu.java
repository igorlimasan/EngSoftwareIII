package test;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;

public class Menu {
	static public void menu() throws JSONException
	{
		Model model = Model.getInstance();
		
		int op = Integer.parseInt(JOptionPane.showInputDialog("Busca por: \n1-Título\n2-Autor\n3-Assunto\n4-Favoritos\n5-Sair"));
		 switch (op) 
		 {
			case 1:
				BuscaLivroNome buscar = new BuscaLivroNome(model);
				break;
				
			case 2:
				BuscaLivroAutor buscarAutor = new BuscaLivroAutor(model);
				break;
				
			case 3:
				BuscaLivroAssunto buscarAssunto = new BuscaLivroAssunto(model);
				break;
			case 4:
				FavView frame = new FavView();				
				break;
			case 5:
				model.getLivros().close();
				model.getFavoritos().close();
				System.exit(0);
				break;
	
			default:
				JOptionPane.showMessageDialog(null, "DIGITE ALGUM NUMERO RELEVANTE");
			
				break;
		 }
	}

}
