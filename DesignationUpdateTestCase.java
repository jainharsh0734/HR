import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exception.*;
class DesignationUpdateTestCase
{
public static void main(String gg[])
{
if(gg.length==0)
{
System.out.println("usage : addDesignationTestCase code title.");
return;
}
int code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("designation Updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}