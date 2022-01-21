import java.util.ArrayList;

public class A2048AI {

    
    public static void main(String[] args){

        
        Board b = new Board();

        while(possibleMoves(b.board).size() != 0){
            switch(nextMove(b.board)){
                case 0:
                    b.left();
                    break;
                case 1:
                    b.right();
                    break;
                case 2:
                    b.up();
                    break;
                case 3:
                    b.down();
                    break;
            }
            b.populateOne();
            System.out.println(b);
            System.out.println();
        }

    }

    public static int nextMove(int[][] b){
        return (int) Expectimax(b, 3, true)[0];
    }
    
    public static double[] Expectimax(int[][] board, int depth, boolean playerTurn){
        ArrayList<Integer> moves = possibleMoves(board);
        if(depth == 0){
            double[] result = {-1, staticEval(board)};
            return result;
        }
        if(moves.size() == 0){
            double[] result = {-3, -3};
            return result;
        }
        
        if(playerTurn){
            double[] max = {-1, -1};
            for(int move : moves){
                double[] returned = Expectimax(move(board, move), depth-1, false);
                if(returned[1] > max[1]){
                    max[1] = returned[1];
                    max[0] = move;
                }
                
            }
            return max;
        } else{
            double sum = 0;
            int count = 0;
            int[][] subBoard = new int[board.length][board[0].length];
            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[0].length; j++){
                    subBoard[i][j] = board[i][j];
                }
            }
            for(int a = 0; a < board.length; a++){
                for(int b = 0; b < board[0].length; b++){
                    if(board[a][b] == 0){
                        subBoard[a][b] = 2;
                        sum += Expectimax(subBoard, depth-1, true)[1] * 0.9;
                        subBoard[a][b] = 4;
                        sum += Expectimax(subBoard, depth-1, true)[1] * 0.1;
                        count++;
                    }
                }
            }
            double[] result = {-1, sum/count};
            return result;
        }

    }

    public static double staticEval(int[][] board){
        double score = 0;

        //count empty squares
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == 0){
                    score += 8;
                }
            }
        }


        //check if large value are on the edge
        for(int i = 0; i < board.length; i+=3){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != 0){
                    score += Math.log(board[i][j])/(Math.log(2));
                }
            }
        }


        return score;
    }

    public static ArrayList<Integer> possibleMoves(int[][] board){
        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i = 0; i < 4; i++){
            int[][] results = move(board, i);
            boolean flag = false;
            for(int a = 0; a < board.length; a++){
                for(int b = 0; b < board[0].length; b++){
                    if(board[a][b] != results[a][b]){
                        flag = true;
                        break;
                    }
                }
            }
            if(flag){
                result.add(i);
            }
        }
        return result;
    }

    public static int[][] move(int[][] board, int move){
        int[][] result = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                result[i][j] = board[i][j];
            }
        }
        switch(move){
            case 0:
                return(left (result));
            case 1:;
                return(right(result));
            case 2:
                return(up   (result));
            case 3:
                return(down (result));
        }
        return null;
    }

    public static int[][] left(int[][] board){
        slideLeft(board);
        for(int j = 0; j < board.length; j++) {
			for(int i = 0; i < board[0].length-1; i++){
				if(board[j][i] == board[j][i+1]) {
					board[j][i] += board[j][i+1];
					board[j][i+1] = 0;
				}
			}
		}
        slideLeft(board);
        return board;
    }

    public static int[][] right(int[][] board){
        slideRight(board);
        for(int j = 0; j < board.length; j++) {
			for(int i = board[0].length-1; i > 0; i--){
				if(board[j][i] == board[j][i-1]) {
					board[j][i] += board[j][i-1];
					board[j][i-1] = 0;
				}
			}
		}
        slideRight(board);
        return board;
    }

    public static int[][] up(int[][] board){
        board = flipBoard(board);
        left(board);
        return flipBoard(board);
    }

    public static int[][] down(int[][] board){
        board = flipBoard(board);
        right(board);
        return flipBoard(board);
    }

    public static int[][] flipBoard(int[][] board){
        int[][] result = new int[board[0].length][board.length];

        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                result[j][i] = board[i][j];
            }
        }
        return result;
    }

    public static void slideRight(int[][] board){
        for(int i = 0; i < board.length; i++) {
			int index = board[0].length-1;
		    for(int j = index; j >= 0; j--) {
			    if(board[i][j] != 0) {
    				if(index != j) {
	    				board[i][index] = board[i][j];
		    			board[i][j] = 0;
			    	}
				    index--;
			    }
		    }
		}
    }

    public static void slideLeft(int[][] board){
        for(int i = 0; i < board.length; i++) {
			int index = 0;
		    for(int j = 0; j < board[0].length; j++) {
			    if(board[i][j] != 0) {
    				if(index != j) {
	    				board[i][index] = board[i][j];
		    			board[i][j] = 0;
			    	}
				    index++;
			    }
		    }
		}
    }
}
