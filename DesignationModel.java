package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exception.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.image.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private String[] columnTitle;
private DesignationManagerInterface designationManager;
public DesignationModel()
{
this.populateDataStructure();
}
private void populateDataStructure()
{
columnTitle=new String[2];
columnTitle[0]="S.No.";
columnTitle[1]="Designations";
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
//???????? what to do.
}
this.designations=new LinkedList<>();
Set<DesignationInterface> blDesignations=new TreeSet<>();
blDesignations=designationManager.getDesignations();
for(DesignationInterface blDesignation: blDesignations)
{
designations.add(blDesignation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
//application specific method.
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0; 
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation : "+designation.getTitle());
throw blException;
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.designations.get(index);
}


public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0; 
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation : "+title);
throw blException;
}
public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0; 
while(iterator.hasNext())
{
d=iterator.next();
if(d.getCode()==code) break;
index++;
}
if(index==designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid code : "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}

public void exportToPDF(File file) throws BLException
{
if(file.exists()) file.delete();
try
{
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);
PdfFont companyTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont titleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont numberPageFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont footerBoldFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont footerFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

Image logoImage=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logoImage);
Paragraph companyTitlePara=new Paragraph("  ABCD Carporations");
companyTitlePara.setFont(companyTitleFont);
companyTitlePara.setFontSize(20);

Paragraph reportTitlePara=new Paragraph("List of designations");
reportTitlePara.setFont(titleFont);
reportTitlePara.setFontSize(16);

Paragraph title1=new Paragraph("S.No.");
title1.setFont(titleFont);
title1.setFontSize(14);
Paragraph title2=new Paragraph("Designations");
title2.setFont(titleFont);
title2.setFontSize(14);


int pageSize=20;
int numberOfPages=this.designations.size()/pageSize;
if((this.designations.size()%pageSize)!=0) numberOfPages++;
int pageNumber=0;
int sNo=0;
String sNoString="";
int row=0;
boolean newPage=true;
float columnWidths[]={3,7};
Table topTable=null;
Table numberPageTable=null;
Table dataTable=null;
Table footerTable=null;
Paragraph cellPara;
Cell cell;

while(row<designations.size())
{
if(newPage)
{
topTable=new Table(UnitValue.createPercentArray(columnWidths));
cell=new Cell();
cell.add(logoImage);
cell.setBorder(Border.NO_BORDER);
topTable.addCell(cell);
cell=new Cell();
cell.add(companyTitlePara);
cell.setBorder(Border.NO_BORDER);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
document.add(topTable);

numberPageTable=new Table(1);
numberPageTable.setWidth(UnitValue.createPercentValue(100));
pageNumber++;
Paragraph numberPagePara=new Paragraph("Page : "+pageNumber+"/"+numberOfPages);
numberPagePara.setFont(numberPageFont);
numberPagePara.setFontSize(12);
cell=new Cell();
cell.add(numberPagePara);
cell.setBorder(Border.NO_BORDER);
cell.setTextAlignment(TextAlignment.RIGHT);
numberPageTable.addCell(cell);
document.add(numberPageTable);

dataTable=new Table(UnitValue.createPercentArray(columnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2).add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);

dataTable.addHeaderCell(new Cell().add(title1));
dataTable.addHeaderCell(new Cell().add(title2));

newPage=false;
}
sNo++;
sNoString=Integer.toString(sNo);
cellPara=new Paragraph(sNoString);
cellPara.setFont(dataFont);
cellPara.setFontSize(14);
cellPara.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(new Cell().add(cellPara));
cellPara=new Paragraph(this.designations.get(row).getTitle());
cellPara.setFont(dataFont);
cellPara.setFontSize(14);
dataTable.addCell(new Cell().add(cellPara));
if(sNo%pageSize==0 || sNo==this.designations.size())
{
document.add(dataTable);
Paragraph footerBoldPara=new Paragraph("Created by : ");
footerBoldPara.setFont(footerBoldFont);
footerBoldPara.setFontSize(14);
Paragraph footerPara=new Paragraph("Vishal Tikriya");
footerPara.setFont(footerFont);
footerPara.setFontSize(14);
footerTable=new Table(UnitValue.createPercentArray(columnWidths));
cell=new Cell();
cell.add(footerBoldPara);
cell.setBorder(Border.NO_BORDER);
footerTable.addCell(cell);
cell=new Cell();
cell.add(footerPara);
cell.setBorder(Border.NO_BORDER);
footerTable.addCell(cell);
document.add(footerTable);
if(sNo==this.designations.size())
{
document.close();
break;
}
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
row++;
}//loop ends here.
}catch(Exception e)
{
BLException blException=new BLException();
blException.setGenericException(e.getMessage());
throw blException;
}
}//export to Pdf method ends here.
}