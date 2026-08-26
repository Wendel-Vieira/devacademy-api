package com.dio.devacademy.controller;

import com.dio.devacademy.controller.exception.StandardError;
import com.dio.devacademy.controller.exception.ValidationError;
import com.dio.devacademy.domain.model.Developer;
import com.dio.devacademy.service.DeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/developers")
@Tag(name = "Desenvolvedores", description = "Endpoints para gerenciamento do perfil e carreira de Desenvolvedores")
public class DeveloperController {

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os desenvolvedores", description = "Retorna uma lista com todos os desenvolvedores cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Developer>> findAll() {
        return ResponseEntity.ok(developerService.findAll());
    }

    @GetMapping("/paged")
    @Operation(summary = "Listar desenvolvedores paginados", description = "Retorna os desenvolvedores com suporte a paginação e ordenação")
    @ApiResponse(responseCode = "200", description = "Página retornada com sucesso")
    public ResponseEntity<Page<Developer>> findAllPaged(@PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(developerService.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar desenvolvedor por ID", description = "Retorna os dados completos do desenvolvedor pelo seu identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Desenvolvedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Desenvolvedor não encontrado",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Developer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo desenvolvedor", description = "Cadastra um novo desenvolvedor com seu plano, carteira, habilidades e certificações")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Desenvolvedor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos informados",
                    content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio (e.g. e-mail ou GitHub já cadastrados)",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Developer> create(@Valid @RequestBody Developer developerToCreate) {
        Developer created = developerService.create(developerToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar desenvolvedor", description = "Atualiza todos os dados de um desenvolvedor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Desenvolvedor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos",
                    content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "404", description = "Desenvolvedor não encontrado",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Developer> update(@PathVariable Long id, @Valid @RequestBody Developer developerToUpdate) {
        return ResponseEntity.ok(developerService.update(id, developerToUpdate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir desenvolvedor", description = "Remove o cadastro de um desenvolvedor e seus dados agregados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desenvolvedor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Desenvolvedor não encontrado",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        developerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
