package MiniProject;

import java.util.*;

import MiniProject.Process;

import MiniProject.Algorithms;

import java.time.*;

class Node1 {

	static Scanner sc = new Scanner(System.in);

	String path;

	String name;

	String type;// file folder

	int level;

	ArrayList<Node1> childlist;

	Node1() {

		this.name = "";

		this.type = "";

	}

	Node1(String name, String type) {

		this.name = name;

		this.type = type;

		path = "";
	}

}

class directory1 {

	Node1 root;

	Scanner sc = new Scanner(System.in);

	directory1() {

		root = new Node1("ROOT", "folder");

		root.level = 0;

		root.childlist = null;

		root.path = "\\ROOT";

	}

	void add(Node1 temp) {

		System.out.println("Adding node " + temp.name + "....");

		if (temp.type.toLowerCase().equals("folder"))// create childlist for temp only if it is a folder

		{

			temp.childlist = new ArrayList<>();

		}

		Node1 ptr = root;

		int incLevel = 0;


		while (true) {

			if (ptr.type.toLowerCase().equals("file")) {

				System.out.println("Cannot add into a file!");
				
				return;

			}

// check if there exists sub folders

			if (ptr.childlist == null) {

// if no sub folders, add into root folder

				ptr.childlist = new ArrayList<>();

				incLevel++;

				temp.level = incLevel;

				ptr.childlist.add(temp);

				temp.path = temp.path+"\\" +ptr.name;
								
				System.out.println("Node '" + temp.name + "' added in "+ptr.name+"!");

				return;

			}

// if sub folders exist ask if user wants to add into root or sub folder

			System.out.println("Do you want to add in " + ptr.name + "? Enter 1 for yes , 0 for no");

			temp.path = temp.path + "\\" + ptr.name;
			
			int ch = sc.nextInt();

			sc.nextLine();

			incLevel++;

			if (ch == 1) {

// if root, add to root

				temp.level = incLevel;

				ptr.childlist.add(temp);

				System.out.println("Node added to " + ptr.name);
				
				
				return;

			}

// else print all sub folders and ask for name of folder to add into

			System.out.println("Following are the contents of " + ptr.name + ": ");

			for (int i = 0; i < ptr.childlist.size(); i++) {

				System.out.println(ptr.childlist.get(i).name + " (" + ptr.childlist.get(i).type + ") ");

			}

			System.out.println("Enter folder to add into:");

			String name = sc.nextLine();

			boolean flag = false;

// search the sub folder and repeat the process

			for (int i = 0; i < ptr.childlist.size(); i++) {

				if (name.equals(ptr.childlist.get(i).name)) {

					if (ptr.childlist.get(i).type.toLowerCase().equals("file")) {

						System.out.println("Cannot add into a file!");
						
						temp.path = "";

						return;

					}

					flag = true;

					Node1 parent = ptr.childlist.get(i);

					if (parent.childlist == null) {

						System.out.println("Adding into " + parent.name);

						parent.childlist = new ArrayList<>();

						incLevel++;

						temp.level = incLevel;

						parent.childlist.add(temp);

						temp.path = temp.path + "\\" + parent.name;

						System.out.println("Node added!");

						return;

					} else {
						
						
						ptr = parent;

						break;

					}

				}

			}

// folder name entered does not match any existing folder

			if (!flag) {

				System.out.println("Folder does not exist!!");

				return;

			}

		}

	}

	void displayDF(Node1 root) {

		if (root == null) {

			return;

		}

		Stack<Node1> st = new Stack<>();

		Node1 ptr = root;

		st.add(root);

		while (!st.isEmpty()) {

			ptr = st.pop();

			for (int i = 0; i <= ptr.level; i++) {

				System.out.print(" ");

			}

			System.out.println(ptr.name + " (" + ptr.type + ")");

			if (ptr.childlist != null) {

				for (int i = ptr.childlist.size() - 1; i >= 0; i--) {

					st.add(ptr.childlist.get(i));

				}

			}

		}

	}

	void search(Node1 root) {

		System.out.println("Would you like to search a file or a folder?");

		String type = sc.nextLine();
		boolean flag = false;

		while (!flag) {
			if (type.toLowerCase().equals("file") || type.toLowerCase().equals("folder")) {
				flag = true;
				break;
			}
			System.out.println("Enter correct type: file or folder!:");
			type = sc.nextLine();
		}

		System.out.println("Enter name of file/folder:");

		String name = sc.next();

		sc.nextLine();

		if (root == null) {

			System.out.println("Directory is empty.");

			return;

		}

		Node1 result = searchNode(root, name);
		if (result == null) {
			System.out.println("File/folder not found!");
			return;
		} else {
			System.out.println("File/folder found!");
			System.out.println("Path:" + result.path + "\\" + result.name);
		}
	}

	Node1 searchNode(Node1 root, String name) {
		if (root == null) {
			return null;
		}
		if (root.name.equals(name)) {
			return root;
		}
		if (root.childlist != null) {
			for (Node1 child : root.childlist) {
				Node1 result = searchNode(child, name);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	void delete(Node1 root) {
		System.out.println("Would you like to delete a file or a folder?");
		String type = sc.nextLine();
		boolean flag = false;

		while (!flag) {
			if (type.toLowerCase().equals("file") || type.toLowerCase().equals("folder")) {
				flag = true;
				break;
			}
			System.out.println("Enter correct type: file or folder!:");
			type = sc.nextLine();
		}

		if (type.equals("folder")) {
			System.out.println("All contents of your folder will be lost. Do you still wish to continue? (1/0)");
			int del = sc.nextInt();

			if (del == 0) {
				System.out.println("Returning to the menu");
				return;
			}
		}

		System.out.println("Enter name of file/folder:");
		String name = sc.next();
		sc.nextLine();

		if (root == null) {
			System.out.println("Directory does not exist.");
			return;
		}

		if (root.name.equals(name) && root.type.equals(type)) {
			System.out.println("Cannot delete the root folder.");
			return;
		}

		Stack<Node1> stack = new Stack<>();
		stack.push(root);

		while (!stack.isEmpty()) {
			Node1 currentNode = stack.pop();

			if (currentNode.childlist != null) {
				for (int i = 0; i < currentNode.childlist.size(); i++) {
					Node1 child = currentNode.childlist.get(i);
					if (child.name.equals(name) && child.type.equals(type)) {
						currentNode.childlist.remove(i);
						System.out.println(name + " of type " + type + " deleted.");
						return;
					}

					if (child.type.equals("folder")) {
						stack.push(child);
					}
				}
			}
		}

		System.out.println("File or folder not found.");
	}

	void open(ArrayList<Node1> open) {
		String type = "file";

		System.out.println("Enter name of the file you want to open");

		String name = sc.next();

		sc.nextLine();

		if (root == null) {

			System.out.println("Directory is empty.");

			return;

		}

		Stack<Node1> st = new Stack<>();

		StringBuffer sb = new StringBuffer();

		Node1 ptr = root;

		st.push(root);

		boolean flag = false;

		while (!st.isEmpty()) {

			ptr = st.pop();

			sb.append(ptr.name);
			sb.append("/");

			if (ptr.name.equals(name)) {

				if (ptr.type.equals("file")) {

					System.out.println(ptr.name + " is now open");
					open.add(ptr);
					flag = true;

					return;
				}

			}

			if (ptr.childlist != null) {

				for (int i = ptr.childlist.size() - 1; i >= 0; i--) {

					st.push(ptr.childlist.get(i));

				}

			}

		}

		if (flag == false) {

			System.out.println("File not found");

			return;

		}
	}

	void opendisp(ArrayList<Node1> open) {
		System.out.println("Files and folders open at the moment are:");
		if(open.size()==0) {
			System.out.println("No files open!");
		}
		for (int i = 0; i < open.size(); i++) {
			System.out.println(open.get(i).name);
		}
		
	}

	void close(ArrayList<Node1> open) {
		System.out.println("Enter the name of the file you want to close");
		String cl = sc.nextLine();
		for (int i = 0; i < open.size(); i++) {
			if (open.get(i).name.equals(cl)) {
				System.out.println("File " + open.get(i).name + " closed");
				open.remove(i);
				return;
			}

		}
		System.out.println("File not found!");
	}

}

public class trial {
	int startTime = (int) System.currentTimeMillis();

	public static void main(String[] args) {

		trial t = new trial();
		// int startTime = (int) System.currentTimeMillis();

		Scanner s = new Scanner(System.in);

		directory1 d = new directory1();

		ArrayList<Node1> open = new ArrayList<>();

		boolean cont = true;

		ArrayList<Process> input = new ArrayList<>();

		Queue<Process> q = new LinkedList<>();

		Queue<Process> readyQueue = new LinkedList<>();
		
		

//		Process cr = new Process(5, "create", 2, 350);
//		Process dis = new Process(7, "display", 5, 160);
//		Process src = new Process(5, "search", 6, 200);
//		Process del = new Process(9, "delete", 8, 100);
//		Process opn = new Process(3, "open", 1, 250);
//		Process cls = new Process(2, "close", 3, 115);
//		Process diso = new Process(4, "display_open", 9, 175);

		Algorithms a = new Algorithms();

		do {

			System.out.println("--------MENU----------");

			System.out.println("Welcome to your directory!!");

			System.out.println("What do you want to do?");

			System.out.println(
					"1.Create and add a file or folder\n2.Display directory\n3.Search for a folder or file\n4.Delete a file or folder\n5.Open a file\n6.Close a file\n7.Display all the open files\n8.Exit");

// search , delete, display absolute path , open a file or folder , close file

			System.out.println("------------------------");

			int ch = s.nextInt();

			s.nextLine();

			switch (ch) {

			case 1:
				int curTime1 = (int) System.currentTimeMillis();
				int arrTime1 = (curTime1 - t.startTime) / 1000;
				System.out.println("Arrival time:"+arrTime1);
				System.out.println("------CREATION-------");

				System.out.println("Do you want to create a file or folder? enter:");

				String type = s.nextLine();
				boolean flag = false;
				if (type.toLowerCase().equals("file") || type.toLowerCase().equals("folder")) {
					flag = true;
				}
				while (!flag) {
					if (type.toLowerCase().equals("file") || type.toLowerCase().equals("folder")) {
						flag = true;
						break;
					}
					System.out.println("Enter correct type: file or folder!:");
					type = s.nextLine();
				}

				System.out.println("Enter name of " + type + ": ");

				String name = s.nextLine();

				Node1 newNode = new Node1(name, type);

				d.add(newNode);

				System.out.println("Directory is:");

				d.displayDF(d.root);

//r.q.add(cr);
				Process cr = new Process(20, "create", 2, arrTime1);
				input.add(cr);

				readyQueue.add(cr);

				q.add(cr);

				break;

			case 2:

				int curTime2 = (int) System.currentTimeMillis();

				int arrTime2 = (curTime2 - t.startTime) / 1000;
				System.out.println("Arrival time:"+arrTime2);

				System.out.println("Directory is:");

				d.displayDF(d.root);

//r.q.add(dis);
				Process dis = new Process(25, "display", 5, arrTime2);

				input.add(dis);

				readyQueue.add(dis);

				q.add(dis);

				break;

			case 3:
				int curTime3 = (int) System.currentTimeMillis();

				int arrTime3 = (curTime3 - t.startTime) / 1000;
				
				System.out.println("Arrival time:"+arrTime3);

				d.search(d.root);
//r.q.add(src);
				Process src = new Process(16, "search", 1, arrTime3);

				input.add(src);

				readyQueue.add(src);

				q.add(src);

				break;

			case 4:
				int curTime4 = (int) System.currentTimeMillis();

				int arrTime4 = (curTime4 - t.startTime) / 1000;
				
				System.out.println("Arrival time:"+arrTime4);


				d.delete(d.root);

//r.q.add(del);
				Process del = new Process(30, "delete", 7, arrTime4);

				input.add(del);

				readyQueue.add(del);

				q.add(del);

				break;

			case 5:
				int curTime5 = (int) System.currentTimeMillis();

				int arrTime5 = (curTime5 - t.startTime) / 1000;
				d.open(open);
				System.out.println("Arrival time:"+arrTime5);

//r.q.add(opn);

				Process opn = new Process(10, "open", 0, arrTime5);

				input.add(opn);

				readyQueue.add(opn);

				q.add(opn);

				break;

			case 6:
				int curTime6 = (int) System.currentTimeMillis();

				int arrTime6 = (curTime6 - t.startTime) / 1000;
				
				System.out.println("Arrival time:"+arrTime6);

				
				d.close(open);
//r.q.add(cls);

				Process cls = new Process(17, "close", 9, arrTime6);

				input.add(cls);

				readyQueue.add(cls);

				q.add(cls);

				break;

			case 7:
				int curTime7 = (int) System.currentTimeMillis();

				int arrTime7 = (curTime7 - t.startTime) / 1000;

				System.out.println("Arrival time:"+arrTime7);
				
				d.opendisp(open);

				// r.q.add(diso);
				Process diso = new Process(19, "display_open", 11, curTime7);

				input.add(diso);

				readyQueue.add(diso);

				q.add(diso);

				break;

			case 8:

				cont = false;

				System.out.println("Bye bye!");

				break;

			}

		} while (cont);

//nonPreemptivePriorityScheduling(L)

		System.out.println("FCFS Output:");
		a.FCFS(readyQueue);
		System.out.println("SJF Output:");
		a.SJF(input, input.size());
		System.out.println("Priority Scheduling Output: ");
		a.nonPreemptivePriorityScheduling(input);
		System.out.println("Round Robin Output:");
		a.RoundRobin(q);

	}
}
/* OUTPUT
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
1
Arrival time:0
------CREATION-------
Do you want to create a file or folder? enter:
folder
Enter name of folder: 
A
Adding node A....
Node 'A' added in ROOT!
Directory is:
 ROOT (folder)
  A (folder)
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
1
Arrival time:5
------CREATION-------
Do you want to create a file or folder? enter:
folder
Enter name of folder: 
B
Adding node B....
Do you want to add in ROOT? Enter 1 for yes , 0 for no
1
Node added to ROOT
Directory is:
 ROOT (folder)
  A (folder)
  B (folder)
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
1
Arrival time:10
------CREATION-------
Do you want to create a file or folder? enter:
folder
Enter name of folder: 
a1
Adding node a1....
Do you want to add in ROOT? Enter 1 for yes , 0 for no
0
Following are the contents of ROOT: 
A (folder) 
B (folder) 
Enter folder to add into:
A
Do you want to add in A? Enter 1 for yes , 0 for no
1
Node added to A
Directory is:
 ROOT (folder)
  A (folder)
   a1 (folder)
  B (folder)
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
1
Arrival time:17
------CREATION-------
Do you want to create a file or folder? enter:
file
Enter name of file: 
1
Adding node 1....
Do you want to add in ROOT? Enter 1 for yes , 0 for no
0
Following are the contents of ROOT: 
A (folder) 
B (folder) 
Enter folder to add into:
A
Do you want to add in A? Enter 1 for yes , 0 for no
0
Following are the contents of A: 
a1 (folder) 
Enter folder to add into:
a1
Do you want to add in a1? Enter 1 for yes , 0 for no
1
Node added to a1
Directory is:
 ROOT (folder)
  A (folder)
   a1 (folder)
    1 (file)
  B (folder)
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
2
Arrival time:25
Directory is:
 ROOT (folder)
  A (folder)
   a1 (folder)
    1 (file)
  B (folder)
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
3
Arrival time:26
Would you like to search a file or a folder?
file
Enter name of file/folder:
1
File/folder found!
Path:\ROOT\A\a1\1
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
5
Enter name of the file you want to open
1
1 is now open
Arrival time:31
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
7
Arrival time:33
Files and folders open at the moment are:
1
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
6
Arrival time:34
Enter the name of the file you want to close
1
File 1 closed
--------MENU----------
Welcome to your directory!!
What do you want to do?
1.Create and add a file or folder
2.Display directory
3.Search for a folder or file
4.Delete a file or folder
5.Open a file
6.Close a file
7.Display all the open files
8.Exit
------------------------
8
Bye bye!
FCFS Output:
create --------------------
create --------------------
create --------------------
create --------------------
display -------------------------
search ----------------
open ----------
display_open -------------------
close -----------------
SJF Output:
Process create
--------------------
Process create
--------------------
Process create
--------------------
Process create
--------------------
Process display
-------------------------
Process search
----------------
Process open
----------
Process close
-----------------
Process display_open
-------------------
Priority Scheduling Output: 
Process	Burst Time	Waiting Time
open		10		0
search		16		10
create		20		26
create		20		46
create		20		66
create		20		86
display		25		106
close		17		131
display_open		19		148
Round Robin Output:

State of process create:* * * * 
State of process create:* * * * 
State of process create:* * * * 
State of process create:* * * * 
State of process create:* * * * 
State of process create:* * * * 
State of process create:* * * * 
State of process display:* * * * 
State of process create:* * * * 
State of process search:* * * * 
State of process create:* * * * 
State of process open:* * * * 
State of process create:* * * * 
State of process display_open:* * * * 
State of process create:* * * * 
State of process close:* * * * 
State of process display:* * * * 
State of process create:* * * * 
State of process search:* * * * 
State of process create:* * * * 
State of process open:* * * * 
State of process create:* * * * 
State of process display_open:* * * * 
State of process create:* * * * 
Process create:Execution complete 
**********************************************************
State of process close:* * * * 
State of process display:* * * * 
State of process create:* * * * 
State of process search:* * * * 
State of process create:* * * * 
Process create:Execution complete 
**********************************************************
State of process open:* * 
Process open:Execution complete 
**********************************************************
State of process create:* * * * 
State of process display_open:* * * * 
State of process close:* * * * 
State of process display:* * * * 
State of process create:* * * * 
Process create:Execution complete 
**********************************************************
State of process search:* * * * 
Process search:Execution complete 
**********************************************************
State of process create:* * * * 
Process create:Execution complete 
**********************************************************
State of process display_open:* * * * 
State of process close:* * * * 
State of process display:* * * * 
State of process display_open:* * * 
Process display_open:Execution complete 
**********************************************************
State of process close:* 
Process close:Execution complete 
**********************************************************
State of process display:* * * * 
State of process display:* 
Process display:Execution complete 
*********************************************************************************************************************/
 