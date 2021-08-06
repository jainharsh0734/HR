import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.managers.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class DesignationModelTestCase extends JFrame
{
private DesignationModel designationModel;
private JTable table;
private JScrollPane jsp;
private Container container;
DesignationModelTestCase()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);

Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;
setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
DesignationModelTestCase d=new DesignationModelTestCase();
}
}