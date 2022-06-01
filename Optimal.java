import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Optimal {
    private VirtualMemory memory = new VirtualMemory();
    private Vector<String> pageFaults = new Vector<>();
    private Vector<Vector<Integer>> matrix = new Vector<>();
    private Integer numberOfPF = 0;

    public Optimal(VirtualMemory virMemory){
        memory = virMemory;
        numberOfPF = 1;
    }

    private Integer GetMaxDistance(HashMap<Integer, Integer> map){

        Integer distance = 0;
        Integer key = 0;

        for (Map.Entry<Integer, Integer> element : map.entrySet()){
            
            if(element.getValue() > distance){
                distance = element.getValue();
                key = element.getKey();
            }
        }

        return key;
    }
    public void StartSimulation(){
        Vector<Integer> firstElement = new Vector<>();
        firstElement.add(memory.GetReferences().elementAt(0));
        matrix.add(firstElement);
        pageFaults.add("PF");

        for(int i = 1; i < memory.GetNumberOfReferences(); i++){

            Vector<Integer> tmp = new Vector<>();
            tmp.addAll(matrix.elementAt(i-1));

            if(tmp.contains(memory.GetReferences().elementAt(i))){
                pageFaults.add("/ ");
            }
            else{
                if(tmp.size() == memory.GetNumberOfFrames()){

                    HashMap<Integer, Integer> distance = new HashMap<>();

                    for(int j = 0; j < tmp.size(); ++j){
                        Integer counter = 0;

                        int k = i;
                        while(k < memory.GetNumberOfReferences() && memory.GetReferences().elementAt(k) != tmp.elementAt(j)){
                            counter++;
                            k++;
                        }

                        distance.put(tmp.elementAt(j), counter);
                    }

                    Integer element = GetMaxDistance(distance);
                    tmp.removeElement(element);
                }
                
                tmp.add(memory.GetReferences().elementAt(i));
                pageFaults.add("PF");
                numberOfPF++;

            }

            matrix.add(tmp);
        }
    }

    public void PrintResult(){

        for(int i = 0; i < matrix.size(); ++i){

            System.out.print(memory.GetReferences().elementAt(i) + " " + pageFaults.elementAt(i) + "   ");

            for(int j = matrix.elementAt(i).size() - 1; j >= 0; --j){

                System.out.print(matrix.elementAt(i).elementAt(j) + " ");
            }

            System.out.print("\n");
        }

        double result = (numberOfPF.doubleValue() / memory.GetNumberOfReferences().doubleValue())*100;
        System.out.println("Efikasnost algoritma je: " + result + "%");
    }
}
