import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exception.*;
class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
if(gg.length==0)
{
System.out.println("usage : addDesignationTestCase title.");
return;
}
String title=gg[0];
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
System.out.println(title+" exists : "+designationDAO.titleExists(title));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}