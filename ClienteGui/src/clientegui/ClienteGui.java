/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientegui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject; 

/**
 *
 * @author Eduardo
 */
public class ClienteGui extends JPanel {
   int cont=0;
   JSONObject objetos;
   Socket socket;
  
    //Contructor, donde crea el objeto Json para guardar los carros, y otros objetos
   //También abre el socket
    public ClienteGui(){

        this.objetos = new JSONObject();
        String testServerName = "localhost";
        int port = 6000;
        try
        {
          // open a socket
          this.socket = openSocket(testServerName, port);

        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        

        try{
           String result = this.writeToAndReadFromSocket(this.socket, "jsonString"); //"GET /\n\n");
           System.out.println(result);
       
     
            }catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    
    
    //parsea el json y grafica
     public void parsearJson(Graphics2D obj) throws Exception{   
         
         
         
       System.out.println(objetos);
        
        ////////////////////////////////////////////////////
        JSONObject carro_Object_1 = (JSONObject) this.objetos.get("carro_Object_1");    
        JSONObject detalles_carro_1 = (JSONObject) carro_Object_1.get("detalles_carro_1"); 
        System.out.println(detalles_carro_1);
        
        ////////////////////////////////////////////////////
        JSONObject carro_Object_2 = (JSONObject) objetos.get("carro_Object_2");    
        JSONObject detalles_carro_2 = (JSONObject) carro_Object_2.get("detalles_carro_2"); 
        System.out.println(detalles_carro_2);
       
        ////////////////////////////////////////////////////
        JSONObject Hueco_Object = (JSONObject) objetos.get("Hueco_Object");    
        JSONObject detalles_hueco = (JSONObject) Hueco_Object.get("detalles_hueco"); 
        System.out.println(detalles_hueco);
        
        ///////////////////////////////////////////////////
        System.out.println(objetos);
         
        System.out.println(detalles_carro_1.get("X"));
        auxpaint(obj,Integer.valueOf((String) detalles_carro_1.get("X")), Integer.valueOf((String) detalles_carro_1.get("Y")),
                Integer.valueOf((String) detalles_carro_1.get("Angle")), "images/" +(String) detalles_carro_1.get("ID"));
        auxpaint(obj,Integer.valueOf((String) detalles_carro_2.get("X")), Integer.valueOf((String) detalles_carro_2.get("Y")),
                Integer.valueOf((String) detalles_carro_2.get("Angle")), "images/" +(String) detalles_carro_2.get("ID"));
        
        
        
       
        /*
         try{
            String result = writeToAndReadFromSocket(socket, "carro_Object_1.toString()"); //"GET /\n\n");
           System.out.println(result);
      
     
       }catch (Exception e)
    {
      e.printStackTrace();
    }
        */
        
    
    
        

     
     }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
  
        JFrame frame = new JFrame("Car Racing Game");   //creating a new JFrame window to display the game  		
        frame.setSize(1900,1000); //setting size of screen to 500x500
        frame.setVisible(true); //allows the JFrame and its children to displayed on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ClienteGui juego = new ClienteGui();
        frame.add(juego);
             
        juego.repaint();
      
        
        
    }
    //Para graficar
     @Override
    public void paint(Graphics g ){
  
        super.paint(g);
        Graphics2D obj = (Graphics2D) g;
        obj.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
        try{            
            datosJasonDelServer(); //recibe los datos del server 
            parsearJson(obj);       //Parsea el jason recibido del server y posteriormente grafica
            
        }
     
        catch(Exception e){
            System.out.println(e);
        }      
        
    }   
    
     //Se usa para dibujar cada objeto, sólo se llama en la funcion paint
    public void auxpaint(Graphics2D obj,int pos_x, int pos_y, int grados, String ID){
        Image image = getToolkit().getImage(ID);
        obj.rotate(Math.toRadians(grados), image.getWidth(this)+getWidth()/2, image.getHeight(this)+getHeight()/2);
        obj.drawImage(image,pos_x,pos_y,this);   //draw car on window
    }
    
    
    String writeToAndReadFromSocket(Socket socket, String writeTo) throws Exception{
     
    try 
    {
     //TimeUnit.SECONDS.sleep(1);
     // write text to the socket
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      bufferedWriter.write(writeTo);
      bufferedWriter.flush();
      
      // read text from the socket
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String str;
      while ((str = bufferedReader.readLine()) != null)
      {
        sb.append(str + "\n");
      }
      
      // close the reader, and return the results as a String
      bufferedReader.close();
      //System.out.println(sb.toString());
      return sb.toString();
      
      //return "";
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
      throw e;
    }
  }
  
  /**
   * Open a socket connection to the given server on the given port.
   * This method currently sets the socket timeout value to 10 seconds.
   * (A second version of this method could allow the user to specify this timeout.)
   */
  private Socket openSocket(String server, int port) throws Exception
  {
    Socket socket;
    
    // create a socket with a timeout
    try
    {
      InetAddress inteAddress = InetAddress.getByName(server);
      SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);
  
      // create a socket
      socket = new Socket();
  
      // this method will block no more than timeout ms.
      int timeoutInMs = 10*1000;   // 10 seconds
      socket.connect(socketAddress, timeoutInMs);
      
      return socket;
    } 
    catch (SocketTimeoutException ste) 
    {
      System.err.println("Timed out waiting for the socket.");
      ste.printStackTrace();
      throw ste;
    }
  }



public void datosJasonDelServer(){ 
        //////////////////////////////////////////////////
        JSONObject detalles_carro_1 = new JSONObject();
        detalles_carro_1.put("X", "100");
        detalles_carro_1.put("Y", "100");
        detalles_carro_1.put("Angle", "0");
        detalles_carro_1.put("vidas", "1");
        detalles_carro_1.put("ID", "car_self.png");
         
        JSONObject carro_1_Object = new JSONObject(); 
        carro_1_Object.put("detalles_carro_1", detalles_carro_1);
         
        
        //////////////////////////////////////////////////
        JSONObject detalles_carro_2 = new JSONObject();       
        detalles_carro_2.put("X", "100");
        detalles_carro_2.put("Y", "0");
        detalles_carro_2.put("Angle", "0");
        detalles_carro_2.put("vidas", "1");
        detalles_carro_2.put("ID", "car_right_2.png");
        
        JSONObject carro_2_Object = new JSONObject(); 
        carro_2_Object.put("detalles_carro_2", detalles_carro_2);
        
        //////////////////////////////////////////////////
        JSONObject detalles_hueco = new JSONObject();       
        detalles_hueco.put("X", "100");
        detalles_hueco.put("Y", "0");
        detalles_hueco.put("Angle", "0");
        detalles_hueco.put("ID", "hueco.png");
        
        JSONObject hueco_Object = new JSONObject(); 
        hueco_Object.put("detalles_hueco", detalles_hueco);
        
        //////////////////////////////////////////////////////////////
        //Crea un nuevo objeto json para guardar todos los objetos
        objetos = new JSONObject(); 
        objetos.put("carro_Object_1",carro_1_Object);
        objetos.put("carro_Object_2",carro_2_Object);
        objetos.put("Hueco_Object",hueco_Object);
    }
}