function update() {
	if (game_object.getX() - game_object.getMoveSpeed() >= 0) {
		game_object.setX(game_object.getX() - game_object.getMoveSpeed());
	}
}