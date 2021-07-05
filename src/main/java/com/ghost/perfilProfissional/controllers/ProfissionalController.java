package com.ghost.perfilProfissional.controllers;

import com.ghost.perfilProfissional.models.Profissional;
import com.ghost.perfilProfissional.models.enums.TipoCargo;
import com.ghost.perfilProfissional.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping(value = "/professionals")
public class ProfissionalController {

    @Autowired
    ProfissionalService profissionalService;

    //ROTA PARA PESQUISA POR PARAMETROS q PARA PESQUIA GERAL, id, nome, profissional PARA PESQUISA ESPECIFICA.
    @GetMapping(value = "/fields")
    public ResponseEntity<Page<Profissional>> searchByPesquisa(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "") Integer id,
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "") TipoCargo cargo,
            @RequestParam(defaultValue = "1/01/1800") String nascimento,
            Pageable pageable) throws ParseException {
        Page<Profissional> result = profissionalService.search(q, id, nome, cargo, nascimento, pageable);
        return ResponseEntity.ok(result);
    }

    //ROTA DE PESQUISA POR ID DO PROFISSIONAL
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Profissional> findById(@PathVariable Integer id) {
        Profissional obj = profissionalService.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    //ROTA PARA CRIAÇÃO DE PROFISSIONAL
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Profissional profissional){
        profissional = profissionalService.insert(profissional);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(profissional.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    //ROTA PARA EDIÇÃO DE PROFISSIONAL
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Profissional profissional, @PathVariable Integer id ){
        profissional.setId(id);
        profissional = profissionalService.update(profissional);
        return ResponseEntity.noContent().build();
    }

    //ROTA PARA DELEÇÃO DE PROFISSIONAL
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        profissionalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
