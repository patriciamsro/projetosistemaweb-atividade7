package br.edu.iftm.atividade7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Atividade7Application implements CommandLineRunner {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Atividade7Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		jdbcTemplate.execute("DROP TABLE agenda IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE agenda(id SERIAL, nome VARCHAR(255), telefone VARCHAR(255))");

		jdbcTemplate.update("insert into agenda (nome, telefone) values (?, ?)","Edson Angoti", "9876");
		jdbcTemplate.update("insert into agenda (nome, telefone) values (?, ?)","Patricia Rocha", "1234");
		jdbcTemplate.update("insert into agenda (nome, telefone) values (?, ?)","Rafael Rocha", "4321");

		Contato contato = jdbcTemplate.queryForObject(
				"select id, nome, telefone from agenda where nome = ?",
				(rs, rowNum) -> {
					return new Contato(rs.getLong("id"), rs.getString("nome"), rs.getString("telefone"));
				},

				"Edson Angoti");

				System.out.println(contato.getTelefone());

				int rowCount = this.jdbcTemplate.queryForObject("select count(*) from agenda", Integer.class);
				System.out.println(rowCount);
				System.out.println("Fim da consulta");
				
	}

}
