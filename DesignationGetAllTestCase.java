import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exception.*;
import java.util.*;
class DesignationGetAllTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
Set<DesignationDTOInterface> designations;
designations=designationDAO.getAll();
designations.forEach((designationDTO)->{
System.out.println("code : "+designationDTO.getCode()+",Title : "+designationDTO.getTitle());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}