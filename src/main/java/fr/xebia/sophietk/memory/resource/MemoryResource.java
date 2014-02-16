package fr.xebia.sophietk.memory.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
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
@Singleton
public class MemoryResource {

	private ScoreService scoreService;
	private Game game;
	private int gameId;

	@Inject
	public MemoryResource(ScoreService scoreService) {
		this.scoreService = scoreService;
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
