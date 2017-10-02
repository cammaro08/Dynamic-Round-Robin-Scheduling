import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

class DynamicRoundRobin {

  static Process p[];
  static ArrayList<Process> p1 = new ArrayList<Process>(0);
  static int index=0, timeCount, mainCount;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int sum = 0;
    int maxBurstTime;
    float timeQuantum=0;
    float avgWaitingTime = 0;
    float avgTurnaroundTime = 0;
    boolean flag = true;
    Vector<Integer> v = new Vector<Integer>();
    ArrayList<Process> p2;

    System.out.println("\nEnter the no. of processes: ");
    int no_of_proc = sc.nextInt();

    p = new Process[no_of_proc];
    for(int i=0; i<no_of_proc; i++) {
      p[i] = new Process(i);
    }

    for(int i=0; i<no_of_proc; i++) {
      System.out.println("\nEnter the Arrival Time and Burst Time of "+i+" process: ");
      p[i].setArrivalTime(sc.nextInt());
      p[i].setBurstTime(sc.nextInt());
      sum += p[i].getArrivalTime();
      v.add(i+1);
    }

    Arrays.sort(p);
    mainCount = p[0].getArrivalTime();

    for(timeCount=0; ; ) {
      checkForProcess(timeCount, index);

      p2 = (ArrayList<Process>)p1.clone();
      p2.sort(new Process());

      if(flag) {
        maxBurstTime = p2.get(p2.size()-1).getBurstTime();
        //System.out.println(maxBurstTime);
        timeQuantum = 0.8f * maxBurstTime;
        System.out.println("\n\nTime Quantum now: " + timeQuantum);
      }

      /*for(int i=0; i<p1.size(); i++)
        System.out.print((p1.get(i).getProcessNo()+1)+"\t");*/

      /*p1.get(0).setWaitingTime(timeCount-p1.get(0).getArrivalTime());
      timeCount += p1.get(0).getBurstTime();*/

      //System.out.println("\n");
      for(int i=0; i<p1.size(); i++) {
        if(p1.get(i).getBurstTime() <= timeQuantum && !p1.get(i).isFinish()) {
          System.out.print("Process finished: "+(p1.get(i).getProcessNo()+1)+"\n");
          p1.get(i).setWaitingTime(mainCount-p1.get(i).getArrivalTime());
          timeCount += p1.get(i).getBurstTime();
          mainCount += p1.get(i).getBurstTime();
          p1.get(i).setFinish(true);
          p1.get(i).setTurnaroundTime(mainCount-p1.get(i).getArrivalTime());
          v.removeElement(i+1);
        }
      }

      if(p1.size() == no_of_proc) {
        timeQuantum = p2.get(p2.size()-1).getBurstTime();
        System.out.println("Time Quantum now: " + timeQuantum);
        flag = false;
      }

      if(v.isEmpty()) {
        break;
      }
    }

    System.out.println("\n\nProcess\t\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
    for(int i=0; i<no_of_proc; i++) {
      System.out.println("   "+p[i].getProcessNo()+"\t\t   "+p[i].getArrivalTime()+"\t\t   "+p[i].getBurstTime()+"\t\t   "+p[i].getWaitingTime()+"\t\t  "+p[i].getTurnaroundTime());
      avgWaitingTime += p[i].getWaitingTime();
      avgTurnaroundTime += p[i].getTurnaroundTime();
    }

    avgWaitingTime /= no_of_proc;
    avgTurnaroundTime /= no_of_proc;
    System.out.println("\nAverage Waiting Time: "+avgWaitingTime);
    System.out.println("Average Turnaround Time: "+avgTurnaroundTime);

  }

  public static void checkForProcess(int time, int ind)
  {
    for(int j=ind; j<p.length; j++)
    {
      if(p[j].getArrivalTime() <= time)
      {
        p1.add(p[j]);
        index++;
      }
    }
    if(index > ind)
      return;
    timeCount++;
    return;
  }
}
