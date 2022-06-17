import java.util.HashMap;
import java.util.Map;
import java.util.Vector;;

public class LFU {
    private VirtualMemory memory = new VirtualMemory();
    private Vector<String> pageFaults = new Vector<>();
    private Vector<Vector<Integer>> matrix = new Vector<>();
    private Integer numberOfPF = 0;
    private HashMap<Integer, Integer> mapOfPages = new HashMap<>();

    public LFU(VirtualMemory virMemory){
        memory = virMemory;
        numberOfPF = 1;

        for(var element : memory.GetReferences()){
            mapOfPages.put(element, 10);
        }
    }

    private Integer GetMinKey(Vector<Integer> keys){

        Integer key = keys.elementAt(0);
        Integer min = mapOfPages.get(key);

        for(var element : keys){
            
            if(min > mapOfPages.get(element)){

                min = mapOfPages.get(element);
                key = element;
            }
        }

        return key;
    }

    private void UpdateMap(Integer page){

        for (Map.Entry<Integer, Integer> element : mapOfPages.entrySet()) {
            
            if(element.getKey() != page){
                Integer value = element.getValue();
                element.setValue(value - 1);
            }
        }
    }

    private void SortColon(Vector<Integer> colon){

        for(int i = 0; i < colon.size() - 1; i++){
            for(int j = i + 1; j < colon.size(); j++){

                if(mapOfPages.get(colon.elementAt(i)) > mapOfPages.get(colon.elementAt(j))){
                    Integer tmp1 = colon.elementAt(i);
                    Integer tmp2 = colon.elementAt(j);
                    colon.remove(j);
                    colon.add(j, tmp1);
                    colon.remove(i);
                    colon.add(i, tmp2);
    
                }
            }   
        }
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

                Integer value = mapOfPages.get(memory.GetReferences().elementAt(i));
                mapOfPages.put(memory.GetReferences().elementAt(i), value + 4);
            }
            else{
                if(tmp.size() == memory.GetNumberOfFrames()){
                    Integer min = GetMinKey(tmp);
                    tmp.remove(min);
                }

                mapOfPages.put(memory.GetReferences().elementAt(i), 10);
                tmp.add(memory.GetReferences().elementAt(i));
                pageFaults.add("PF");
                numberOfPF++;

            }

            UpdateMap(memory.GetReferences().elementAt(i));
            SortColon(tmp);
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
