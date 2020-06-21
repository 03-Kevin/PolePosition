/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientegui;



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
import java.util.concurrent.TimeUnit;


import org.json.simple.JSONObject;
/**
 *
 * A complete Java class that demonstrates how to use the Socket
 * class, specifically how to open a socket, write to the socket,
 * and read from the socket.
 *
 * @author alvin alexander, alvinalexander.com.
 *
 */
public class SimpleSocketClient  
{
/*
  // call our constructor to start the program
  public static void main(String[] args)
  {
    new SimpleSocketClient();
  }*/
    
    Socket socket;
  
  public SimpleSocketClient()
  {
    String testServerName = "localhost";
    int port = 6000;
    try
    {
      // open a socket
      socket = openSocket(testServerName, port);
      /*
      //crea json
      JSONObject obj = new JSONObject();
      //da formato json
      obj.put("car_1","X,Y,Q");
      obj.put("car_2","X,Y,Q");
      obj.put("vidas_car_1","V");
      obj.put("vidas_car_1","V");
      obj.put("hueco","X,Y");
      obj.put("disparo","0");
      
      obj.toString();
      
      //while(true){
      // write-to, and read-from the socket.
      // in this case just write a simple command to a web server.
      String result = writeToAndReadFromSocket(socket, obj.toString());//"GET /\n\n");
       
      // print out the result we got back from the server
      System.out.println(result);
*/
      // close the socket, and we're done
      //socket.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  
  

  String writeToAndReadFromSocket(Socket socket, String writeTo) throws Exception
  {
     System.out.println("loooool");
    try 
    {
     TimeUnit.SECONDS.sleep(1);
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

}
