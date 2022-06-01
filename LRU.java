import java.util.Vector;;

public class LRU {

    private VirtualMemory memory = new VirtualMemory();
    private Vector<String> pageFaults = new Vector<>();
    private Vector<Vector<Integer>> matrix = new Vector<>();
    private Integer numberOfPF = 0;

    public LRU(VirtualMemory virMemory){
        memory = virMemory;
        numberOfPF = 1;
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

                Vector<Integer> colon = new Vector<>();

                for(int j = 0; j < tmp.size(); ++j){

                    if(tmp.elementAt(j) != memory.GetReferences().elementAt(i)){
                        colon.add(tmp.elementAt(j));
                    }
                }
                colon.add(memory.GetReferences().elementAt(i));

                tmp.removeAllElements();

                tmp.addAll(colon);
            }
            else{
                if(tmp.size() == memory.GetNumberOfFrames()){
                    tmp.removeElementAt(0);
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
