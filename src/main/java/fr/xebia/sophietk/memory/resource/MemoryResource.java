package fr.xebia.sophietk.memory.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import fr.xebia.sophietk.memory.service.CardPosition;
import fr.xebia.sophietk.memory.service.Game;
import fr.xebia.sophietk.memory.service.GameService;
import fr.xebia.sophietk.memory.service.LogService;
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
public class MemoryResource {

	private GameService gameService;
	private ScoreService scoreService;
	private LogService logService;

	@Inject
	public MemoryResource(GameService gameService, ScoreService scoreService, LogService logService) {
		this.gameService = gameService;
		this.scoreService = scoreService;
		this.logService = logService;
	}

	@GET
	@Path("ping")
	public String ping(@Context HttpServletRequest request) {
		return "pong " + request.getRemoteAddr();
	}

	@POST
	public MemoryResponse play(List<List<Integer>> positions, @Context HttpServletRequest request) throws Exception {
		String player = request.getRemoteAddr();
		Game game = gameService.getCurrentGame();
		logService.addLog(player, "Plays " + new ObjectMapper().writeValueAsString(positions));

		if (game.getProgress() == 100) {
			Response response = Response.status(Response.Status.BAD_REQUEST).entity("Cannot play because game is over").build();
			throw new WebApplicationException(response);
		}

		if(!gameService.canPlay(player)) {
			Response response = Response.status(Response.Status.BAD_REQUEST).entity("You play too fast").build();
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
		final int turnScore = turn.getTurnScore();
		int gameScore = scoreService.addTurnScore(player, game.getGameId(), turnScore);
		logService.addLog(player, String.format("%s %d (total: %d)", turnScore >= 0 ? "Wins" : "Loses", turnScore, gameScore));

		return new MemoryResponse(game.getGameId(), game.getProgress(), turn, gameScore);
	}
}
