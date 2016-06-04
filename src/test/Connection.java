package test;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import java.io.InputStreamReader;
import java.util.LinkedList;

import org.json.JSONObject;
/**
 * Created by Giuliano on 01/10/2015.
 */
public class Connection {
	private Model bd;
	
	public Connection(Model model)
	{
		this.bd = model;
	}

    public Model getData(String s) throws JSONException {



        final StringBuilder result = new StringBuilder();

        URL url;
        HttpURLConnection urlConnection = null;
        try {
        	//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy2.sme.sjc.sp.gov.br",8080));
            url = new URL(s);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();
            

            InputStreamReader isw = new InputStreamReader(in);
            
           


            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                result.append(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        

      
        
        
        Model finalResult = generateJSON(new JSONObject(result.toString()));

        return finalResult;
    }


    public Model generateJSON(JSONObject json){

        //List<Livro> found = new LinkedList<Livro>();

        try {


            for (int i = 0; i < json.getJSONArray("items").length(); i++) {
                JSONObject obj = json.getJSONArray("items").getJSONObject(i);
                //found.add(new Livro(obj.getJSONObject("volumeInfo").getString("title").toString(),obj.getJSONObject("volumeInfo").getString("description")));
                bd.adicionarLivro(new Livro(obj.getJSONObject("volumeInfo").getString("title").toString(),obj.getJSONObject("volumeInfo").getString("description"),obj.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail").toString()));
            }

        } catch (JSONException e) {
            // handle exception
        }

        return bd;

    }

}