import java.io.*; 
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
  
public class Parcours 
{ 
  static void XmlToDoc(String path) {
    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
    try{
      DocumentBuilder builder=factory.newDocumentBuilder();
      Document document=builder.parse(new File(path));
      document.getDocumentElement().normalize();
      Directory dossier=null;
      NodeList dList=document.getElementsByTagName("fichier");
      for(int i=0;i<dList.getLength();i++){
        Node file=dList.item(i);
        if(file.getNodeType()==Node.ELEMENT_NODE){
          Element filElement=(Element) file;
          Files liste=new Files(filElement.getAttribute("name"));
          liste.afficher();
        }
      }
      NodeList docList=document.getElementsByTagName("directory");
      for(int i=0;i<docList.getLength();i++){
        Node directory=docList.item(i);
        if(directory.getNodeType()==Node.ELEMENT_NODE){
          Element dirElement=(Element) directory;
          dossier=new Directory(dirElement.getAttribute("name"));

          NodeList dirChild=directory.getChildNodes();
          for(int j=0;j<dirChild.getLength();j++){
            Node Child=dirChild.item(j);
            if(Child.getNodeType()==Node.ELEMENT_NODE){
              Element element=(Element) Child;
              if(element.getNodeName().equals("file")){
                Files fich=new Files(element.getAttribute("name"));
                dossier.ajout(fich);
              }else if(element.getNodeName().equals("directory")){
                Directory dic=new Directory(element.getAttribute("name"));
                dossier.ajout(dic);
              }
              
            }

          }
        }
        dossier.afficher();
      }
    }catch(ParserConfigurationException e){
      e.printStackTrace();
    }catch(SAXException e){
      e.printStackTrace();
    }catch(IOException e){
      e.printStackTrace();
    }  
  }
  static void pathXML(String path){
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
 
        // élément de racine
      Document doc = docBuilder.newDocument();
      Element racine = doc.createElement("arborescence");
      doc.appendChild(racine);
      File dir  = new File(path);
      File[] liste = dir.listFiles();
      String list;
      for(int i=0;i<liste.length;i++){
        if(liste[i].isFile())
        { 
          list=liste[i].getName().replace((i+1)+". ","");
          Element file = doc.createElement("fichier");
          racine.appendChild(file);
          Attr attr = doc.createAttribute("name");
          attr.setValue(list);
          file.setAttributeNode(attr);
        } 
        else if(liste[i].isDirectory())
        {
          Element directory = doc.createElement("directory");
          racine.appendChild(directory);
          Attr attr = doc.createAttribute("name");
          attr.setValue(liste[i].getName());
          directory.setAttributeNode(attr);
          Directory dic=new Directory(liste[i].getName());
          directoryChild(path,dic, doc,directory);
        } 
      }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult resultat = new StreamResult(new File("C://Users//etudiante//Documents//dic1//semestre 6//patron//composite//arbre//Arborescence.xml"));
 
        transformer.transform(source, resultat);
 
     } catch (ParserConfigurationException pce) {
         pce.printStackTrace();
     } catch (TransformerException tfe) {
         tfe.printStackTrace();
     }
  }
  static void directoryChild(String way,Directory doc,Document document,Element direct ){
    String fic=way+"\\"+doc.getName();
    File files=new File(fic);
    File[] fichier=files.listFiles();
    for(int i=0;i<fichier.length;i++){
      if(fichier[i].isFile())
      { 
        String list;
        list=fichier[i].getName().replace((i+1)+". ","");
        Element file = document.createElement("file");
        direct.appendChild(file);
        Attr attr = document.createAttribute("name");
        attr.setValue(list);
        file.setAttributeNode(attr);
      } 
      else if(fichier[i].isDirectory())
      {
        Element directory = document.createElement("directory");
        direct.appendChild(directory);
        Attr attr = document.createAttribute("name");
        attr.setValue(fichier[i].getName());
        directory.setAttributeNode(attr);
        Directory dico=new Directory(fichier[i].getName());
        directoryChild(way,dico,document,directory);
      } 
    }

  }
  public static void main(String[] args) throws IOException
  { 
    String way="C://Users//etudiante//Documents//dic1//semestre 6//hcia";
    pathXML(way);
    String path="Arborescence.xml";
    XmlToDoc(path);
  } 
}