import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exception.*;
class DesignationAddTestCase
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
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println(title+" added with code "+designationDTO.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}