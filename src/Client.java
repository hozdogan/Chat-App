
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class Client{

    private static String name,adress;
    private static int port;
    private DatagramSocket datagramsocket;
    private InetAddress ip;
    private Thread sendproc;//gönderme thread le yapılacakki çakışma olmasın mesaj kaybolmasın
    private String ID;//int calismadi string yanina int koyarım
    
    public Client(String name,String adress,int port)
    {
        this.adress=adress;
        this.name=name;
        this.port=port;
        
    }
    public String getAdress(){
        return adress;
    }
    public String getName()
    {
        return name;
    }
    public int getPort()
    {
        return port;
    }
    public void SetID(String id)
    {
        this.ID=id;
    }
    public String getSIID()
    {
        return ID;
    }
    public boolean openConnection(String adress)
    {
        try
        {
            datagramsocket = new DatagramSocket();
            ip=InetAddress.getByName(adress);//ismi ip ye dönusturuyor sonradan bind yapacak socketi direk açtı
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
            
            
    }
    public void close()
    {
        new Thread()
        {
            public void run()
            {
                synchronized(datagramsocket){
                    datagramsocket.close();
        }
            }
        }.start();
        
        
    }
    public String receive()//veriler byte byte geldiğ için alıp stringe dönüştürüyoruz 
    {
        byte []data = new byte[1024];
        DatagramPacket datapacket = new DatagramPacket(data, data.length);//veri ve uzunluğu ağlar kitabındaki gibi kaç byte okunacak 1 kb gelecek
        try
        {
            datagramsocket.receive(datapacket);//ilişkili sınıf datagram packet veri almak için onun nesnesini içeren socket in receive fonk var
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        String message =new String(datapacket.getData());
        return message;
    }
    public void sendData(final byte[]data)
    {
        sendproc = new Thread()//süreci olustur kos
        {
          public void run()
          {
              DatagramPacket datapack = new DatagramPacket(data, data.length,ip,port);//sunucunun listen yaptıgı port o veri gonderirken bu adresten bu porta veri geldi çekerkende packet.getport packet gelen port ok 
              try
              {
                  datagramsocket.send(datapack);
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
          }
        };
        sendproc.start();
    }
    
   
}