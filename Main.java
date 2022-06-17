import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);


        System.out.println("Unesite broj okvira: ");
        Integer numberOfFrames = scanner.nextInt();

        System.out.println("Unesite broj referenci: ");
        Integer numberOfPages = scanner.nextInt();

        VirtualMemory memory = new VirtualMemory(numberOfPages, numberOfFrames);
        System.out.println("Unesite reference: ");
        for(int i = 0; i < numberOfPages; ++i){
            Integer page = scanner.nextInt();
            memory.AddReference(page);
        }

        System.out.println("Unesite koliko algoritama zelite koristiti[1-5]: ");
        Integer numberOfAlgorithms = scanner.nextInt();

        Vector<String> algorithms = new Vector<>();

        if(numberOfAlgorithms > 0 && numberOfAlgorithms < 6){

            System.out.println("Unesite koje algoritme zelite koristiti [FIFO] [LRU] [LFU] [SC] [OP]: ");
            for(int i = 0; i < numberOfAlgorithms; ++i){
                String alg = scanner.next();
                algorithms.add(alg);
            }
    
            for(var element : algorithms){
    
                switch(element){
    
                    case "FIFO":
    
                    FIFO fifo = new FIFO(memory);
                    fifo.StartSimulation();
                    System.out.println("Rezultati FIFO simulacije: ");
                    fifo.PrintResult();
                    break;
    
                    case "LFU":
    
                    LFU lfu = new LFU(memory);
                    lfu.StartSimulation();
                    System.out.println("Rezultati LFU simulacije: ");
                    lfu.PrintResult();
                    break;
    
                    case "LRU":
    
                    LRU lru = new LRU(memory);
                    lru.StartSimulation();
                    System.out.println("Rezultati LRU simulacije: ");
                    lru.PrintResult();
                    break;
    
                    case "SC":
    
                    System.out.println("Unesite referencu sa R-bitom: ");
    
                    Integer Rpage = scanner.nextInt();
    
                    if(memory.GetReferences().contains(Rpage)){
                        SecondChance sc = new SecondChance(memory, Rpage);
                        sc.StartSimulation();
                        System.out.println("Rezultati Second Chance simulacije: ");
                        sc.PrintResult();
                    }
    
                    else System.out.println("Nije moguce postaviti R-bit na odabranu referencu");
                    break;
    
                    case "OP":
    
                    Optimal optimal = new Optimal(memory);
                    optimal.StartSimulation();
                    System.out.println("Rezultati OP simulacije: ");
                    optimal.PrintResult();
                    break;
    
                    default: System.out.println("Pogresno unesen naziv algoritma!");
                }
            }
        }
        else System.out.println("Potrebno je izabrati najmanje 1 algoritam, a najvise 5");

        scanner.close();
    }
}
