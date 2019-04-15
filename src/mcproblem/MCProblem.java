package mcproblem;

import java.util.HashSet;
import java.util.Set;

public class MCProblem {
    public static int solutions = 0;
    
    public static void main(String[] args) {
        
        Set<String> prevStates = new HashSet<String>();
        findSafePath(3,3,0,0,0,0, prevStates, "", 0);
        
    }
    
    public static void findSafePath(int m1, int c1, int m2, int c2, int boatM, int boatC, Set<String> prevStates, String path,int move) {
        
        //Check for impossible move (i.e. more Missionaries get on boat than available etc.)
        if (m1 < 0 || c1 < 0 || m2 < 0 || c2 < 0) return;
        
        //Check for missionary to cannibal inequality while boat is traveling to current shore (not docked)
        if ((m1 < c1 && m1 != 0) || (m2 < c2 && m2 != 0)) return;
        
        //Draw boat in transit to shore (Skip for first iter since boat starts out docked)
        if (move > 0) {
            String pic = move % 2 == 1 ? drawDepart(m2, c2, m1, c1, boatM, boatC) : drawReturn(m1, c1, m2, c2, boatM, boatC) ;
            path += pic + "\n";
        }
        
        //Unload Missionaries and Cannibals to shore after boat docks at shore
        m1 += boatM;
        c1 += boatC;
        
      //Check for missionary to cannibal inequality while boat is docked. consider all members on shore and boat.
        if ((m1 < c1 && m1 != 0)) return;
        
      //Missionaries are safe when all three are on the other side. 
        if (move % 2 == 0 && m1 == 0) {
            String finalPath = String.join("\n", path) + "\n\n\n\n";
            System.out.println(String.format("Solution %d\n\n", ++solutions) + finalPath);
            return;
        }
        
        //Avoid infinite recursion
        String state = String.format("%d,%d,%d,%d,%d", move % 2, m1, c1, m2, c2, boatM, boatC);
        if (prevStates.contains(state)) return;
        prevStates.add(state);
        
        //Recursively Check every possible boarding combination for next transit
        //2M, 2C, 1M 1C, 1M, 1C  
        findSafePath(m2, c2, m1-2, c1,   2, 0, new HashSet(prevStates), path, move + 1);
        findSafePath(m2, c2, m1,   c1-2, 0, 2, new HashSet(prevStates), path, move + 1);
        findSafePath(m2, c2, m1-1, c1-1, 1, 1, new HashSet(prevStates), path, move + 1);
        findSafePath(m2, c2, m1-1, c1,   1, 0, new HashSet(prevStates), path, move + 1);
        findSafePath(m2, c2, m1,   c1-1, 0, 1, new HashSet(prevStates), path, move + 1);
    }
    
    public static String drawDepart(int m1, int c1, int m2, int c2, int mb, int cb) {
        String l1  = "  _ _ _     _ _ _     _____          _ _ _     _ _ _ \n";
        
        String l2  = "|       | |       |  |     \\       |       | |       |\n";
        
        String l3a = "| " + duplicate(m1,"m") + " | " + "| " + duplicate(c1,"c") + " |  ";
        String l3b = "| " + boat(mb,cb) + "  |  ->  ";
        String l3c = "| " + duplicate(m2,"m") + " | " + "| " + duplicate(c2,"c") + " |  ";
        String l3  = l3a + l3b + l3c + "\n";
        
        String l4 = "| _ _ _ | | _ _ _ |  |_____/       | _ _ _ | | _ _ _ |\n";
        
        String desc = "";
        if (mb * cb >= 1) desc = "Missionary and Cannibal depart to destination";
        else if (mb == 2) desc = "2 Missionaries depart to destination";
        else if (mb == 1) desc = "1 Missionary departs to destination";
        else if (mb == 0) desc = String.format("%d Cannibal(s) depart to destination", cb);
        
        return desc + "\n" + l1 + l2 + l3 + l4;
    }
    
    public static String drawReturn(int m1, int c1, int m2, int c2, int mb, int cb) {
        String l1  = "  _ _ _     _ _ _          _____      _ _ _     _ _ _ \n";
        
        String l2  = "|       | |       |       /     |  |       | |       |\n";
        
        String l3a = "| " + duplicate(m1,"m") + " | " + "| " + duplicate(c1,"c") + " |  ";
        String l3b = "<-  |  " + boat(mb,cb) + " |  ";
        String l3c = "| " + duplicate(m2,"m") + " | " + "| " + duplicate(c2,"c") + " |  ";
        String l3  = l3a + l3b + l3c + "\n";
        
        String l4 = "| _ _ _ | | _ _ _ |       \\_____|  | _ _ _ | | _ _ _ | \n";
        
        String desc = "";
        if (mb * cb >= 1) desc = "Missionary and Cannibal return to departure";
        else if (mb == 2) desc = "2 Missionaries return to departure";
        else if (mb == 1) desc = "1 Missionary returns to departure";
        else if (mb == 0) desc = String.format("%d Cannibal(s) returns to departure", cb);
        
        return desc + "\n" + l1 + l2 + l3 + l4;
    }
    
    public static String duplicate(int n, String s) {
        String result = "";
        for (int i = 0; i < n; i++) result += s + " ";
        return (result + "       ").substring(0,5);
    }
    
    public static String boat(int m, int c) {
        if (m == 2) return "m m";
        if (c == 2) return "c c";
        return (m == 1 ? "m " : "  ") + (c == 1 ? "c" : " ");
    }

}





 


