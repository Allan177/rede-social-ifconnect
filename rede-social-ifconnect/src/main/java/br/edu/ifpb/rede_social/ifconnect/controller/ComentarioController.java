package br.edu.ifpb.rede_social.ifconnect.controller;

import br.edu.ifpb.rede_social.ifconnect.entity.Comentario;
import br.edu.ifpb.rede_social.ifconnect.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // POST /api/comentarios
    @PostMapping
    public ResponseEntity<Comentario> criarComentario(@RequestBody Comentario comentario) {
        try {
            Comentario novoComentario = comentarioService.criarComentario(comentario);
            return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/comentarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarComentarioPorId(@PathVariable Long id) {
        return comentarioService.buscarComentarioPorId(id)
                .map(comentario -> new ResponseEntity<>(comentario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/comentarios/postagem/{postagemId}
    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<Comentario>> listarComentariosPorPostagem(@PathVariable Long postagemId) {
        List<Comentario> comentarios = comentarioService.listarComentariosPorPostagem(postagemId);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    // PUT /api/comentarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Comentario> atualizarComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
        try {
            Comentario comentarioAtualizado = comentarioService.atualizarComentario(id, comentario);
            return new ResponseEntity<>(comentarioAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/comentarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Long id) {
        try {
            comentarioService.deletarComentario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}