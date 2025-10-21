package br.edu.ifpb.rede_social.ifconnect.controller;

import br.edu.ifpb.rede_social.ifconnect.document.LogSistema;
import br.edu.ifpb.rede_social.ifconnect.service.LogSistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogSistemaController {

    @Autowired
    private LogSistemaService logSistemaService;

    // POST /api/logs/registrar (Para registro manual/teste)
    @PostMapping("/registrar")
    public ResponseEntity<LogSistema> registrarLog(@RequestParam String nivel,
                                                   @RequestParam String mensagem,
                                                   @RequestParam String classeOrigem) {
        try {
            LogSistema novoLog = logSistemaService.registrarLog(nivel, mensagem, classeOrigem);
            return new ResponseEntity<>(novoLog, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/logs?nivel=error
    @GetMapping(params = "nivel")
    public ResponseEntity<List<LogSistema>> listarLogsPorNivel(@RequestParam String nivel) {
        List<LogSistema> logs = logSistemaService.listarLogsPorNivel(nivel);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    // GET /api/logs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LogSistema> buscarLogPorId(@PathVariable String id) {
        return logSistemaService.buscarLogPorId(id)
                .map(log -> new ResponseEntity<>(log, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/logs
    @GetMapping
    public ResponseEntity<List<LogSistema>> listarTodosLogs() {
        List<LogSistema> logs = logSistemaService.listarTodosLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}