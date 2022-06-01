import java.util.Vector;

public class VirtualMemory{

    private Integer numberOfReferences;
    private Integer numberOfFrames;
    private Vector<Integer> references = new Vector<>();

    public VirtualMemory(Integer numberOfReferences, Integer numberOfFrameworks){
        this.numberOfFrames = numberOfFrameworks;
        this.numberOfReferences = numberOfReferences;
    }

    public VirtualMemory(){
        numberOfReferences = 0;
        numberOfFrames = 0;
    }

    public void AddReference(Integer number){
        references.add(number);
    }

    public Integer GetNumberOfReferences(){
        return numberOfReferences;
    }

    public Integer GetNumberOfFrames(){
        return numberOfFrames;
    }

    public Vector<Integer> GetReferences(){
        return references;
    }
}