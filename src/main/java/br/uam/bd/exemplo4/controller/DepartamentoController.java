package br.uam.bd.exemplo4.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.uam.bd.exemplo4.model.Departamento;
import br.uam.bd.exemplo4.repository.DepartamentoRepository;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoRepository repository;

    public DepartamentoController(DepartamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Departamento> listarTodos() {
        return repository.findAll(); // retorna 200 OK por padrão
    }

    @GetMapping("/{id}")
    public Departamento buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED) // retorna 201 CREATED
    public Departamento criar(@RequestBody @Valid Departamento departamento) {
        return repository.save(departamento); // salva e já retorna o objeto com o ID gerado
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Departamento atualizar(@PathVariable Long id,
                                @RequestBody @Valid Departamento departamentoAtualizado) {
        return repository.findById(id)
                .map(
                    departamentoExistente -> {
                    departamentoExistente.setNome(departamentoAtualizado.getNome());
                    departamentoExistente.setOrcamento(departamentoAtualizado.getOrcamento());
                    return repository.save(departamentoExistente);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    
}
