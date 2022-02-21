package project2;

public class ChessModel implements IChessModel {
    private IChessPiece[][] board;
	private Player player;


	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(Player.WHITE);
		}

		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight (Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}
	}

	public boolean isComplete() {
		boolean valid = false;
		return valid;
	}

	public boolean isValidMove(Move move) {
		boolean valid = false;

		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board))
                return true;

		return valid;
	}

	public void move(Move move) {
		if (board[move.fromRow][move.fromColumn].type().equals("Pawn")) {
			((Pawn) board[move.fromRow][move.fromColumn]).setHasMoved();
			if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
				// Two steps forward
				if (move.fromRow == move.toRow + 2) {
					((Pawn) board[move.fromRow][move.fromColumn]).startEnPassantTimer();
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow + 1 && board[move.toRow][move.toColumn] == null) {
					board[move.toRow + 1][move.toColumn] = null;
				}
			} else {  // player is BLACK
				// Two steps forward
				if (move.fromRow == move.toRow - 2) {
					((Pawn) board[move.fromRow][move.fromColumn]).startEnPassantTimer();
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow - 1 && board[move.toRow][move.toColumn] == null) {
					board[move.toRow - 1][move.toColumn] = null;
				}
			}
		} else if (board[move.fromRow][move.fromColumn].type().equals("King")) {
			// Checks for castling
            if (!((King) board[move.fromRow][move.fromColumn]).hasMoved()) {
                if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
                    // Left castle
                    if (move.toRow == 7 && move.toColumn == 2) {
                        board[7][3] = board[7][0];
                        board[7][0] = null;
                    // Right castle
                    } else if (move.toRow == 7 && move.toColumn == 6) {
                        board[7][5] = board[7][7];
                        board[7][7] = null;
                    }
                } else {  // player is BLACK
                    // Left castle
                    if (move.toRow == 0 && move.toColumn == 2) {
                        board[0][3] = board[0][0];
                        board[0][0] = null;
                    // Right castle
                    } else if (move.toRow == 0 && move.toColumn == 6) {
                        board[0][5] = board[0][7];
                        board[0][7] = null;
                    }
                }
            }
            ((King) board[move.fromRow][move.fromColumn]).setHasMoved();
        } else if (board[move.fromRow][move.fromColumn].type().equals("Rook")) {
			((Rook) board[move.fromRow][move.fromColumn]).setHasMoved();
		}


		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	public boolean inCheck(Player p) {
//		for (int r = 0; r < 8; r++) {
//			for (int c = 0; c < 8; c++) {
//				if (board[r][c] != null) {
//					for (int i = 0; i < 8; i++) {
//						for (int j = 0; j < 8; j++) {
//							if (board[r][c].isValidMove(new Move(r, c, i, j), board) && board[i][j] != null &&
//									board[i][j].type().equals("King")) {
//								return true;
//							}
//						}
//					}
//				}
//			}
//		}
		return false;
	}


	public Player currentPlayer() {
		return player;
	}

	public int numRows() {
		return 8;
	}

	public int numColumns() {
		return 8;
	}

	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	public void setNextPlayer() {
		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	public void AI() {
		/* Write a simple AI set of rules in the following order.
		 * a. Check to see if you are in check.
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check
		 *
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 * c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 * d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

		}

	public static void main(String[] args) {
		ChessModel model = new ChessModel();
		model.display();

	}

	public void display() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == null) {
					System.out.print("null\t");
				} else {
					System.out.print(board[i][j].type() + "\t");
				}
			}
			System.out.println();
		}
	}
}
