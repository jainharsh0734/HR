package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exception.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JTextField searchTextField;
private JButton searchClearButton; 
private DesignationModel designationModel;
private JTable designationTable;
private JScrollPane scrollPane;
private DesignationPanel designationPanel;
private Container container;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon cancelIcon;
private ImageIcon deleteIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon clearIcon;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
this.setViewMode();
designationPanel.setViewMode();
}
public void initComponents()
{
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
addIcon=new ImageIcon(this.getClass().getResource("/icons/add.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save.png"));
clearIcon=new ImageIcon(this.getClass().getResource("/icons/clear.png"));
setIconImage(logoIcon.getImage());
designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
searchClearButton=new JButton(clearIcon);
searchErrorLabel=new JLabel("");
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
designationPanel=new DesignationPanel();
container=getContentPane();
}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(30);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader tableHeader=designationTable.getTableHeader();
tableHeader.setFont(columnHeaderFont);
tableHeader.setReorderingAllowed(false);
tableHeader.setResizingAllowed(false);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

int lm,tm;
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
searchErrorLabel.setBounds(lm+10+100+400-65,tm+10+40-10,100,20);
searchClearButton.setBounds(lm+10+100+5+400,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,565,200);

container.setLayout(null);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(searchErrorLabel);
container.add(searchClearButton);
container.add(scrollPane);
container.add(designationPanel);

Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=660;
setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
searchClearButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
private void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
searchClearButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
searchClearButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
searchClearButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
searchClearButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
searchClearButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
searchClearButton.setEnabled(false);
designationTable.setEnabled(false);
}
//inner class starts.
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JLabel titleErrorLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(130,130,130)));
initComponents();
setAppearance();
addListeners();
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleErrorLabel.setVisible(false);
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleErrorLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton(clearIcon);
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font titleErrorFont=new Font("Verdana",Font.BOLD,12);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleErrorLabel.setFont(titleErrorFont);
titleErrorLabel.setForeground(Color.red);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+10+110+10,tm+20,400,30);
titleErrorLabel.setBounds(lm+10+110+10,tm+20,400,30);
titleTextField.setBounds(lm+10+110+10,tm+20,400,30);
clearTitleTextFieldButton.setBounds(lm+10+110+10+400,tm+20,30,30);

buttonsPanel.setBounds(50,tm+20+30+30,465,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(130,130,130)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(clearTitleTextFieldButton);
add(buttonsPanel);
}
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Title required.");
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,"Designation "+blException.getException("title"));
}
return false;
}
return true;
}
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Title required.");
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,"Designation "+blException.getException("title"));
}
return false;
}
return true;
}
private void removeDesignation()
{
String title=this.designation.getTitle();
int result=JOptionPane.showConfirmDialog(this,title+" delete?","Confirmation",JOptionPane.YES_NO_OPTION);
if(result==JOptionPane.NO_OPTION) return;
try
{
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted");
//this.clearDesignation();
}catch(BLException blException)
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
}
void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation())
{
setViewMode();
}
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
updateDesignation();
setViewMode();
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
this.clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
titleTextField.requestFocus();
}
});

this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
setViewMode();
}
});

this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
jfc.setAcceptAllFileFilterUsed(false);
jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.isDirectory()) return true;
if(file.getName().endsWith(".pdf")) return true;
return false;
}
public String getDescription()
{
return "pdf files";
}
});
int result=jfc.showSaveDialog(DesignationUI.this);
if(result==JFileChooser.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parentFile=new File(file.getParent());
if(parentFile.exists()==false || parentFile.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path : "+file.getAbsolutePath());
return;
}
if(file.exists())
{
int selectedOption=JOptionPane.showConfirmDialog(DesignationUI.this,"Do you want to override : "+file.getName(),"Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION)
{
return;
}
}
try
{
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to PDF : "+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException()) JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
return;
}
}//APPROVE_OPTION if part ends here.
}
});

}
private void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.titleLabel.setVisible(true);
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
private void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleTextField.setVisible(true);
this.titleTextField.requestFocus();
this.titleLabel.setVisible(false);
this.clearTitleTextFieldButton.setVisible(true);
addButton.setIcon(saveIcon);
addButton.setEnabled(true);
cancelButton.setEnabled(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
private void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit.");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleTextField.setVisible(true);
this.titleLabel.setVisible(false);
this.clearTitleTextFieldButton.setVisible(true);
editButton.setIcon(saveIcon);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
editButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
private void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete.");
return;
}
DesignationUI.this.setDeleteMode();
this.titleLabel.setVisible(true);
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
removeDesignation();
}
private void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}//inner class ends here.
}