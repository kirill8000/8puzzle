import java.util.ArrayList;

public class Board {

    private final int[][] blocks;
    private int hamming = 0;
    private int manhattan = 0;
    private int zX = 0, zY = 0;

    private int[][] getCopy()
    {
        int[][] a = new int[blocks.length][blocks.length];
        for(int i = 0; i < a.length; i++)
        {
            for(int j = 0; j < a.length; j++)
            {
                a[i][j] = blocks[i][j];
            }
        }
        return a;
    }

    private void swap(int[][] ar, int x1, int y1, int x2, int y2)
    {
        int t = ar[x1][y1];
        ar[x1][y1] = ar[x2][y2];
        ar[x2][y2] = t;
    }

    private Board getNeighbor(int x, int y)
    {
        int[][] a = getCopy();
        swap(a, zX, zY, x, y);
        return new Board(a);
    }

    public Board(int[][] blocks)
    {

        this.blocks = blocks;
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks.length; j++)
            {
                if(blocks[i][j] != (i * blocks.length + j + 1)) {
                    if(!((j == blocks.length - 1) && (i == blocks.length - 1)))
                        hamming++;
                    if(blocks[i][j] != 0)
                    {
                        int x = blocks[i][j] % blocks.length == 0 ? blocks[i][j] / blocks.length : blocks[i][j] / blocks.length + 1;
                        int y = blocks[i][j] - ((x - 1) * blocks.length);
                        manhattan += Math.abs(x - (i + 1)) + Math.abs(y - (j + 1));
                    }
                    else
                    {
                        zX = i;
                        zY = j;
                    }
                }
            }
        }
    }
    public int dimension()
    {
        return blocks.length;
    }
    public int hamming()
    {
        return hamming;
    }
    public int manhattan()
    {
        return manhattan;
    }
    public boolean isGoal()
    {
           return hamming == 0;
    }
    public Board twin()
    {
        int x = 0, y = 0;
        int x2 = 1, y2 = 1;
        if(blocks[x][y] == 0) x++;
        if(blocks[x2][y2] == 0) x2--;
        int[][] b = getCopy();
        int t = b[x][y];
        b[x][y] = b[x2][y2];
        b[x2][y2] = t;
        return new Board(b);
    }
    public boolean equals(Object y)
    {
        if(y == null) return false;
        if(y == this) return true;
        if(y.getClass() != this.getClass()) return false;
        return (y.toString()).equals(this.toString());
    }
    public Iterable<Board> neighbors()
    {
        ArrayList<Board> n = new ArrayList<>();
        if(zX > 0)
            n.add(getNeighbor(zX - 1, zY));
        if(zX < blocks.length - 1)
            n.add(getNeighbor(zX + 1, zY));
        if(zY > 0)
            n.add(getNeighbor(zX, zY - 1));
        if(zY < blocks.length - 1)
            n.add(getNeighbor(zX, zY + 1));
        return n;
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        b.append(dimension());
        b.append('\n');
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks.length; j++)
            {
                b.append('\t');
                b.append(blocks[i][j]);
            }
            b.append('\n');
        }
        return b.toString();
    }
    public static void main(String[] args)
    {
        Board b = new Board(new int[][]{{1,2,3}, {0, 7, 6}, {5, 4, 8}});
        System.out.println(b.toString());
        for (Board brd: b.neighbors()
             ) {
            System.out.println(brd.toString());
        }
        System.out.println(b.twin().toString());
    }
}