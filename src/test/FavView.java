package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.json.JSONException;

public class FavView implements ActionListener,Voltar{
	URL url;
	JFrame frame;
	JTable tabela;
	JButton voltar;
	JButton deletar;
	JPanel pTabela;
	JLabel imagem;
	JPanel pBotoes;
	Image img;
	JPanel pImagem;
	List<String> columns = new ArrayList<String>();
	List<String[]> values = new ArrayList<String[]>();
	DefaultTableModel tableModel;
	Menu menu = new Menu();
	
	Model model = Model.getInstance();
	
	public FavView()
	{
		columns.add("Nome");
		
		for(Livro l: model.getAll())
		{
			values.add(new String[] {l.getNome()});
		}
		
		
		createView();
		
	}
	
public void createView(){
		
		
		frame = new JFrame("Favoritos");
		pBotoes = new JPanel();
		tableModel = new DefaultTableModel(values.toArray(new Object[][] {}),columns.toArray());
		tabela = new JTable(tableModel);
		pTabela = new JPanel();
		pImagem = new JPanel();
		voltar = new JButton("Voltar");
		deletar= new JButton("Apagar dos Favoritos");
		imagem = new JLabel();
		JScrollPane scrollpane = new JScrollPane(tabela);
		imagem.setPreferredSize(new Dimension(128,183));
		
		
	
		voltar.addActionListener(this);
		deletar.addActionListener(this);
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(tabela.getSelectedRow() > -1)
				{
				for(Livro l: model.getAll())
				{
					if(l.getNome().equals(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()))
					{
						

									try {
										url = new URL(l.getCapa());

									} catch (MalformedURLException e) {

										e.printStackTrace();
									}
									try {
										img = ImageIO.read(url);
										// imagem = new JLabel(new
										// ImageIcon(img));
										imagem.setIcon(new ImageIcon(img));
									} catch (IOException e) {

										e.printStackTrace();
									}
						}
						
						
						
						
					}
					
				}
			}
				
			
		});
		
		
	
		
		pBotoes.add(voltar);
		pBotoes.add(deletar);
		pTabela.add(scrollpane);
		pImagem.add(imagem);
		tabela.setPreferredSize(new Dimension(300,200));
		frame.getContentPane().add(BorderLayout.SOUTH, pBotoes);
		frame.getContentPane().add(BorderLayout.WEST, pTabela);
		frame.getContentPane().add(BorderLayout.EAST,pImagem);
		
		
		
	
		
		
		
		frame.setSize(650, 300);
		frame.setVisible(true);
		frame.show();
		
		
	}
	
	
	

	
	
	
	
	
	
	
	
	@Override
	public void voltar() {
		try 
		{
			this.frame.dispose();
			
			menu.menu();
		} 
		
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == voltar)
		{
			voltar();
		}
		
		if(event.getSource() == deletar)
		{
			if(tabela.getSelectedRow() > -1)
			{
				
				//System.out.println(tabela.getSelectedRow());
				for(Livro l: model.getAll())
				{
					if(l.getNome().equals(tabela.getValueAt(tabela.getSelectedRow(), 0).toString())) 
					{
						imagem.setIcon(null);
						model.getFavoritos().delete(l);
					}
				}
				tableModel.removeRow(tabela.getSelectedRow());
				//tableModel.getDataVector().removeAllElements();
				/*columns.add("Nome");
				for(Livro l: favoritos.getAll())
				{
					values.add(new String[] {l.getNome()});
				}*/
				
				
				
				
				
				
			}
		}
		
	}
	
	
	
	

}
