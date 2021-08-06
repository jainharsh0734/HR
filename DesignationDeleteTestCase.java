import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exception.*;
class DesignationDeleteTestCase
{
public static void main(String gg[])
{
if(gg.length==0)
{
System.out.println("usage : DeleteDesignationTestCase code");
return;
}
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("Designaiton deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}