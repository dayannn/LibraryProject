package libSource.Attributes;

import libSource.AttributeList;
import libSource.DataBaseWorker;

import java.util.List;

/**
 * Created by Антон on 16.05.2017.
 */
public class MainClass
{
    public static void main(String[] args)
    {
        try
        {
            DataBaseWorker worker = new DataBaseWorker();

            AttributeList lst = new AttributeList();
            AttributeName           atn = new AttributeName("");
            AttributeDescription    atd = new AttributeDescription("");
            AttributeLink           atl = new AttributeLink("");
            AttributeTheme          ath = new AttributeTheme("");
            AttributeType           atp = new AttributeType("");
            AttributeAccessType     atat = new AttributeAccessType("");
            AttributeKind           atk  = new AttributeKind("");
            lst.add(atn);
            lst.add(atd);
            lst.add(atl);
            lst.add(ath);
            lst.add(atp);
            lst.add(atat);
            lst.add(atk);

            List<AttributeList> lstRes = worker.getSomeResources(lst);
            lstRes = worker.simpleSearch(lst, "Имя1");
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
}
