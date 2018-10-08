import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private Node result;
    private boolean isSolvable;
    private class Node implements Comparable<Node>{
        public Node parent;
        public Board board;
        public int moves;
        public int priority;
        public Node(Board b, Node p, int m)
        {
            board = b;
            parent = p;
            moves = m;
            priority = moves + b.manhattan();
        }
        @Override
        public int compareTo(Node o) {
            if(this.priority < o.priority) return -1;
            else if(this.priority > o.priority) return 1;
            return 0;
        }
    }
    public Solver(Board initial)
    {
        if(initial == null)
            throw new IllegalArgumentException();
        Node curr = new Node(initial, null, 0);
        Board temp = initial.twin();
        Node currTwin = new Node(temp, null, 0);
        MinPQ<Node> pqt = new MinPQ<>();
        MinPQ<Node> pq = new MinPQ<>();
        while (true)
        {
            if(curr.board.isGoal()){
                isSolvable = true;
                break;
            }
            else if(currTwin.board.isGoal()){
                isSolvable = false;
                break;
            }
            for (Board b:curr.board.neighbors())
                if(curr.parent == null || curr.parent != null && !curr.parent.board.equals(b))
                    pq.insert(new Node(b, curr, curr.moves + 1));
            for (Board b:currTwin.board.neighbors())
                if(currTwin.parent == null || currTwin.parent != null && !currTwin.parent.board.equals(b))
                    pqt.insert(new Node(b, currTwin, currTwin.moves + 1));
            curr = pq.delMin();
            currTwin = pqt.delMin();
        }
        if(isSolvable)
            result = curr;
    }
    public int moves(){
        if(!isSolvable) return -1;
        return result.moves;
    }
    public Iterable<Board> solution()
    {
        if(!isSolvable) return null;
        ArrayList<Board> arrayList = new ArrayList<>();
        Stack<Board> s = new Stack<>();
        Node curr = result;
        while (curr != null)
        {
            s.push(curr.board);
            curr = curr.parent;
        }
        while (!s.isEmpty())
            arrayList.add(s.pop());
        return arrayList;
    }
    public boolean isSolvable(){
        return isSolvable;
    }

}
