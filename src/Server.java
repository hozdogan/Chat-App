
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import static java.lang.Math.random;
import java.util.*;
import javax.print.DocFlavor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class Server implements Runnable
{
    public List<ServerClient> clients = new ArrayList<ServerClient>();//mesaj geldiğinde client lara ekleyecek 
    
    private int port;
    private DatagramSocket datagramsock;
    private boolean running=false;
    private Thread run,manage,send,receive;
    public Server(int port)
    {
        this.port=port;
        try 
        {
            datagramsock=new DatagramSocket(port);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        run = new Thread(this);
        run.start();
    }
    public void run()
    {
        running=true;
        System.out.println("Server Started on port:"+port);
        receive();
        //sendToAll("Bu bir Sunucu Mesajıdır");hatalı olabilir ilerde kaldırılabilir
    }
    private void manageClients()
    {
        manage = new Thread("Manage"){
            public void run()
            {
                while(running);
            }
        };
        manage.start();
    }
     private void receive()
    {
        receive = new Thread("Receive"){
            public void run()
            {
                while(running)
                {
                    byte [] data = new byte[1024];
                    DatagramPacket dpacket = new DatagramPacket(data, data.length);
                    try
                    {
                        datagramsock.receive(dpacket);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    String str = new String(dpacket.getData());
                    processstring(dpacket);//isim sonra değişecek burdan belirlenmez tabi yada id ve ip port yaparsın isim gerekmez const a burda işlenen altta ekrana basılıyor
                    if(str.startsWith("/clientname"))
                    {
                        System.out.println("receive data from "+dpacket.getAddress().toString()+" "+dpacket.getPort()+" "+str.substring(11,str.length()));//str de istemciden gelen mesaj ınetadress adres+port formunda
                    }
                    else if(str.startsWith("/n/"))
                    {
                        System.out.println(str.substring(3,str.length()));
                    }
                    //str de istemciden gelen mesaj ınetadress adres+port formunda
                }
            }
        };
        receive.start();
    }
      private void send(final byte[] data,final InetAddress adress,final int port)
     {
         send = new Thread("send")
         {
           public void run()
           {
               DatagramPacket packet = new DatagramPacket(data, data.length, adress, port);
               
               try
               {
                   datagramsock.send(packet);
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
           }
           
         };
         send.start();
     }
     private void sendToAll(String message)
     {
         for(int i=0;i<clients.size();i++)
         {
            ServerClient istemci =clients.get(i);
            send(message.getBytes(),istemci.adres,istemci.port);
         }
     }
     private void disconnect(String str)
     {
         String name,id,mes;
         int count=0;
         
         //System.out.println(clients.get(0).getSId()+" "+str);//doğru ananı sikim çalışmıyo aq
         /*boolean val=(clients.get(0).getSId()).equals(str);
         System.out.println(val);*/
        
         for(int i=0;i<clients.size();i++)
         {
             String stri=clients.get(i).getSId();
             for(int j=0;j<stri.length();j++)
             {
                 if(stri.charAt(j)==str.charAt(j))
                     count++;
             }
             
             if(count==stri.length()) //sonunda bosluk olmadığı için eşitlemiyor
             {
                 name=clients.get(i).name;
                 id=clients.get(i).getSId();
                 String message="User "+name+" have spesificed id "+id+" Disconnected!";
                 mes=name+" disconnected";
                 System.out.println(message);
                 sendToAll("/s/"+mes);
                 clients.remove(i);//o listeden bu id li olan clienti kaldır hatta o cıkan kullanıcının name ini alıp tüm clientlara bildirelim
                 break;
             }
         }
         count=0;
       
    }
     
     private void processstring(DatagramPacket data)
     {
        String str = new String(data.getData());
        int id =new UniqueIdentifier().getId();
         //yoksa const calısmaz 
         String idstr ="/i/"+String.valueOf(id);
         if(str.startsWith("/clientname"))
         {
             clients.add(new ServerClient(str.substring(11,str.length()),data.getAddress(), data.getPort(),String.valueOf(id)));
             send(idstr.getBytes(),data.getAddress(),data.getPort());//ıd yi client e gönderiyoruz
         }
         else if(str.startsWith("/n/"))//tum clientlere gönder
         {
             sendToAll(str);
         }
         else if(str.startsWith("/d/"))
         {
             String idd=str.substring(3,str.length());//bölmeden deneyelim belki öyle olur string hep sayı içerdiği için oluyor bunlar
             disconnect(idd);
             
         }
         else
         {
             return;//yoksa istemci listesine ekleme mesajdır zaten ekranda yazdır
         }
         
     }
    
}
