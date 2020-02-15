
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class UniqueIdentifier {
    
    private static List<Integer> ids = new ArrayList<Integer>();//liste ürettik herkese karışık veriyor
    private final int RANGE=10000;
    private static int index=0;
    
    
    public UniqueIdentifier(){
        for(int i=0;i<RANGE;i++)
        {
            ids.add(i);
        }
        Collections.shuffle(ids);
    }
    
    
    public int getId()
    {
        if(index>ids.size()-1)
        {
            index=0;
        }
        return ids.get(index++);
    }
    
    
    
}
