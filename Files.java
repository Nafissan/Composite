 

public class Files extends Composite{
    private String name;

    public Files(String name){
        super(name);
    }

    public void ajout(Composite nom){}

    public void afficher()
	{
		System.out.println("│     "+ getName());
	}
}