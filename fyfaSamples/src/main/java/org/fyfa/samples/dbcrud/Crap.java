package org.fyfa.samples.dbcrud;

import javax.ws.rs.Path;

import org.fyfa.samples.RenderingEngine;
import org.springframework.jdbc.core.JdbcTemplate;

@Path("/crap")
public class Crap extends CountryRest {

	public Crap(RenderingEngine renderingEngine, JdbcTemplate jdbcTemplate) throws Exception {
		super(renderingEngine, jdbcTemplate);
		// TODO Auto-generated constructor stub
	}

}
