package fr.xebia.sophietk.memory.resource;

import com.google.inject.Inject;
import fr.xebia.sophietk.memory.service.Game;
import fr.xebia.sophietk.memory.service.GameService;
import fr.xebia.sophietk.memory.service.LogService;
import fr.xebia.sophietk.memory.service.ScoreService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/admin")
public class AdminResource {

	private GameService gameService;
	private ScoreService scoreService;
	private LogService logService;

	@Inject
	public AdminResource(GameService gameService, ScoreService scoreService, LogService logService) {
		this.gameService = gameService;
		this.scoreService = scoreService;
		this.logService = logService;
	}

	@GET
	@Path("/game")
	public Game currentGame() {
		return gameService.getCurrentGame();
	}

	@POST
	@Path("/new")
	public void newGame(int gridSize) {
		logService.addLog("admin", "Launches new game with size " + gridSize);
		try {
			gameService.launchNewGame(gridSize);
		} catch (RuntimeException e) {
			Response response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
			throw new WebApplicationException(response);
		}
		logService.addLog("admin", "Sets game round " + gameService.getCurrentGame().getGameId());
	}

	@GET
	@Path("/logs")
	public List<Map<String, Object>> logs() {
		return logService.getLogs();
	}

	@POST
	@Path("/reset")
	public void reset() {
		gameService.reset();
		logService.reset();
		scoreService.reset();
	}

	@POST
	@Path("/tempo")
	public void temporize(int duration) {
		gameService.temporize(duration);
	}
}
