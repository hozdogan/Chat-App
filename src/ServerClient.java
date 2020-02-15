
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
public class ServerClient 
{
    public InetAddress adres;
    public int port;
    public String name;
    private String Id;

    
    public ServerClient(String name,InetAddress adres,int port ,String Id) {//final sa ya fonk içinde yada baslangıcda 1 değer atanır baska atanamaz
        this.name=name;
        this.adres=adres;
        this.port=port;
        this.Id=Id;
    }
    
    public String getSId()
    {
        return Id;
    }
    
    
    
}
