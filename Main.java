
class ChessBoard
{
	private int numRows, numCols;
	private boolean[][] board;
	int numQueenSolns = 0;
	
	ChessBoard(int numRowsCols)
	{
		numRows = numCols = numRowsCols;
		board = new boolean[numRows][numCols]; //arrays of booleans are initialized to false
	}
	
	public void add(int row, int col) { board[row][col] = true; }
	public void remove(int row, int col) { board[row][col] = false; }

	public boolean isSafe(int row, int col)
	{
		for (int r = row; r < numRows; r++ ) if (board[r][col]) return false;
		for (int r = row; r >= 0; r-- ) if (board[r][col]) return false;
		for (int c = col; c < numCols; c++ ) if (board[row][c]) return false;
		for (int c = col; c >= 0; c-- ) if (board[row][c]) return false;

		for (int r = row, c = col; r < numRows && c < numCols; r++, c++ ) if (board[r][c]) return false;
		for (int r = row, c = col; r >= 0 && c >= 0; r--, c-- ) if (board[r][c]) return false;
		for (int r = row, c = col; r < numRows && c >= 0; r++, c-- ) if (board[r][c]) return false;
		for (int r = row, c = col; r >= 0 && c < numCols; r--, c++ ) if (board[r][c]) return false;
		return true;
	}

	
	public void solveQueens() 
	{
		solveQueens(0);
	}	

	private void solveQueens(int col) 
	{
		if (col == numCols){
			print();
			numQueenSolns++;
			return;
		}
		for (int row = 0; row < numRows; row++) {
			if (isSafe(row, col)){
				add(row, col);
				solveQueens(col + 1);
				remove(row, col);
			}
		}
	}			

	
	public void print()
	{
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
				if (board[i][j] == false)
					System.out.print('⬜');  //https://www.alt-codes.net/square-symbols
				else
					System.out.print('⬛');
			System.out.println();
		}	
		System.out.println();
	}
}



public class Main 
{
	public static void main(String[] args) 
	{
		ChessBoard chessBoard = new ChessBoard(8);
		chessBoard.solveQueens();
		System.out.println(chessBoard.numQueenSolns);
	}
}



