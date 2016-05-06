package piece;

public enum Owner {
	Player1, Player2;
	private Owner otherOwner;

	static {
		Player1.otherOwner = Player2;
		Player2.otherOwner = Player1;
	}

	public Owner getOtherOwner() {
		return this.otherOwner;
	}
}
