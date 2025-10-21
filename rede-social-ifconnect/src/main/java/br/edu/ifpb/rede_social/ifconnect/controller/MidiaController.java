package br.edu.ifpb.rede_social.ifconnect.controller;

import br.edu.ifpb.rede_social.ifconnect.entity.Midia;
import br.edu.ifpb.rede_social.ifconnect.service.MidiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/midias")
public class MidiaController {

    @Autowired
    private MidiaService midiaService;

    // POST /api/midias
    @PostMapping
    public ResponseEntity<Midia> salvarMidia(@RequestBody Midia midia) {
        try {
            Midia novaMidia = midiaService.salvarMidia(midia);
            return new ResponseEntity<>(novaMidia, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/midias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Midia> buscarMidiaPorId(@PathVariable Long id) {
        return midiaService.buscarMidiaPorId(id)
                .map(midia -> new ResponseEntity<>(midia, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/midias/postagem/{postagemId}
    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<Midia>> listarMidiasPorPostagem(@PathVariable Long postagemId) {
        List<Midia> midias = midiaService.listarMidiasPorPostagem(postagemId);
        return new ResponseEntity<>(midias, HttpStatus.OK);
    }

    // DELETE /api/midias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMidia(@PathVariable Long id) {
        try {
            midiaService.deletarMidia(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}