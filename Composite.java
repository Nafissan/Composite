public abstract class Composite{
    private String name;
    public abstract void afficher();
    public void ajout(Composite c){

    };
    public Composite(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
}