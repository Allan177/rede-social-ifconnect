package br.edu.ifpb.rede_social.ifconnect.controller;

import br.edu.ifpb.rede_social.ifconnect.entity.Curtida;
import br.edu.ifpb.rede_social.ifconnect.service.CurtidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/curtidas")
public class CurtidaController {

    @Autowired
    private CurtidaService curtidaService;

    // POST /api/curtidas/curtir?usuarioId=1&postagemId=10
    // Adiciona uma curtida e registra uma Atividade no MongoDB
    @PostMapping("/curtir")
    public ResponseEntity<?> adicionarCurtida(@RequestParam Long usuarioId, @RequestParam Long postagemId) {
        try {
            Curtida novaCurtida = curtidaService.adicionarCurtida(usuarioId, postagemId);
            return new ResponseEntity<>(novaCurtida, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // Já curtiu
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /api/curtidas/descurtir?usuarioId=1&postagemId=10
    // Remove uma curtida
    @DeleteMapping("/descurtir")
    public ResponseEntity<Void> removerCurtida(@RequestParam Long usuarioId, @RequestParam Long postagemId) {
        try {
            curtidaService.removerCurtida(usuarioId, postagemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Não encontrou para remover
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/curtidas/contagem?postagemId=10
    @GetMapping("/contagem")
    public ResponseEntity<Long> contarCurtidasPorPostagem(@RequestParam Long postagemId) {
        long contagem = curtidaService.contarCurtidasPorPostagem(postagemId);
        return new ResponseEntity<>(contagem, HttpStatus.OK);
    }
}