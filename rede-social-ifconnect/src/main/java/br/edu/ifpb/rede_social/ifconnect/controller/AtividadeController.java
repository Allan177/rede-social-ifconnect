package br.edu.ifpb.rede_social.ifconnect.controller;

import br.edu.ifpb.rede_social.ifconnect.document.Atividade;
import br.edu.ifpb.rede_social.ifconnect.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    // POST /api/atividades/registrar (Para registro manual/teste)
    @PostMapping("/registrar")
    public ResponseEntity<Atividade> registrarAtividade(@RequestParam Long usuarioId,
                                                        @RequestParam String acao,
                                                        @RequestParam String descricao) {
        try {
            Atividade novaAtividade = atividadeService.registrarAtividade(usuarioId, acao, descricao);
            return new ResponseEntity<>(novaAtividade, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/atividades/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Atividade>> listarAtividadesPorUsuario(@PathVariable Long usuarioId) {
        List<Atividade> atividades = atividadeService.listarAtividadesPorUsuario(usuarioId);
        return new ResponseEntity<>(atividades, HttpStatus.OK);
    }

    // GET /api/atividades/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Atividade> buscarAtividadePorId(@PathVariable String id) {
        return atividadeService.buscarAtividadePorId(id)
                .map(atividade -> new ResponseEntity<>(atividade, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/atividades (Para todos, útil para um feed de atividades)
    @GetMapping
    public ResponseEntity<List<Atividade>> listarTodasAtividades() {
        List<Atividade> atividades = atividadeService.listarTodasAtividades();
        return new ResponseEntity<>(atividades, HttpStatus.OK);
    }
}