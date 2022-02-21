package project2;

public class Pawn extends ChessPiece {
	boolean hasMoved;
	int enPassantTimer = 0;

	public Pawn(Player player) {
		super(player);
	}

	public String type() {
		return "Pawn";
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setHasMoved() {
		hasMoved = true;
	}

	public int getEnPassantTimer() {
		return enPassantTimer;
	}

	public void startEnPassantTimer() {
		enPassantTimer = 2;
	}

	public void decEnPassantTimer() {
		if (enPassantTimer > 0) {
			System.out.println("Decremented en passant timer from " + enPassantTimer);
			enPassantTimer--;
		}
	}

	@Override
	// determines if the move is valid for a pawn piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}

		if (player() == Player.WHITE) {
			// Normal 1 move forward
			if (move.fromColumn == move.toColumn && move.fromRow == move.toRow + 1 && board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// If the pawn hasn't moved yet, it can move 2 spaces forward
			if (!hasMoved && move.fromColumn == move.toColumn && move.fromRow == move.toRow + 2 &&
					board[move.toRow + 1][move.toColumn] == null && board[move.toRow][move.toColumn] == null) {
				return true;
			}
			if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
					move.fromRow == move.toRow + 1) {
				// Normal piece capture
				if (board[move.toRow][move.toColumn] != null &&
						board[move.toRow][move.toColumn].player() == Player.BLACK) {
					return true;
				}
				// En passant
				else if (board[move.toRow + 1][move.toColumn] != null &&
						board[move.toRow + 1][move.toColumn].type().equals("Pawn") &&
						board[move.toRow + 1][move.toColumn].player() == Player.BLACK &&
						((Pawn) board[move.toRow + 1][move.toColumn]).getEnPassantTimer() >= 1) {
					return true;
				}
			}

		} else {  // player() == Player.BLACK
			// Normal 1 move forward
			if (move.fromColumn == move.toColumn && move.fromRow == move.toRow - 1 && board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// If the pawn hasn't moved yet, it can move 2 spaces forward
			if (!hasMoved && move.fromColumn == move.toColumn && move.fromRow == move.toRow - 2 &&
					board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// Can move diagonally forward if there is an enemy piece there
			if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
					move.fromRow == move.toRow - 1) {
				// Normal piece there
				if (board[move.toRow][move.toColumn] != null &&
						board[move.toRow][move.toColumn].player() == Player.WHITE) {
					return true;
				}
				// En passant
				else if (board[move.toRow - 1][move.toColumn] != null &&
						board[move.toRow - 1][move.toColumn].type().equals("Pawn") &&
						board[move.toRow - 1][move.toColumn].player() == Player.WHITE &&
						((Pawn) board[move.toRow - 1][move.toColumn]).getEnPassantTimer() >= 1) {
					return true;
				}
			}
		}
		return false;
	}
}
