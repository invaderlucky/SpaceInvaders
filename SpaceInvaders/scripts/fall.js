function update() {
	if (game_object.getX() >= 200 && game_object.getX() <= 300
			&& game_object.y < 395) {
		game_object.setY(325);
	}
	else {
		game_object.setY((450 - game_object.r.height));
	}
}