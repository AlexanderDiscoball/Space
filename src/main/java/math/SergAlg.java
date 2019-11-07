
package math;

import math.entity.AreaSegments.AreaList;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileWriter;


public class SergAlg implements Cloneable{
	static final Random rand=new Random(124);
	private int begin;
	private int end;
	private int roll;
	//private int flight_number;
	public SergAlg(int begin_, int end_) {
		begin=begin_;
		end=end_;
	}
	public SergAlg(int begin_, int end_, int roll_) {
		this(begin_,end_);
		roll=roll_;
	}

	public int getBegin() {
		return begin;
	}
	
	public int getEnd() {
		return end;
	}
	
	public int getRoll() {
		return roll;
	}
	
	public String getInterval(){
		return "\""+String.valueOf(begin+" "+end)+"\"";
	}

	public int getLen() { //the method returning length of interval
		return (end-begin);
	}
	
	public int getWeight() { //change it when you find out how
		return 0; 
	}
	
	public void compare() {
		
	}
	
	public boolean equal() {
		return true;
	}
	
	public boolean areIntersected() {
		return true;
	}

	@Override
	public String toString() {
		return +begin
				+"-"
				+end
				+"L"
				+roll;
	}

	public static void output_tracks(List<LinkedList<SergAlg>> catalog, String note) {
		System.out.println(note);
		for(int i=0;i<catalog.size();i++) {
				ListIterator<SergAlg> it=catalog.get(i).listIterator();
			while(it.hasNext()) {
				SergAlg temp=it.next();
				System.out.printf("%s","(");
				System.out.printf("%2d",temp.getBegin());
				System.out.printf("%s"," ");
				System.out.printf("%2d",temp.getEnd());
				System.out.printf("%s",")");
				System.out.printf("%d",temp.getRoll());
				System.out.printf("%s"," ");
			}
			System.out.println();
		}
	}
	
	public static void output_tracks(LinkedList<SergAlg> catalog) {
				ListIterator<SergAlg> it=catalog.listIterator();
			while(it.hasNext()) {
				SergAlg temp=it.next();
				System.out.printf("%s","(");
				System.out.printf("%2d",temp.getBegin());
				System.out.printf("%s"," ");
				System.out.printf("%2d",temp.getEnd());
				System.out.printf("%s",")");
				System.out.printf("%d",temp.getRoll());
				System.out.printf("%s"," ");
			}
			System.out.println();
	}
	
	public static int[] get_tracks_info(List<LinkedList<SergAlg>> given_tracks) {
		int tracks_len=0;
		int intervals_quantity=0;
		for(int i=0;i<given_tracks.size();i++) {
			intervals_quantity+=given_tracks.get(i).size();
			for(int j=0;j<given_tracks.get(i).size();j++)
				tracks_len+=given_tracks.get(i).get(j).getLen();
		}
		return new int[] {intervals_quantity,tracks_len};
	}
	
	public static void show_statistics(int bytr_quantity,int bytr_length,int[] info) {//info[0]-quantity,info[1]-len
		if(bytr_quantity!=info[0])
			System.out.println("Solution is built incorrectly! Error with the total number of intervals!");
		else if(bytr_length!=info[1])
			System.out.println("Solution is built incorrectly! Error with the total length of intervals!");
		else{
			System.out.println("Проверка пройдена");
		}
	}
	
	public static String checkIfEquals(ArrayList<int[]> solution_stat) throws IOException{
		Scanner sc0 = new Scanner(new File("D:\\Eclipse_projects\\Path\\path_dir_known-good.txt"));
		ArrayList<int[]> sol_runs = new ArrayList<int[]>();
			while(sc0.hasNext())
				sol_runs.add(new int[]{sc0.nextInt(),sc0.nextInt()}); //sol_runs is known-good solution
		sc0.close();

		if(sol_runs.size()==(solution_stat.size())) {
			for(int i=0;i<sol_runs.size();i++) 
				if(!Arrays.equals(sol_runs.get(i), solution_stat.get(i)))
					return "Length or size of examined subsolution No " + i + " isn't correct";
			
			return "The solution being examined is correct";
		}
		else 
			return "Number of tracks in known-good and examined solutions don't coincide!!!Not optimized!";
	}
	
	public static int rand_gen(int prev) { //Random generator
		int min=1;
		int max=9;
		int diff=max-min;
		int i=rand.nextInt(diff+1);
		i+=min+prev;
		return i;
	}
	
	//to insert track element into solution
	public static int solutionInsert(ListIterator<SergAlg> it_track, ListIterator<SergAlg> it_sol,
									 SergAlg tempo_track){
			 	//tmp_sol=tempo_sol;
						it_sol.previous();
						it_sol.add(tempo_track);
						it_sol.next();
						it_track.remove();
						return tempo_track.getLen();
			 }
	
	//to add track element to the end of solution
	public static int solutionAppend(ListIterator<SergAlg> it_track, ListIterator<SergAlg> it_sol,
									 SergAlg tempo_track){
						it_sol.add(tempo_track);
						it_track.remove();
						return tempo_track.getLen();
			 }
	
	public static ArrayList<int[]> bypass_tracks(List<LinkedList<SergAlg>> tracks, int[] info) {
		ArrayList<int[]> sol_stat = new ArrayList<>();
		int tracks_quantity=tracks.size();
		int flights_quantity=0;
		int bytr_quantity=0;
		int bytr_length=0;
		int initial=0;
		
		
		//System.out.println("solution:"); //comment when you work with large arrays of tracks
		 while(true) { //eternal cycle: to stop when tracks are empty, i.e. passed
			 int last_operated_track_num=0;
			 int bytr_length_prev=bytr_length;
			 LinkedList<SergAlg> sol=new LinkedList<>();
			 ListIterator<SergAlg> it_track=tracks.get(initial).listIterator(); //it_track points at the beginning of track No. i
				 	if(!it_track.hasNext())
				 		break; // exit from eternal cycle
			 	while(it_track.hasNext()) {
			 		SergAlg temp=it_track.next();
			 		sol.add(temp);
			 		bytr_length+=temp.getLen();
			 		it_track.remove();
			 	}
			 last_operated_track_num=initial;

			 ListIterator<SergAlg> it_sol=sol.listIterator();
			 	int init_temp=initial;
			 	for(int j=initial+1;j<tracks.size();j++) {
			 		it_track=tracks.get(j).listIterator();
			 		SergAlg tempo_track, tempo_sol, tempo_sol_pr, tmp_sol;
			 			if(it_track.hasNext()) {
							tempo_track = it_track.next();
						}
			 			else {
							continue;
						}
			 			tempo_sol=it_sol.next();
			 			tempo_sol_pr=new SergAlg(-1,-1);
			 			tmp_sol=tempo_sol;
					do {
						if(tempo_track.getEnd()<=tempo_sol.getBegin()) {
							if((tmp_sol!=tempo_sol)&&(tempo_track.getBegin()>=tempo_sol_pr.getEnd())) {
								tmp_sol=tempo_sol;
								bytr_length+=solutionInsert(it_track, it_sol, tempo_track);
								last_operated_track_num=j;
							}
							else if(tmp_sol==tempo_sol) { 
								bytr_length+=solutionInsert(it_track, it_sol, tempo_track);
								last_operated_track_num=j;
							}
							else { 
								if(initial==init_temp) {
									initial = j;
								}
							}
						}
						else {
							if(it_sol.hasNext()) {
								tempo_sol_pr=tempo_sol;
								tempo_sol=it_sol.next();
									continue;
							}
							else if(tempo_track.getBegin()>=tempo_sol.getEnd()){
								while(it_track.hasNext()) {
									bytr_length+=solutionAppend(it_track, it_sol, tempo_track);
									tempo_track=it_track.next();
								}
								bytr_length+=solutionAppend(it_track, it_sol, tempo_track);
								last_operated_track_num=j;
							}
							else {
								if(initial==init_temp)
									initial=j;
							}
						}
						
						if(tracks.get(j).size()==0 || !it_track.hasNext()
								/*|| count_missed_int>=15500*/) //remove 3rd cond if you want to find the optimized solution
							break;
						else {
							tempo_track=it_track.next();
						}		
					} while(it_track.hasNext() || it_track.hasPrevious());					
						if(j!=tracks.size()-1)//no need to return iterator to the head of sol when bypassing the last track
							it_sol=sol.listIterator(); // how much does it cost????? (time value)
			 	}
			 	//output_tracks(sol); //comment when you work with large arrays of tracks

				bytr_quantity+=sol.size();
				flights_quantity++;
				//sol_stat stores (length, quantity) of all intervals in every solution
				sol_stat.add(new int[] {bytr_length-bytr_length_prev,sol.size(),last_operated_track_num}); 
		 }
		 show_statistics(bytr_quantity,bytr_length,info);
		 System.out.println("Количество решений надирным алгоритмом "+flights_quantity+"/"+tracks_quantity);
		 return sol_stat;
	}

	public static void fileEditor(ArrayList<int[]> solution_stat, int tracks_num) throws IOException{
		FileWriter outwrite=new FileWriter("D:\\Eclipse_projects\\Path\\path_dir_examined.txt");
		//PrintWriter pw = new PrintWriter(outwrite);
		outwrite.write(" len"+"\t");
		outwrite.write("quantity"+"\t");
		outwrite.write("last track"+"\n");
		for(int i=0;i<solution_stat.size();i++) {
			outwrite.write(" "+Integer.toString(solution_stat.get(i)[0])+"\t");
			outwrite.write("      "+Integer.toString(solution_stat.get(i)[1])+"\t");
			outwrite.write("       "+Integer.toString((solution_stat.get(i)[2])+1)+"/"+tracks_num+" "+"\n");
		}		
		outwrite.close();
	}
	
	public static void main(String[] args) throws CloneNotSupportedException, IOException{
		// TODO Auto-generated method stub
		ArrayList<int[]> solution_stat;
		Scanner in=new Scanner(System.in); // FOR MANUAL WORK(for tests)
		List<LinkedList<SergAlg>> tracks=new ArrayList<>();
		System.out.println("Enter number of tracks: ");
		int tracks_num=in.nextInt(); //quantity of tracks
		in.nextLine(); //this line's necessary to have an ability to continue input
		System.out.println("What do you want to implement: Test(T) or Random(R)");
		String work_tool=in.nextLine(); //tool for work - what you're gonna do
		switch(work_tool) {
			case "T":
				for(int i=0;i<tracks_num;i++) {
					LinkedList<SergAlg> temporary=new LinkedList<>();
					while(in.hasNext()) { // Enter "Ctrl+z" to exit from cycle
						if(in.hasNextInt()) {
							SergAlg temp_obj=new SergAlg(in.nextInt(),in.nextInt(),i);
							temporary.add(temp_obj);
						}
						else {
							in.next();
							break;
						}	
					}
					tracks.add(temporary);
				}
			break;
			case "R":
				System.out.println("Enter number of intervals: ");
				int intervals_num=in.nextInt();
				int temp, temp1;
				for(int i=0;i<tracks_num;i++) {
					temp=rand_gen(0);
					LinkedList<SergAlg> temporary=new LinkedList<>();
					for(int j=0;j<intervals_num;j++) {
						temp1=rand_gen(temp);
						SergAlg temp_obj=new SergAlg(temp,temp1,i);
						temporary.add(temp_obj);
						temp=rand_gen(temp1);
					}
					tracks.add(temporary);
				}
			break;
		}
		in.close(); //Finish enter from the keyboard
		
		int[] tracks_characteristics=new int[2]; //[0]=quantity;[1]=length
		tracks_characteristics=get_tracks_info(tracks);
		output_tracks(tracks,"tracks: "); // comment(i.e. hide) when you work with large arrays of tracks
		long startTime = System.currentTimeMillis();
		bypass_tracks(tracks,tracks_characteristics); //now tracks store runs in order how we should bypass them
		long timeSpent = System.currentTimeMillis() - startTime;
		System.out.println("The solution has been built for " + timeSpent + " milliseconds OR " + (float)timeSpent/1000 + " seconds");
		//System.out.println(checkIfEquals(solution_stat));
		//fileEditor(solution_stat,tracks_num);	
	}
}
