package fr.xebia.sophietk.memory.resource;

import com.google.inject.Inject;
import fr.xebia.sophietk.memory.service.ScoreService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/scores")
public class ScoreResource {

	private ScoreService scoreService;

	@Inject
	public ScoreResource(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	@GET
	public Map<String, Integer> getTotalScores() {
		return scoreService.getTotalScores();
	}

	@GET
	@Path("/game/{gameId}")
	public Map<String, Integer> getGameScores(@PathParam("gameId") int gameId) {
		return scoreService.getGameScores(gameId);
	}

	@GET
	@Path("/player/{player}")
	public Map<Integer, Integer> getPlayerScores(@PathParam("player") String player) {
		return scoreService.getPlayerScores(player);
	}

	@GET
	@Path("/game")
	public Set<Integer> getAllGamesId() {
		return scoreService.getAllGamesId();
	}

	@GET
	@Path("/player")
	public Set<String> getAllPlayers() {
		return scoreService.getAllPlayers();
	}

	@POST
	@Path("/register")
	public String registerPlayer(String player, @Context HttpServletRequest request) {
		scoreService.registerPlayer(request.getRemoteAddr(), player);
		return "ok";
	}
}
