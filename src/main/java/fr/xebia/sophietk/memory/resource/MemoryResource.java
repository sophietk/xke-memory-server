package fr.xebia.sophietk.memory.resource;

import fr.xebia.sophietk.memory.service.CardPosition;
import fr.xebia.sophietk.memory.service.Game;
import fr.xebia.sophietk.memory.service.ScoreService;
import fr.xebia.sophietk.memory.service.Turn;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/play")
public class MemoryResource {

	private final static ScoreService scoreService = new ScoreService();
	private Game game;
	private int gameId;

	public MemoryResource() {
		game = new Game();
		gameId = 1;
	}

	@GET
	@Path("ping")
	public String ping() {
		return "pong";
	}

	@POST
	public MemoryResponse play(List<CardPosition> positions) {
		Turn turn = game.play(positions);
		String ip = "";

		int gameScore = scoreService.addTurnScoreAndReturnGameScore(ip, gameId, turn.getTurnScore());

		return new MemoryResponse(gameId, game.getProgress(), turn, gameScore);
	}
}
