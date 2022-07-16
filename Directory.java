import java.util.ArrayList;

public class Directory extends Composite
{
	private String nom;
	private ArrayList<Composite> composants = new ArrayList<Composite>();

	public Directory(String nom)
	{
		super(nom);
	}

	public void ajout(Composite composant)
	{
		this.composants.add(composant);
	}

	public void afficher()
	{
		System.out.println("├────" + getName());
		for (Composite c : this.composants) 
		{
			c.afficher();
		}
    }
}
//utiliser xml creer une fonction void pathXML(String path) lui donner un ensemble de dossier et fichiers et qu'il génère un fichier xml
//methode de main Document XmlToDoc(string) il crée des fichiers et dossiers en partant de la description en param dans path /il part de ca l'analyse et génère le code
//parseurXML donne représentation dom du doc et use getelmtbyid 
//qui evoque la méthode afficher dessus  /FSCapture   fast stone capture 