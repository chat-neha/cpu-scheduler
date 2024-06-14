package MiniProject;

import java.util.*;
import java.util.List;
import java.util.Queue;

class Process {
	int BurstTime;// burst time of the process
	String name;// process name
	int ArrivalTime;// arrival time
	int ct;// completion time
	int wt;// waiting time
	int priority;

	Process(int bt, String name, int pri, int at) {
//this.at=at;let's try to work with the clock
		BurstTime = bt;
		this.name = name;
		priority = pri;
		ArrivalTime = at;
//this.ct=ct;
//this.wt=wt;
	}
}

public class Algorithms {
		



		void bubbleSort(List<Process> processes, int num) {

			for (int i = 0; i < num - 1; i++) {

				for (int j = 0; j < num - i - 1; j++) {

					if (processes.get(j).priority > processes.get(j + 1).priority) {

// Swap the processes based on priority

						Process temp = processes.get(j);

						processes.set(j, processes.get(j + 1));

						processes.set(j + 1, temp);

					}

				}

			}

		}

		void nonPreemptivePriorityScheduling(ArrayList<Process> arr) {



		    // Create arrays to store burst time and priority for each process

		    int num = arr.size();

		    int[] burstTime = new int[num];

		    int[] priority = new int[num];



		    // Populate burstTime and priority arrays from the Process objects

		    for (int i = 0; i < num; i++) {

		        burstTime[i] = arr.get(i).BurstTime;

		        priority[i] = arr.get(i).priority;

		    }



		    // Use bubble sort to sort the priority values and keep corresponding burst times in sync

		    for (int i = 0; i < num - 1; i++) {

		        for (int j = 0; j < num - i - 1; j++) {

		            if (priority[j] > priority[j + 1]) {

		                // Swap priority values

		                int tempPriority = priority[j];

		                priority[j] = priority[j + 1];

		                priority[j + 1] = tempPriority;



		                // Swap burst times accordingly

		                int tempBurstTime = burstTime[j];

		                burstTime[j] = burstTime[j + 1];

		                burstTime[j + 1] = tempBurstTime;



		                // Swap corresponding Process objects in the 'arr' list

		                Process tempProcess = arr.get(j);

		                arr.set(j, arr.get(j + 1));

		                arr.set(j + 1, tempProcess);

		            }

		        }

		    }
		    // Initialize variables for waiting times and total waiting time

		    int waitTime = 0;

		    int[] waitTimes = new int[num];

		    System.out.println("Process\tBurst Time\tWaiting Time");

		    float totalWaitTime = 0;



		    for (int i = 0; i < num; i++) {

		        System.out.println(arr.get(i).name + "\t\t" + burstTime[i] + "\t\t" + waitTime);

		        waitTimes[i] = waitTime;

		        totalWaitTime += waitTime;

		        waitTime += burstTime[i];

		    }



		}



		public void FCFS(Queue<Process> readyQueue) {

			int currentTime = 0;

			int totalWait = 0;

			int num = 0;

			while (!readyQueue.isEmpty()) {

			Process currentProcess = readyQueue.remove();

			System.out.print(currentProcess.name + " ");

			num++;

			// Calculate waiting time for the current process

			int waitingTime = Math.max(0, (currentTime - currentProcess.ArrivalTime)) ;

			totalWait += waitingTime;

			for (int t = 0; t < currentProcess.BurstTime; t++) {

			System.out.print("-");

			currentTime++;

			}

			System.out.println();

			

			}

			}


			


		void SJF(ArrayList<Process> input, int num) {
			int clock = 0;// set clock to 0

			boolean CPU_Run = false;

			int exec = 0;// the current executing process burst time

			int prDone = 0;// num of proc. done

			int min = Integer.MAX_VALUE; // to fine min burst time

			int minIdx = 0;// indx of min burst time

		

// start clock

			ArrayList<Process> ready = new ArrayList<>();

			while (true) {

				if (prDone == num) {

					break;

				}

// add processes which arrive at current clock time in the ready queue

				for (int i = 0; i < num; i++) {

					if (clock == input.get(i).ArrivalTime) {

						ready.add(input.get(i));

					}

				}

				if (!CPU_Run) {

// find minimum burst time of all processes in the queue

					for (int i = 0; i < ready.size(); i++) {

						if (ready.get(i).ArrivalTime < min) {

							min = ready.get(i).BurstTime;

							minIdx = i;

						}

					}

// remove process

					if (!ready.isEmpty()) {

						exec = ready.get(minIdx).BurstTime;
						System.out.println("Process " + ready.get(minIdx).name);

						ready.remove(minIdx);

						CPU_Run = true;

						min = Integer.MAX_VALUE;

					}

				} else {

					if (exec == 0) {

						System.out.println();

						prDone++;

						CPU_Run = false;

					} else {

						exec--;

						System.out.print("-");

					}

				}

				clock++;

			}

		}

		void RoundRobin(Queue<Process> q)

	    {
			Queue<Process> rq=new LinkedList<>();//executing queue
		    int ft=4;//fixed time or time quantum
			if(!q.isEmpty()) {
	    		rq.add(q.remove());
			}
	    	int clk=0;

	    	Process running;

	    	while(!rq.isEmpty())

	    	{

	    		running=rq.peek();

	    		System.out.print("\nState of process "+running.name+":");

	    		for(int i=0;i<ft;i++)

	    		{

	    			System.out.print("* ");
	    			
                    clk=clk+10;

	    			running.BurstTime--;

	    			if(running.BurstTime==0)

	    			{

	    				running.ct=clk-running.ArrivalTime;
	    				
	    				//running.ct=MiniProject.trial.Main

	    				running.wt=running.ct-running.BurstTime;

	    				System.out.print("\nProcess "+running.name+":Execution complete "+"\n**********************************************************");

	    				rq.remove();

	    				break;

	    			}

	    		}

	    		if(!q.isEmpty()&&q.peek().ArrivalTime<clk)

	    		{

	    			rq.add(q.remove());

	    		}

	    		if(running.BurstTime==0)

	    			continue;

	    		rq.remove();

	    		rq.add(running);

	    	}

	    }
	}

