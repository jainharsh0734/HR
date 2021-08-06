import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;
class GetCountDesignationTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
System.out.println("Designation count is : "+designationDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}