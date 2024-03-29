package board;

public class PointColRow {
    public int col;
    public int row;

    public PointColRow() {

    }

    public PointColRow(int col, int row) {
        this.col = col;
        this.row = row;
    }
    public boolean compare(PointColRow point){
        return point.col==this.col && point.row == this.row;
    }

    @Override
    public String toString() {
        return "[col: " + col + ", row: " + row + "]";
    }
}
