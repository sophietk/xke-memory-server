package fr.xebia.sophietk.memory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/play")
public class MemoryResource {

	@GET
	@Path("ping")
	public String ping() {
		return "pong";
	}
}
