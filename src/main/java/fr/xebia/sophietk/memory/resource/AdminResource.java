package fr.xebia.sophietk.memory.resource;

import com.google.inject.Inject;
import fr.xebia.sophietk.memory.service.Game;
import fr.xebia.sophietk.memory.service.GameService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/admin")
public class AdminResource {

	private GameService gameService;

	@Inject
	public AdminResource(GameService gameService) {
		this.gameService = gameService;
	}

	@GET
	@Path("/game")
	public Game currentGame() {
		return gameService.getCurrentGame();
	}

	@POST
	@Path("/new")
	public void newGame(int gridSize) {
		try {
			gameService.launchNewGame(gridSize);
		} catch (RuntimeException e) {
			Response response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
			throw new WebApplicationException(response);
		}
	}
}
