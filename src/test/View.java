package test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.json.JSONException;



public class View implements ActionListener, Observer, Voltar{
	
	//private Subject model;
	private Busca acesso;
	
	
	//private String nome;
	//private String desc;
	private Livro livro;
	
	JFrame frame;
	JPanel panelBotoes;
	JPanel panelTitulo;
	JPanel panelDesc;
	JLabel nomeLivro;
	JTextArea descLivro;
	JLabel titulo;
	JLabel descricao;
	
	
	JButton prox;
	JButton voltar;
	JButton fav;


	Model model = Model.getInstance();
	Menu menu = new Menu();
	
	

	public View(Busca acesso) {
		
	    this.acesso = acesso;
//		this.p = proximo;
//		this.s = sair;
	    

	}
	
public void createView(){
		
		frame = new JFrame("Busca de Livro");
		panelBotoes = new JPanel();
		panelTitulo = new JPanel();
		panelDesc = new JPanel();
		prox = new JButton("Próximo");
		voltar = new JButton("Voltar");
		fav = new JButton("Adicionar aos favoritos");
		nomeLivro = new JLabel();
		descLivro = new JTextArea();
		titulo=new JLabel("Titulo: ");
		descricao = new JLabel("Descrição: ");
		voltar.setEnabled(false);
		
		
		prox.addActionListener(this);
		voltar.addActionListener(this);
		fav.addActionListener(this);
	
		
		panelBotoes.add(prox);
		panelBotoes.add(voltar);
		panelBotoes.add(fav);

		panelTitulo.add(titulo);
		panelTitulo.add(nomeLivro);
		panelDesc.add(descricao);
		panelDesc.add(descLivro);
		descLivro.setLineWrap(true);
		descLivro.setEditable(false);
		descLivro.setPreferredSize(new Dimension(500, 200));
		frame.getContentPane().add(BorderLayout.SOUTH, panelBotoes);
		frame.getContentPane().add(BorderLayout.CENTER, panelDesc);
		frame.getContentPane().add(BorderLayout.NORTH, panelTitulo);
		
		
	
		
		
		
		frame.setSize(600, 300);
		frame.setVisible(true);
		
		
	}

	@Override
	public void update(Livro livro) {
		// TODO Auto-generated method stub
		this.livro = livro;
		
		display();

	}


	

	public void display() {

		nomeLivro.setText(livro.getNome());
		descLivro.setText(livro.getDescricao());
		

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == prox)
		{
		
			acesso.proximo();
		}
		
		else if(event.getSource() == voltar)
		{
			this.voltar();
			
		}
		
		else if(event.getSource() == fav)
		{
			int qtde=model.adicionarLivroFavorito(livro);
			if(qtde == 0)JOptionPane.showMessageDialog(null,"Livro Adicionado aos Favoritos =D");
			else JOptionPane.showMessageDialog(null,"Livro já pertence aos Favoritos =( ");
		}
		
	}

	@Override
	public void voltar() {
		try {
			
			this.frame.dispose();
			
			
			menu.menu();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String buscaAutor()
	{
		return JOptionPane.showInputDialog("Digite o autor do Livro: ");
		
	}
	
	public String buscaNome()
	{
		return JOptionPane.showInputDialog("Digite o nome do Livro: ");
	}
	
	public String buscaAssunto()
	{
		return JOptionPane.showInputDialog("Digite o assunto do Livro: ");
	}
	

	

	
}
