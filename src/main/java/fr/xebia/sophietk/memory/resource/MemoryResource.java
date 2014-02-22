package fr.xebia.sophietk.memory.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.xebia.sophietk.memory.service.CardPosition;
import fr.xebia.sophietk.memory.service.Game;
import fr.xebia.sophietk.memory.service.ScoreService;
import fr.xebia.sophietk.memory.service.Turn;
import fr.xebia.sophietk.memory.util.PositionConverter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
	public String ping(@Context HttpServletRequest request) {
		return "pong " + request.getRemoteAddr();
	}

	@POST
	public MemoryResponse play(List<List<Integer>> positions, @Context HttpServletRequest request) throws Exception {
		if (game.getProgress() == 100) {
			Response response = Response.status(Response.Status.BAD_REQUEST).entity("Cannot play because game is over").build();
			throw new WebApplicationException(response);
		}

		List<CardPosition> cardPositions;
		try {
			cardPositions = PositionConverter.toCardPosition(positions);
		} catch (RuntimeException e) {
			Response response = Response.status(Response.Status.BAD_REQUEST).entity("Cards positions to flip are malformed").build();
			throw new WebApplicationException(response);
		}

		Turn turn = game.play(cardPositions);
		String player = request.getRemoteAddr();
		int gameScore = scoreService.addTurnScore(player, gameId, turn.getTurnScore());

		return new MemoryResponse(gameId, game.getProgress(), turn, gameScore);
	}
}
